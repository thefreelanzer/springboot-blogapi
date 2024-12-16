package com.blog.blog.controller;

import com.blog.blog.dtos.BlogsDto;
import com.blog.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/blogs")
public class BlogsController {

    private final BlogService blogService;

    public BlogsController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public ResponseEntity<List<BlogsDto>> getAllOrders() {
        List<BlogsDto> orders = blogService.getAllBlogs();
        return ResponseEntity.ok(orders);
    }
}
