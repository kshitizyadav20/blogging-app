package com.Kshitiz.blog.repositories;

import com.Kshitiz.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
