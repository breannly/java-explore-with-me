package com.example.server.event.dto;

import com.example.server.event.model.EventState;
import com.example.server.event.model.RequestStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ParticipationRequestDto {

	private final Long id;

	@JsonFormat(pattern = "yy:MM:dd HH:mm:ss")
	private final LocalDateTime created;

	private final Long event;
	private final Long requester;
	private final RequestStatus status; // create new enum for status  {PENDING, CANCELLED, CONFIRMED}
}
