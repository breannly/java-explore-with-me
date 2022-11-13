package com.example.server.comment.dto;

import com.example.server.event.dto.EventTinyDto;
import com.example.server.user.dto.UserShortDto;
import com.fasterxml.jackson.annotation.JsonFormat;
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private final LocalDateTime createdOn;

	private final Boolean updated;
	private final Boolean available;
}
