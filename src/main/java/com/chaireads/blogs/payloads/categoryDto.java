package com.chaireads.blogs.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class categoryDto {
    private Integer id;
    @NotBlank
    @Size(min = 2, max = 70, message = "Title cant be less than 2 characters")
    private String title;
    @NotBlank
    @Size(max = 50, message = "Description cant be nore than 2 characters")
    private String description;
}
