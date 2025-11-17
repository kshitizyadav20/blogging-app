package com.Kshitiz.blog.repositories;

import com.Kshitiz.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
