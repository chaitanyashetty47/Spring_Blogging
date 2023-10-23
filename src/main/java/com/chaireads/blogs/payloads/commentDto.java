package com.chaireads.blogs.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class commentDto {
    private int commentId;
    private String content;
}
