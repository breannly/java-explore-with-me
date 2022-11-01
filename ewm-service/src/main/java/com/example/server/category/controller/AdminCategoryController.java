package com.example.server.category.controller;

import com.example.server.category.Category;
import com.example.server.category.CategoryService;
import com.example.server.category.dto.CategoryDto;
import com.example.server.category.dto.NewCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class AdminCategoryController {

	private final CategoryService categoryService;

	@PatchMapping
	public CategoryDto updateCategory(@RequestBody Category category) {
		return categoryService.updateCategory(category);
	}

	@PostMapping
	public CategoryDto createCategory(@RequestBody NewCategoryDto category) {
		return categoryService.createCategory(category);
	}

	@DeleteMapping("/{catId}")
	public void deleteCategory(@PathVariable("catId") Long catId) {
		categoryService.deleteCategory(catId);
	}
}
