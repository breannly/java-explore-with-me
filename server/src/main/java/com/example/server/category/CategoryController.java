package com.example.server.controller.category;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @GetMapping
    public Category getCategories() {
        return null;
    }

    @GetMapping("/{catId}")
    public Category getCategory(@PathVariable("catId") Long catId) {
        return null;
    }
}
