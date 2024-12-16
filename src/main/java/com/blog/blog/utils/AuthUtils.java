package com.blog.blog.utils;

import com.blog.blog.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

    public static User getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                return (User) principal; // Return the User object
            } else {
                throw new IllegalStateException("Authenticated user is not of type User");
            }
        }
        throw new IllegalStateException("No authenticated user found");
    }
}
