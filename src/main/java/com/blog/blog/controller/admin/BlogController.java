package com.blog.blog.controller.admin;

import com.blog.blog.dtos.BlogsDto;
import com.blog.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/admin/blogs")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/add")
    public ResponseEntity<BlogsDto> addBlog(@RequestBody BlogsDto blogsDto) {
        blogsDto = blogService.createBlog(blogsDto);
        return new ResponseEntity<>(blogsDto, HttpStatus.CREATED);
    }
}
