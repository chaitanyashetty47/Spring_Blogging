package com.chaireads.blogs.repositories;

import com.chaireads.blogs.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface commentRepo extends JpaRepository<Comment,Integer> {

}
