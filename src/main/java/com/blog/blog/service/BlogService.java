package com.blog.blog.service;

import com.blog.blog.dtos.BlogsDto;
import com.blog.blog.dtos.CommentsDto;
import com.blog.blog.dtos.CurrentUserDto;
import com.blog.blog.entity.Blogs;
import com.blog.blog.entity.User;
import com.blog.blog.repository.BlogRepository;
import com.blog.blog.repository.CommentRepository;
import com.blog.blog.utils.AuthUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    public BlogsDto createBlog(BlogsDto blogsDto) {

        Blogs blog = new Blogs();
        blog.setCaption(blogsDto.getCaption());
        blog.setDescription(blogsDto.getDescription());

        User currentUser = AuthUtils.getCurrentUserDetails();
        blog.setCreatedUser(currentUser);
        blogsDto.setCreatedBy(currentUser.getId());

        LocalDateTime currentTime = LocalDateTime.now();
        blog.setCreatedAt(currentTime);
        blogsDto.setCreatedAt(currentTime);
        blog.setUpdatedAt(currentTime);
        blogsDto.setUpdatedAt(currentTime);

        blog = blogRepository.save(blog);
        blogsDto.setId(blog.getId());

        return blogsDto;
    }

    @Transactional
    public List<BlogsDto> getAllBlogs() {
        // Fetch all blogs
        List<Blogs> blogs = blogRepository.findAllByDeletedAtIsNull();

        // Map Blogs entity to BlogsDto
        return blogs.stream().map(blog -> {
            BlogsDto blogsDto = new BlogsDto();
            blogsDto.setId(blog.getId());
            blogsDto.setCaption(blog.getCaption());
            blogsDto.setDescription(blog.getDescription());
            blogsDto.setCreatedAt(blog.getCreatedAt());
            blogsDto.setUpdatedAt(blog.getUpdatedAt());
            blogsDto.setDeletedAt(blog.getDeletedAt());


            // Map created user
            if (blog.getCreatedUser() != null) {
                CurrentUserDto userDto = new CurrentUserDto();
                userDto.setId(blog.getCreatedUser().getId());
                userDto.setFirstname(blog.getCreatedUser().getFirstname());
                userDto.setLastname(blog.getCreatedUser().getLastname());
                userDto.setEmail(blog.getCreatedUser().getEmail());
                blogsDto.setCreatedUser(userDto);
            }

            // Map comments
            List<CommentsDto> commentsDto = blog.getComments().stream().map(comment -> {
                System.out.println("comment");
                CommentsDto dto = new CommentsDto();
                dto.setId(comment.getId());
                dto.setBlogId(blogsDto.getId());
                dto.setComment(comment.getComment());
                dto.setCreatedAt(comment.getCreatedAt());
                dto.setUpdatedAt(comment.getUpdatedAt());
                dto.setDeletedAt(comment.getDeletedAt());

                // Map user details for the comment
                if (comment.getCreatedUser() != null) {
                    CurrentUserDto commentUserDto = new CurrentUserDto();
                    commentUserDto.setId(comment.getCreatedUser().getId());
                    commentUserDto.setFirstname(comment.getCreatedUser().getFirstname());
                    commentUserDto.setLastname(comment.getCreatedUser().getLastname());
                    commentUserDto.setEmail(comment.getCreatedUser().getEmail());
                    dto.setCreatedUser(commentUserDto);
                }

                return dto;
            }).toList();
            blogsDto.setComments(commentsDto);

            return blogsDto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteBlog(int id) {
        Blogs blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found with id: " + id));

        blog.setDeletedAt(LocalDateTime.now());
        blogRepository.save(blog);

        commentRepository.updateDeletedAtByBlogId(id, LocalDateTime.now());
    }
}
