package com.example.server.event.dto;

import com.example.server.category.dto.CategoryDto;
import com.example.server.user.dto.UserShortDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class EventShortDto {

    private final Long id;

    private final String annotation;

    private final CategoryDto category;

    private final Long confirmedRequests;

    @JsonIgnore
    private final Integer participantLimit;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime eventDate;

    private final UserShortDto initiator;

    private final Boolean paid;

    private final String title;

    private final Long views;
}
