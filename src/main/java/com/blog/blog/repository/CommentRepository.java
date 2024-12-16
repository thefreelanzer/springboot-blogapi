package com.blog.blog.repository;

import com.blog.blog.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface CommentRepository extends JpaRepository<Comments, Integer> {
    @Modifying
    @Query("UPDATE Comments c SET c.deletedAt = :deletedAt WHERE c.blogId = :blogId")
    void updateDeletedAtByBlogId(@Param("blogId") int blogId, @Param("deletedAt") LocalDateTime deletedAt);

}
