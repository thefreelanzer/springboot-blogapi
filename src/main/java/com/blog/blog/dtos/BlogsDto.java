package com.blog.blog.dtos;

import com.blog.blog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogsDto {
    private Integer id;

    private String caption;

    private String description;

    private Integer createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    private CurrentUserDto createdUser;

    private List<CommentsDto> comments;

}
