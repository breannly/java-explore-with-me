package com.example.server.event.dto;

import com.example.server.category.dto.CategoryDto;
import com.example.server.event.model.EventState;
import com.example.server.user.dto.UserShortDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class EventFullDto {

    private final Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdOn;

    @NotNull
    private final String annotation;

    private final String description;

    @NotNull
    private final CategoryDto category;

    private final EventState state;

    private final Long confirmedRequests;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime eventDate;

    private final LocationDto location;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime publishedOn;

    @NotNull
    private final UserShortDto initiator;

    @NotNull
    private final Boolean paid;

    private final Integer participantLimit;

    private final Boolean requestModeration;

    @NotNull
    private final String title;

    private final Long views;
}
