package com.Kshitiz.blog.repositories;

import com.Kshitiz.blog.entities.Category;
import com.Kshitiz.blog.entities.Post;
import com.Kshitiz.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
}
