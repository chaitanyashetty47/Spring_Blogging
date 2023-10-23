package com.chaireads.blogs.payloads;

import com.chaireads.blogs.entities.Category;
import com.chaireads.blogs.entities.Comment;
import com.chaireads.blogs.entities.User;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor

@Getter
@Setter
public class postDto {
    private Integer id;

    private String name;

    private String content;

    private String image;

    private Date addedDate;

    private categoryDto category;

    private userDto user;

    private Set<commentDto> comments = new HashSet<>();

}
