package com.chaireads.blogs.services;

import com.chaireads.blogs.payloads.userDto;

import java.util.List;

public interface userService {
    userDto createUser(userDto user);

    userDto updateUser(userDto user, Integer userId);

    userDto getUserById(Integer userId);

    List<userDto> getAllUsers();

    void deleteUser(Integer userId);


}
