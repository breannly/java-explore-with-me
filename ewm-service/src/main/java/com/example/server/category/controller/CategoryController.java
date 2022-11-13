package com.example.server.category.controller;

import com.example.server.category.CategoryService;
import com.example.server.category.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping
	public List<CategoryDto> getCategories(@RequestParam(value = "from", defaultValue = "0") Integer from,
										   @RequestParam(value = "size", defaultValue = "10") Integer size) {
		return categoryService.getCategories(from, size);
	}

	@GetMapping("/{catId}")
	public CategoryDto getCategory(@PathVariable("catId") Long catId) {
		return categoryService.getCategory(catId);
	}
}
