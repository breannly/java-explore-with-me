package com.example.server.compilations;

import com.example.server.event.dto.EventShortDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CompilationDto {

    @NotNull
    private final Long id;

    private final List<EventShortDto> events;

    @NotNull
    private final Boolean pinned;

    @NotNull
    private final String title;
}
