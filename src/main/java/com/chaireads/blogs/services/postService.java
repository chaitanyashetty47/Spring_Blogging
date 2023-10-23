package com.chaireads.blogs.services;

import com.chaireads.blogs.payloads.postDto;
import com.chaireads.blogs.payloads.PostResponse;

import java.util.List;

public interface postService {
    //create post
    postDto createPost(postDto postdto, Integer categoryId, Integer userId);

    //update post
    postDto updatePost(postDto postdto, Integer pid);

    //delete posts
    void deletePost(Integer id);

    //get all posts
    PostResponse getAllPosts(Integer pagenumber, Integer pagesize,String sortBy, String sortOrder);

    //get post by id
    postDto getPostById(Integer id);

    //get all posts by category
    List<postDto> getAllPostsByCategory(Integer cid);

    //get all posts by user
    List<postDto> getAllPostsByUser(Integer uid);

    //search
    List<postDto> searchPosts(String search);


}
