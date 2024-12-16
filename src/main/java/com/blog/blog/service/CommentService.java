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
        Comments comment = createCommentFromDto(commentsDto);

        User currentUser = AuthUtils.getCurrentUserDetails();
        comment.setCreatedUser(currentUser);

        setCommentTimestamps(comment, commentsDto);

        comment = commentRepository.save(comment);

        commentsDto.setCreatedBy(currentUser.getId());
        commentsDto.setId(comment.getId());

        return commentsDto;
    }

    public void deleteComment(Integer id) {

        Comments comment = findCommentById(id);

        User currentUser = AuthUtils.getCurrentUserDetails();
        validateUserAuthorization(comment, currentUser);

        comment.setDeletedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    public CommentsDto updateComment(Integer id, CommentsDto commentsDto) {
        Comments comment = findCommentById(id);

        User currentUser = AuthUtils.getCurrentUserDetails();
        validateUserAuthorization(comment, currentUser);

        comment.setComment(commentsDto.getComment()); // Update comment text
        comment.setBlogId(commentsDto.getBlogId());

        LocalDateTime currentTime = LocalDateTime.now();
        comment.setUpdatedAt(currentTime);
        commentsDto.setUpdatedAt(currentTime);

        comment = commentRepository.save(comment);

        commentsDto.setId(comment.getId());
        commentsDto.setCreatedBy(currentUser.getId());
        commentsDto.setCreatedAt(comment.getCreatedAt());

        return commentsDto;
    }

    /**
     * create Comment from Dto
     *
     * @param commentsDto - commentDto
     * @return Comment
     */
    private Comments createCommentFromDto(CommentsDto commentsDto) {
        Comments comment = new Comments();
        comment.setComment(commentsDto.getComment());
        comment.setBlogId(commentsDto.getBlogId());
        return  comment;
    }

    /**
     * set timestamps for comment
     *
     * @param comment - Comment
     * @param commentsDto - Comment Dto
     */
    private void setCommentTimestamps(Comments comment, CommentsDto commentsDto) {
        LocalDateTime currentTime = LocalDateTime.now();
        comment.setCreatedAt(currentTime);
        commentsDto.setCreatedAt(currentTime);
        comment.setUpdatedAt(currentTime);
        commentsDto.setUpdatedAt(currentTime);
    }

    /**
     * check current logged user is the creator of the comment
     *
     * @param comment - Comment
     * @param currentUser - Created User
     */
    private void validateUserAuthorization(Comments comment, User currentUser) {
        if (!comment.getCreatedUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to delete this comment.");
        }
    }

    /**
     * checking the comment with the id exist or not else throw exception
     *
     * @param id - Id
     * @return Comment or exception if not exist
     */
    private Comments findCommentById(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + id));
    }
}
