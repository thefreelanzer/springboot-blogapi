package com.blog.blog.service;

import com.blog.blog.dtos.CommentsDto;
import com.blog.blog.entity.Blogs;
import com.blog.blog.entity.Comments;
import com.blog.blog.entity.User;
import com.blog.blog.repository.CommentRepository;
import com.blog.blog.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public CommentsDto addComment(CommentsDto commentsDto) {
        Comments comment = new Comments();
        System.out.println(commentsDto.getBlogId());
        comment.setComment(commentsDto.getComment());
        comment.setBlogId(commentsDto.getBlogId());

        User currentUser = AuthUtils.getCurrentUserDetails();
        comment.setCreatedUser(currentUser);
        commentsDto.setCreatedBy(currentUser.getId());

        LocalDateTime currentTime = LocalDateTime.now();
        comment.setCreatedAt(currentTime);
        commentsDto.setCreatedAt(currentTime);
        comment.setUpdatedAt(currentTime);
        commentsDto.setUpdatedAt(currentTime);

        comment = commentRepository.save(comment);
        commentsDto.setId(comment.getId());

        return commentsDto;
    }
}
