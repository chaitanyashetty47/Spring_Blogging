package com.chaireads.blogs.repositories;

import com.chaireads.blogs.entities.Category;
import com.chaireads.blogs.entities.Post;
import com.chaireads.blogs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface postRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category cate);

    @Query("select p from Post p where p.name like :key")
    List<Post> searchByName(@Param("key") String name);
}
