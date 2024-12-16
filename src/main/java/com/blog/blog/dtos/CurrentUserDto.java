package com.blog.blog.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrentUserDto {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
}
