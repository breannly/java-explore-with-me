package com.example.server.category;

import com.example.server.category.dto.CategoryDto;
import com.example.server.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {

	List<CategoryDto> getCategories(Integer from, Integer size);

	CategoryDto getCategory(Long catId);

	CategoryDto updateCategory(Category category);

	CategoryDto createCategory(NewCategoryDto category);

	void deleteCategory(Long catId);
}
