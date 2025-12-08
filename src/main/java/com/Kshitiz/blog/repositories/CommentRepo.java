package com.Kshitiz.blog.repositories;

import com.Kshitiz.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository <Comment, Integer>{
}
