package com.chaireads.blogs.services;

import com.chaireads.blogs.entities.Comment;
import com.chaireads.blogs.payloads.commentDto;

public interface commentService {
    commentDto createComment(commentDto dto,Integer postId);

    void deleteComment(Integer commentId);
}
