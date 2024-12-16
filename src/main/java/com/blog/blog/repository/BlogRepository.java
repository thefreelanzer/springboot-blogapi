package com.blog.blog.repository;

import com.blog.blog.entity.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blogs, Integer> {
    List<Blogs> findById(Long id);
}
