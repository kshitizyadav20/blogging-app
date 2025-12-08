package com.Kshitiz.blog.services;

import com.Kshitiz.blog.entities.Post;
import com.Kshitiz.blog.payloads.PostDto;
import com.Kshitiz.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {

    //create
        PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //update
        PostDto updatePost(PostDto postDto, Integer postId);

    //delete
        void deletePost(Integer postId);

    //get all posts
        PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    //get single post
        PostDto getPostById(Integer postId);

    //get all post by category
    PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize);


    //get all posts by user
    PostResponse getPostsbyUser(Integer pageNumber, Integer pageSize, Integer userId);

    //search posts
    List<PostDto> searchPosts(String keyword);



}
