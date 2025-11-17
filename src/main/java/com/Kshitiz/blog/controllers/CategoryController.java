package com.Kshitiz.blog.controllers;


import com.Kshitiz.blog.payloads.ApiResponse;
import com.Kshitiz.blog.payloads.CategoryDto;
import com.Kshitiz.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    //getSingle - get single user

    //Post-create user
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory (@Valid @RequestBody CategoryDto categoryDto)
    {
        CategoryDto createCategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
    }

    //Put - update user
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory (@Valid @RequestBody CategoryDto categoryDto, @PathVariable ("categoryId") Integer cId)
    {
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto,cId);
        return ResponseEntity.ok(updatedCategory);
    }

    //Delete - delete user
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer cId)
    {
        this.categoryService.deleteCategory(cId);
        return new ResponseEntity(new ApiResponse("Category deleted Successfully",true), HttpStatus.OK);
    }

    //GetAll - get all Category
    //GET-user get
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllUsers()
    {
        return ResponseEntity.ok(this.categoryService.getCategories());
    }

    //GET-single category get
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleUser(@PathVariable("categoryId") Integer cId)
    {
        return ResponseEntity.ok(this.categoryService.getCategoryById(cId));
    }
}
