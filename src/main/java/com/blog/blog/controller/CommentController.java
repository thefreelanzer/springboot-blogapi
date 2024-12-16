package com.blog.blog.controller;

import com.blog.blog.dtos.AppResponse;
import com.blog.blog.dtos.BlogsDto;
import com.blog.blog.dtos.CommentsDto;
import com.blog.blog.service.BlogService;
import com.blog.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/blogs/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public ResponseEntity<AppResponse<CommentsDto>> addComment(@RequestBody CommentsDto commentsDto) {
        CommentsDto savedComment = commentService.addComment(commentsDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AppResponse<>(savedComment, true, "Comment added successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AppResponse<BlogsDto>> deleteBlog(@PathVariable int id) {
        commentService.deleteComment(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AppResponse<>(null, true, "Comment deleted successfully"));
    }

}
