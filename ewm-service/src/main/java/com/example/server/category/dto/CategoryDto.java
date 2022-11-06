package com.example.server.category.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
public class CategoryDto {

    @NotNull
    private final Long id;

    @NotBlank
    private final String name;
}
