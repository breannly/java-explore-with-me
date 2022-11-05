package com.example.server.category;

import com.example.server.category.dto.CategoryDto;
import com.example.server.category.dto.CategoryMapper;
import com.example.server.category.dto.NewCategoryDto;
import com.example.server.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Override
	public List<CategoryDto> getCategories(Integer from, Integer size) {
		Pageable pageable = PageRequest.of(from / size, size);
		return categoryRepository.findAll(pageable)
				.stream()
				.map(CategoryMapper::mapToCategoryDto)
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDto getCategory(Long catId) {
		Category foundCategory = categoryRepository.findById(catId).orElseThrow(()
				-> new ObjectNotFoundException("Category not found"));
		return CategoryMapper.mapToCategoryDto(foundCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto) {
		Category category = CategoryMapper.mapToCategory(categoryDto);
		return CategoryMapper.mapToCategoryDto(category);
	}

	@Override
	public CategoryDto createCategory(NewCategoryDto newCategory) {
		Category category = CategoryMapper.mapToCategory(newCategory);
		return CategoryMapper.mapToCategoryDto(categoryRepository.save(category));
	}

	@Override
	public void deleteCategory(Long catId) {
		categoryRepository.deleteById(catId);
	}
}
