package com.chaireads.blogs.repositories;

import com.chaireads.blogs.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface categoryRepo extends JpaRepository<Category,Integer> {
}
