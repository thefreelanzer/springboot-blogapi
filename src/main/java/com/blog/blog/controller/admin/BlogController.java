package com.blog.blog.controller.admin;

import com.blog.blog.dtos.AppResponse;
import com.blog.blog.dtos.BlogsDto;
import com.blog.blog.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin/blogs")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/add")
    public ResponseEntity<AppResponse<BlogsDto>> addBlog(@RequestBody BlogsDto blogsDto) {
        BlogsDto createdBlog = blogService.createBlog(blogsDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AppResponse<>(createdBlog, true, "Blog created successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AppResponse<BlogsDto>> deleteBlog(@PathVariable int id) {
        blogService.deleteBlog(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AppResponse<>(null, true, "Blog deleted successfully"));
    }
}
