package com.blog.blog.controller;

import com.blog.blog.dtos.BlogsDto;
import com.blog.blog.dtos.CommentsDto;
import com.blog.blog.service.BlogService;
import com.blog.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public ResponseEntity<CommentsDto> addComment(@RequestBody CommentsDto commentsDto) {
        commentsDto = commentService.addComment(commentsDto);
        return new ResponseEntity<>(commentsDto, HttpStatus.CREATED);
    }
}
