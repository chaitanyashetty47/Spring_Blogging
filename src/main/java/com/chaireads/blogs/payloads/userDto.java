package com.chaireads.blogs.payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class userDto {

    private int id;
    @NotEmpty
    @Size(min = 6, max = 20, message = "Username can't less than 6 characters")
    private String username;
    @Email(message = "Invalid Email")
    private String email;
    @NotEmpty
    @Size(min = 8, max = 20,message = "Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    private String password;
    @NotEmpty
    @Size(min = 10, max = 250, message = "About should contain atmost 250 characters")
    private String about;
}
