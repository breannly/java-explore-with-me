package com.example.server.comment.dto;

import com.example.server.user.dto.UserShortDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentShortDto {

    private final Long id;
    private final UserShortDto user;
    private final String description;
    private final Boolean available;
}
