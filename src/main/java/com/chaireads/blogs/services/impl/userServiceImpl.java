package com.chaireads.blogs.services.impl;

import com.chaireads.blogs.entities.User;
import com.chaireads.blogs.exceptions.ResourceNotFoundException;
import com.chaireads.blogs.repositories.userRepo;
import com.chaireads.blogs.services.userService;
import com.chaireads.blogs.payloads.userDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class userServiceImpl implements userService {

    @Autowired
    private userRepo repo;

    @Autowired
    private ModelMapper modelMapper;

//    public userServiceImpl(userRepo repo) {
//        this.repo = repo;
//    }
//    public userServiceImpl(){
//        this.repo = null;
//    }

    @Override
    public userDto createUser( userDto UserDto) {
        User user = this.dtoToUser(UserDto);
        User savedUser = this.repo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public userDto updateUser(userDto UserDto, Integer userId) {

        User user = this.repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));

        user.setEmail(UserDto.getEmail());
        user.setUsername(UserDto.getUsername());
        user.setPassword(UserDto.getPassword());
        user.setAbout(UserDto.getAbout());

        User userUpdate = this.repo.save(user);
        userDto userDto1 = this.userToDto(userUpdate);

        return userDto1;
    }

    @Override
    public userDto getUserById(Integer userId) {
        User user = this.repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));

        return this.userToDto(user);
    }

    @Override
    public List<userDto> getAllUsers() {
        List<User> users = this.repo.findAll();
        List<userDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
        this.repo.delete(user);

    }

    public User dtoToUser(userDto userdto){
        User user = this.modelMapper.map(userdto, User.class);
//        user.setId(userDto.getId());
//        user.setEmail(userDto.getEmail());
//        user.setUsername(userDto.getUsername());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());

        return user;
    }

    public userDto userToDto(User user){
        userDto dto = this.modelMapper.map(user, userDto.class);
//        dto.setId(user.getId());
//        dto.setEmail(user.getEmail());
//        dto.setUsername(user.getUsername());
//        dto.setPassword(user.getPassword());
//        dto.setAbout(user.getAbout());

        return dto;
    }


}
