package com.chaireads.blogs.repositories;

import com.chaireads.blogs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepo extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}
