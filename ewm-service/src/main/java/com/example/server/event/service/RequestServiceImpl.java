package com.example.server.event.service;

import com.example.server.event.dto.ParticipationRequestDto;
import com.example.server.event.dto.RequestMapper;
import com.example.server.event.model.Event;
import com.example.server.event.model.Request;
import com.example.server.event.model.RequestStatus;
import com.example.server.event.repo.EventRepository;
import com.example.server.event.repo.RequestRepository;
import com.example.server.exception.ObjectNotFoundException;
import com.example.server.exception.ValidationException;
import com.example.server.user.User;
import com.example.server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

	private final RequestRepository requestRepository;
	private final EventRepository eventRepository;
	private final UserRepository userRepository;

	@Override
	public List<ParticipationRequestDto> getUserRequests(Long userId) {
		return requestRepository.findRequestsByRequesterId(userId)
				.stream()
				.map(RequestMapper::mapToParticipationRequestDto)
				.collect(Collectors.toList());
	}

	@Override
	public ParticipationRequestDto createUserRequest(Long userId, Long eventId) {
		User user = userRepository.findById(userId).orElseThrow(()
				-> new ObjectNotFoundException("User not found"));
		Event event = eventRepository.findById(eventId).orElseThrow(()
				-> new ObjectNotFoundException("Event not found"));
		Request request = RequestMapper.mapToRequest(user, event, LocalDateTime.now(), RequestStatus.PENDING);
		return RequestMapper.mapToParticipationRequestDto(requestRepository.save(request));
	}

	@Override
	public ParticipationRequestDto cancelUserRequest(Long userId, Long requestId) {
		userRepository.findById(userId).orElseThrow(()
				-> new ObjectNotFoundException("User not found"));
		Request request = requestRepository.findById(requestId).orElseThrow(()
				-> new ObjectNotFoundException("Request not found"));
		if (!request.getRequester().getId().equals(userId)) {
			throw new ValidationException("Validation failed");
		}
		request.setStatus(RequestStatus.CANCELED);

		return RequestMapper.mapToParticipationRequestDto(requestRepository.save(request));
	}
}
