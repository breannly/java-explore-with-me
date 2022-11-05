package com.example.server.category.dto;

import com.example.server.category.Category;

public class CategoryMapper {

	public static Category mapToCategory(NewCategoryDto newCategoryDto) {
		Category category = new Category();
		category.setName(newCategoryDto.getName());

		return category;
	}

	public static Category mapToCategory(CategoryDto categoryDto) {
		Category category = new Category();
		category.setId(categoryDto.getId());
		category.setName(categoryDto.getName());

		return category;
	}

	public static CategoryDto mapToCategoryDto(Category category) {
		return new CategoryDto(
				category.getId(),
				category.getName()
		);
	}
}
