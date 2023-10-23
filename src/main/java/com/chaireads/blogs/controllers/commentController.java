package com.chaireads.blogs.controllers;

import com.chaireads.blogs.payloads.ApiResponse;
import com.chaireads.blogs.payloads.commentDto;
import com.chaireads.blogs.payloads.postDto;
import com.chaireads.blogs.services.commentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class commentController {

    @Autowired
    private commentService commentService;

    @PostMapping("/post/{pid}/comments")
    public ResponseEntity<commentDto> createComment(
            @RequestBody commentDto commDto,
            @PathVariable Integer pid) {
        commentDto newComment = this.commentService.createComment(commDto,pid);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/delete/{cid}")
    public ResponseEntity<ApiResponse> deleteComment(
            @PathVariable Integer cid) {
        this.commentService.deleteComment(cid);
        return new ResponseEntity<>(new ApiResponse("Deleted Comment !!", true), HttpStatus.OK);
    }


}
