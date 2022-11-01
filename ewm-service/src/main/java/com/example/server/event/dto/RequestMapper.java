package com.example.server.event.dto;

import com.example.server.event.model.Event;
import com.example.server.event.model.Request;
import com.example.server.event.model.RequestStatus;
import com.example.server.user.User;

import java.time.LocalDateTime;

public class RequestMapper {

	public static ParticipationRequestDto mapToParticipationRequestDto(Request request) {
		return new ParticipationRequestDto(
				request.getId(),
				request.getCreated(),
				request.getEvent().getId(),
				request.getRequester().getId(),
				request.getStatus()
		);
	}

	public static Request mapToRequest(User user, Event event, LocalDateTime now, RequestStatus status) {
		Request request = new Request();
		request.setRequester(user);
		request.setEvent(event);
		request.setCreated(now);
		request.setStatus(status);
		return request;
	}
}
