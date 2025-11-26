package com.Kshitiz.blog.services.impl;

import com.Kshitiz.blog.entities.Category;
import com.Kshitiz.blog.entities.Post;
import com.Kshitiz.blog.entities.User;
import com.Kshitiz.blog.exceptions.ResourceNotFoundException;
import com.Kshitiz.blog.payloads.PostDto;
import com.Kshitiz.blog.payloads.PostResponse;
import com.Kshitiz.blog.repositories.CategoryRepo;
import com.Kshitiz.blog.repositories.PostRepo;
import com.Kshitiz.blog.repositories.UserRepo;
import com.Kshitiz.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ", "User id", userId));

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", categoryId));

        Post post = this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
        post.setImageName(postDto.getImageName());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost,PostDto.class);

    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber,pageSize, sort);

        Page<Post> pagePost = this.postRepo.findAll(p);

        List<Post> allPosts = pagePost.getContent();

        List<PostDto> postDtos = allPosts.stream().map((post)-> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());


        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
        return this.modelMapper.map(post, PostDto.class);

    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {

        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        Pageable p = PageRequest.of(pageNumber, pageSize);

        Page<Post> pagePost = this.postRepo.findByCategory(cat, p);

        List<PostDto> postDtos = pagePost.getContent()
                .stream()
                .map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }


    @Override
    public PostResponse getPostsbyUser(Integer pageNumber, Integer pageSize, Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user Id", userId));

        Pageable p = PageRequest.of(pageNumber, pageSize);

        Page<Post> pagePost = this.postRepo.findByUser(user, p);

        List<PostDto> postDtos = pagePost.getContent()
                .stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }


    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post>posts = this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
