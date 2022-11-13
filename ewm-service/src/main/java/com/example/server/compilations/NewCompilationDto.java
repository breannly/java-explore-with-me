package com.example.server.compilations;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

import java.util.List;

@Getter
@Setter
public class NewCompilationDto {

    private List<Long> events;

    private Boolean pinned;

    @NotNull
    private String title;
}
