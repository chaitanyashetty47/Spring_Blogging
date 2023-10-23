package com.chaireads.blogs.services.impl;

import com.chaireads.blogs.entities.Comment;
import com.chaireads.blogs.entities.Post;
import com.chaireads.blogs.exceptions.ResourceNotFoundException;
import com.chaireads.blogs.payloads.commentDto;
import com.chaireads.blogs.repositories.commentRepo;
import com.chaireads.blogs.repositories.postRepo;
import com.chaireads.blogs.services.commentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class commentServiceImpl implements commentService {

    @Autowired
    private postRepo post;
    @Autowired
    private commentRepo comment;

    @Autowired
    private ModelMapper mapper;

    @Override
    public commentDto createComment(commentDto dto, Integer postId) {
        Post post = this.post.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post","post id",postId));
        Comment commentPost = this.mapper.map(dto, Comment.class);

        commentPost.setPost(post);

        Comment savedComment = this.comment.save(commentPost);

        return this.mapper.map(savedComment,commentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment com = this.comment.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment","comment id",commentId));
        this.comment.delete(com);
    }
}
