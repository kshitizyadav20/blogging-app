package com.Kshitiz.blog.services.impl;

import com.Kshitiz.blog.entities.Category;
import com.Kshitiz.blog.entities.Post;
import com.Kshitiz.blog.entities.User;
import com.Kshitiz.blog.exceptions.ResourceNotFoundException;
import com.Kshitiz.blog.payloads.PostDto;
import com.Kshitiz.blog.repositories.CategoryRepo;
import com.Kshitiz.blog.repositories.PostRepo;
import com.Kshitiz.blog.repositories.UserRepo;
import com.Kshitiz.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<PostDto> getAllPost() {
        List<Post> posts = this.postRepo.findAll();
        List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(()-> new ResourceNotFoundException("Post", "post Id", postId));
        return this.modelMapper.map(post, PostDto.class);

    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId ));
        List<Post> posts = this.postRepo.findByCategory(cat);

       List<PostDto> postDtos =  posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class))
               .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsbyUser(Integer userId) {
            User user = this.userRepo.findById(userId)
                    .orElseThrow(()-> new ResourceNotFoundException("User", "user Id", userId));
            List<Post> posts = this.postRepo.findByUser(user);

            List<PostDto> postDtos =  posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<Post> searchPosts(String keywords) {
        return List.of();
    }
}
