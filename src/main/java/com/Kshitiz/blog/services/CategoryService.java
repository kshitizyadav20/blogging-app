package com.Kshitiz.blog.services;

import com.Kshitiz.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    //create
    CategoryDto createCategory(CategoryDto category);

    //update
    CategoryDto updateCategory(CategoryDto category, Integer categoryId);

    //delete
    void deleteCategory(Integer userId);

    //get
    CategoryDto getCategoryById(Integer categoryId);

    //getAll
    List<CategoryDto> getCategories();
}
