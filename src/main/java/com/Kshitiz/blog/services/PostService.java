package com.Kshitiz.blog.services;

import com.Kshitiz.blog.entities.Post;
import com.Kshitiz.blog.payloads.PostDto;

import java.util.List;

public interface PostService {

    //create
        PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //update
        PostDto updatePost(PostDto postDto, Integer postId);

    //delete
        void deletePost(Integer postId);

    //get all posts
        List<PostDto> getAllPost();

    //get single post
        PostDto getPostById(Integer postId);

    //get all post by category
        List<PostDto> getPostsByCategory(Integer categoryId);

    //get all posts by user
        List<PostDto> getPostsbyUser(Integer userId);

    //search posts
        List<Post> searchPosts(String keywords);

}
