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

    public void deleteComment(Integer id) {
        Comments comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));

        // Check if the comment was created by the current user
        User currentUser = AuthUtils.getCurrentUserDetails();

        if (!comment.getCreatedUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to delete this comment.");
        }

        // Set the deleted_at field to mark the comment as deleted
        comment.setDeletedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }
}
