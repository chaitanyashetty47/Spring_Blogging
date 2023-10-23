package com.chaireads.blogs.controllers;

import com.chaireads.blogs.config.appConstants;
import com.chaireads.blogs.payloads.ApiResponse;
import com.chaireads.blogs.payloads.PostResponse;
import com.chaireads.blogs.payloads.postDto;
import com.chaireads.blogs.services.fileService;
import com.chaireads.blogs.services.postService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;


@RestController
@RequestMapping("/api")
public class postController {

    @Autowired
    private postService post;

    @Autowired
    private fileService file;

    @Value("${project.image}")
    private String imagePath;

    @PostMapping("/user/{uid}/category/{cid}/posts")
    public ResponseEntity<postDto> createPost(
            @RequestBody postDto postDto,
            @PathVariable Integer cid,
            @PathVariable Integer uid) {
        postDto newPost = this.post.createPost(postDto,cid,uid);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<postDto>> getPostsbyUser(@PathVariable Integer userId){
        List<postDto> posts = this.post.getAllPostsByUser(userId);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/category/{cateId}/posts")
    public ResponseEntity<List<postDto>> getPostsbyCategory(@PathVariable Integer cateId){
        List<postDto> posts = this.post.getAllPostsByCategory(cateId);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/allposts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value="pageNumber",defaultValue = appConstants.PAGE_NUMBER, required = false) Integer pagenum,
            @RequestParam(value="pageSize",defaultValue = appConstants.PAGE_SIZE, required = false) Integer pagesize,
            @RequestParam(value="sortBy",defaultValue = appConstants.SORT_BY,required = false)String sortBy,
            @RequestParam(value="sortOrder",defaultValue = appConstants.SORT_ORDER,required = false)String sortOrder
    ){
        PostResponse postResponse = this.post.getAllPosts(pagenum,pagesize,sortBy,sortOrder);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @GetMapping("/allposts/{postId}")
    public ResponseEntity<postDto> getPostById(@PathVariable Integer postId){
        postDto post = this.post.getPostById(postId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }

    @DeleteMapping("/deletepost/{pid}")
    public ApiResponse deletePost(@PathVariable Integer pid){
        this.post.deletePost(pid);
        return new ApiResponse("Post is successfully deleted",true);
    }
    @PutMapping("/updatepost/{pid}")
    public ResponseEntity<postDto> updatePost(@RequestBody postDto dto, @PathVariable Integer pid){
        postDto updatePost = this.post.updatePost(dto,pid);
        return new ResponseEntity<>(updatePost,HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<postDto>> searchPostByTitle(
            @PathVariable("keywords") String keywords
    ){
        List<postDto> result = this.post.searchPosts(keywords);
        return new ResponseEntity<List<postDto>>(result,HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<postDto> uploadPostImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable() Integer postId){
        postDto dto = this.post.getPostById(postId);
        String fileName = this.file.uploadImage(imagePath,image);
        dto.setImage(fileName);
        postDto updatePost = this.post.updatePost(dto,postId);
        return new ResponseEntity<>(updatePost,HttpStatus.OK);

    }

    @GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    )throws IOException{
        InputStream resource = this.file.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }


}
