package com.Kshitiz.blog.controllers;

import com.Kshitiz.blog.config.AppConstants;
import com.Kshitiz.blog.payloads.ApiResponse;
import com.Kshitiz.blog.payloads.CategoryDto;
import com.Kshitiz.blog.payloads.PostDto;
import com.Kshitiz.blog.payloads.PostResponse;
import com.Kshitiz.blog.services.FileService;
import com.Kshitiz.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {
    //create

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId)
    {
        PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
    }

    //get by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostByUser(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                      @RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)Integer pageSize,@PathVariable Integer userId){
        PostResponse postResponse = this.postService.getPostsbyUser(pageNumber,pageSize,userId);
       return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

    //get by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @PathVariable Integer categoryId){

        PostResponse posts = this.postService.getPostsByCategory(categoryId, pageNumber, pageSize);
        return new ResponseEntity<PostResponse>(posts,HttpStatus.OK);
    }


    //get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                   @RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)Integer pageSize,
                                                   @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
                                                   @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir){
        PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity <PostResponse>(postResponse, HttpStatus.OK);
    }

    //get posts by Id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable Integer postId)
    {
        return ResponseEntity.ok(this.postService.getPostById(postId));
    }

    //delete post
    @DeleteMapping("/posts/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId)
    {
        this.postService.deletePost(postId);
        return new ApiResponse("Post is successfully deleted!!", true);
    }

    //update post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId)
    {
        PostDto updatePost = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
    }

    //search
    @GetMapping("posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(
        @PathVariable("keywords") String keywords)
        {
      List<PostDto> result = this.postService.searchPosts(keywords);
      return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
    }

    //post image upload
    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image, @PathVariable Integer postId) throws IOException {

        PostDto postDto = this.postService.getPostById(postId);
       String fileName = this.fileService.uploadImage(path, image);
       postDto.setImageName(fileName);
       PostDto updatePost = this.postService.updatePost(postDto, postId);
       return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
    }

    @GetMapping(value = "/posts/image/{postId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("postId") Integer postId, HttpServletResponse response) throws IOException {

        PostDto postDto = this.postService.getPostById(postId);
        String imageName = postDto.getImageName();

        InputStream resource = this.fileService.getResource(path, imageName);

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
