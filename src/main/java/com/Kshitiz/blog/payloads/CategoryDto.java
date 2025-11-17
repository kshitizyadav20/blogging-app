package com.Kshitiz.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;

    @NotBlank
    @Size(min = 4,message = "Title must be min of 4 characters")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10, message = "Description must be of min of 10 chars!!")
    private String categoryDescription;

}
