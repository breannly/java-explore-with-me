package com.example.server.comment.dto;

import com.example.server.event.model.EventTinyDto;
import com.example.server.user.dto.UserShortDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CommentDto {

	private final Long id;
	private final UserShortDto user;
	private final EventTinyDto event;
	private final String description;
	private final LocalDateTime createdOn;
	private final Boolean updated;
}
