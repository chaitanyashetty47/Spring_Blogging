package com.chaireads.blogs.controllers;

import com.chaireads.blogs.payloads.ApiResponse;
import com.chaireads.blogs.services.userService;
import com.chaireads.blogs.payloads.userDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class userController {

    @Autowired
    private userService userService;

    @PostMapping(value = "/")
    public ResponseEntity<userDto> createUser(@Valid @RequestBody userDto UserDto){
        userDto createUserDto = this.userService.createUser(UserDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<userDto> updateUser(@Valid @RequestBody userDto UserDto, @PathVariable("userId") Integer uid){
        userDto updateUser = this.userService.updateUser(UserDto,uid);
        return ResponseEntity.ok(updateUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("userId") Integer uid){
        this.userService.deleteUser(uid);
        return new ResponseEntity<ApiResponse>(
                new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);

    }

    @GetMapping("/")
    public ResponseEntity<List<userDto>> getAllUsers(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }
    @GetMapping("/{userId}")
    public ResponseEntity<userDto> getSingleUser(@PathVariable Integer userId){
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }




}
