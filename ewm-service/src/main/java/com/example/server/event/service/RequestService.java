package com.example.server.event.service;

import com.example.server.event.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

	List<ParticipationRequestDto> getUserRequests(Long userId);

	ParticipationRequestDto createUserRequest(Long userId, Long eventId);

	ParticipationRequestDto cancelUserRequest(Long userId, Long requestId);
}
