package com.example.server.event.service;

import com.example.server.client.EventClient;
import com.example.server.category.Category;
import com.example.server.category.CategoryRepository;
import com.example.server.event.dto.AdminUpdateEventRequest;
import com.example.server.event.dto.EventFullDto;
import com.example.server.event.dto.EventMapper;
import com.example.server.event.dto.EventShortDto;
import com.example.server.event.dto.NewEventDto;
import com.example.server.event.dto.ParticipationRequestDto;
import com.example.server.event.dto.RequestMapper;
import com.example.server.event.dto.UpdateEventRequest;
import com.example.server.event.model.*;
import com.example.server.event.repo.EventRepository;
import com.example.server.event.repo.LocationRepository;
import com.example.server.event.repo.RequestRepository;
import com.example.server.exception.ForbiddenAccessException;
import com.example.server.exception.ObjectNotFoundException;
import com.example.server.exception.ValidationException;
import com.example.server.user.User;
import com.example.server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

	private final CategoryRepository categoryRepository;
	private final RequestRepository requestRepository;
	private final EventRepository eventRepository;
	private final UserRepository userRepository;
	private final LocationRepository locationRepository;
	private final EventClient eventClient;

	private static final int MIN_AVAILABLE_TIME_LIMIT = 2;

	@Override
	public List<EventShortDto> getEvents(EventSearchParams params, Integer from, Integer size) {
		Pageable pageable = PageRequest.of(from / size, size);
		List<EventShortDto> eventShortDtos = eventRepository.findEventsWithParams(params, pageable, false)
				.stream()
				.map(event -> {
					Long confirmedRequests = requestRepository.countConfirmedRequests(event.getId());
					Long views = eventClient.getEventViews(event.getCreatedOn(), event.getId());
					return EventMapper.mapToShortDto(event, confirmedRequests, views);
				})
				.collect(Collectors.toList());

		if (params.getOnlyAvailable()) {
			eventShortDtos = eventShortDtos
					.stream()
					.filter(event -> event.getConfirmedRequests() < event.getParticipantLimit())
					.collect(Collectors.toList());
		}
		if (params.getSort() != null) {
			EventSort eventSort = EventSort.from(params.getSort());
			switch (eventSort) {
				case EVENT_DATE:
					eventShortDtos.sort((o1, o2) -> o2.getEventDate().compareTo(o1.getEventDate()));
					break;
				case VIEWS:
					eventShortDtos.sort(Comparator.comparing(EventShortDto::getViews));
					break;
				default:
					throw new ValidationException("Validation failed");
			}
		}

		return eventShortDtos;
	}

	@Override
	public EventFullDto getEvent(Long eventId, String uri, String ip) {
		Event event = eventRepository.findPublishedEvent(eventId).orElseThrow(()
				-> new ObjectNotFoundException("Event not found"));
		eventClient.postStat(uri, ip);
		Long confirmedRequests = requestRepository.countConfirmedRequests(eventId);
		Long views = eventClient.getEventViews(event.getCreatedOn(), event.getId());
		return EventMapper.mapToFullDto(event, confirmedRequests, views);
	}

	@Override
	public List<EventShortDto> getUserEvents(Long userId, Integer from, Integer size) {
		userRepository.findById(userId).orElseThrow(()
				-> new ObjectNotFoundException("User not found"));
		Pageable pageable = PageRequest.of(from / size, size);
		return eventRepository.findEventsByInitiatorId(userId, pageable)
				.stream()
				.map(event -> {
					Long confirmedRequests = requestRepository.countConfirmedRequests(event.getId());
					Long views = eventClient.getEventViews(event.getCreatedOn(), event.getId());
					return EventMapper.mapToShortDto(event, confirmedRequests, views);
				})
				.collect(Collectors.toList());
	}

	@Override
	public EventFullDto createUserEvent(NewEventDto newEventDto, Long userId) {
		User user = userRepository.findById(userId).orElseThrow(()
				-> new ObjectNotFoundException("User not found"));
		Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(()
				-> new ObjectNotFoundException("Category not found"));
		Event event = EventMapper.mapToEvent(newEventDto, user, category);
		locationRepository.save(event.getLocation());
		eventRepository.save(event);
		return EventMapper.mapToFullDto(event, 0L, 0L);
	}

	@Override
	public EventFullDto updateUserEvent(Long userId, UpdateEventRequest updateEvent) {
		userRepository.findById(userId).orElseThrow(()
				-> new ObjectNotFoundException("User not found"));
		Event currentEvent = eventRepository.findById(updateEvent.getEventId()).orElseThrow(()
				-> new ObjectNotFoundException("Event not found"));
		Category category = categoryRepository.findById(updateEvent.getCategory()).orElseThrow(()
				-> new ObjectNotFoundException("Category not found"));
		if (!currentEvent.getInitiator().getId().equals(userId)) {
			throw new ForbiddenAccessException("Access denied");
		}
		if (currentEvent.getState().equals(EventState.PUBLISHED)) {
			throw new ValidationException("Validation failed");
		}
		validateEventDate(currentEvent.getEventDate(), updateEvent.getEventDate());
		fillEvent(currentEvent, updateEvent, category);
		Long confirmedRequests = requestRepository.countConfirmedRequests(currentEvent.getId());
		Long views = eventClient.getEventViews(currentEvent.getCreatedOn(), currentEvent.getId());
		return EventMapper.mapToFullDto(eventRepository.save(currentEvent), confirmedRequests, views);
	}

	private void validateEventDate(LocalDateTime currentEvent, LocalDateTime updateEvent) {
		boolean differentTimes = currentEvent.equals(updateEvent);
		if (Duration.between(currentEvent, updateEvent).toHours() < MIN_AVAILABLE_TIME_LIMIT && !differentTimes) {
			throw new ValidationException("Validation failed");
		}
	}

	private void fillEvent(Event currentEvent, UpdateEventRequest updateEventRequest, Category category) {
		currentEvent.setCategory(category);
		fillEvent(currentEvent, updateEventRequest);
	}

	private void fillEvent(Event currentEvent, UpdateEventRequest updateEvent) {
		if (updateEvent.getAnnotation() != null) {
			currentEvent.setAnnotation(updateEvent.getAnnotation());
		}
		if (updateEvent.getDescription() != null) {
			currentEvent.setDescription(updateEvent.getDescription());
		}
		if (updateEvent.getPaid() != null) {
			currentEvent.setPaid(updateEvent.getPaid());
		}
		if (updateEvent.getParticipantLimit() != null) {
			currentEvent.setParticipantLimit(updateEvent.getParticipantLimit());
		}
		if (updateEvent.getTitle() != null) {
			currentEvent.setTitle(updateEvent.getTitle());
		}
	}

	@Override
	public EventFullDto getUserEvent(Long userId, Long eventId) {
		userRepository.findById(userId).orElseThrow(()
				-> new ObjectNotFoundException("User not found"));
		Event foundEvent = eventRepository.findById(eventId).orElseThrow(()
				-> new ObjectNotFoundException("Event not found"));
		if (!foundEvent.getInitiator().getId().equals(userId)) {
			throw new ForbiddenAccessException("Access denied");
		}
		Long confirmedRequests = requestRepository.countConfirmedRequests(foundEvent.getId());
		Long views = eventClient.getEventViews(foundEvent.getCreatedOn(), foundEvent.getId());
		return EventMapper.mapToFullDto(foundEvent, confirmedRequests, views);
	}

	@Override
	public EventFullDto cancelUserEvent(Long userId, Long eventId) {
		userRepository.findById(userId).orElseThrow(()
				-> new ObjectNotFoundException("User not found"));
		Event foundEvent = eventRepository.findById(eventId).orElseThrow(()
				-> new ObjectNotFoundException("Event not found"));
		if (!foundEvent.getInitiator().getId().equals(userId)) {
			throw new ForbiddenAccessException("Access denied");
		}
		if (!foundEvent.getState().equals(EventState.PENDING)) {
			throw new ValidationException("Validation failed");
		}
		foundEvent.setState(EventState.CANCELED);
		Long confirmedRequests = requestRepository.countConfirmedRequests(foundEvent.getId());
		Long views = eventClient.getEventViews(foundEvent.getCreatedOn(), foundEvent.getId());
		return EventMapper.mapToFullDto(eventRepository.save(foundEvent), confirmedRequests, views);
	}

	@Override
	public List<ParticipationRequestDto> getUserEventRequests(Long userId, Long eventId) {
		userRepository.findById(userId).orElseThrow(()
				-> new ObjectNotFoundException("User not found"));
		Event event = eventRepository.findById(eventId).orElseThrow(()
				-> new ObjectNotFoundException("Event not found"));
		if (!event.getInitiator().getId().equals(userId)) {
			throw new ForbiddenAccessException("Access denied");
		}
		return requestRepository.findRequestsByEventId(eventId)
				.stream()
				.map(RequestMapper::mapToParticipationRequestDto)
				.collect(Collectors.toList());
	}

	@Override
	public ParticipationRequestDto confirmEventRequest(Long userId, Long eventId, Long reqId) {
		userRepository.findById(userId).orElseThrow(()
				-> new ObjectNotFoundException("User not found"));
		Event foundEvent = eventRepository.findById(eventId).orElseThrow(()
				-> new ObjectNotFoundException("Event not found"));
		Request foundRequest = requestRepository.findById(reqId).orElseThrow(()
				-> new ObjectNotFoundException("Request not found"));
		if (!foundEvent.getInitiator().getId().equals(userId)) {
			throw new ForbiddenAccessException("Access denied");
		}
		if (!foundRequest.getEvent().getId().equals(eventId)) {
			throw new ForbiddenAccessException("Access denied");
		}
		if (!foundRequest.getStatus().equals(RequestStatus.PENDING)) {
			throw new ValidationException("Validation failed");
		}
		foundRequest.setStatus(RequestStatus.CONFIRMED);

		return RequestMapper.mapToParticipationRequestDto(requestRepository.save(foundRequest));
	}

	@Override
	public ParticipationRequestDto rejectEventRequest(Long userId, Long eventId, Long reqId) {
		userRepository.findById(userId).orElseThrow(()
				-> new ObjectNotFoundException("User not found"));
		Event foundEvent = eventRepository.findById(eventId).orElseThrow(()
				-> new ObjectNotFoundException("Event not found"));
		Request foundRequest = requestRepository.findById(reqId).orElseThrow(()
				-> new ObjectNotFoundException("Request not found"));
		if (!foundEvent.getInitiator().getId().equals(userId)) {
			throw new ForbiddenAccessException("Access denied");
		}
		if (!foundRequest.getEvent().getId().equals(eventId)) {
			throw new ForbiddenAccessException("Access denied");
		}
		if (!foundRequest.getStatus().equals(RequestStatus.PENDING)) {
			throw new ValidationException("Validation failed");
		}
		foundRequest.setStatus(RequestStatus.REJECTED);

		return RequestMapper.mapToParticipationRequestDto(requestRepository.save(foundRequest));
	}

	@Override
	public List<EventFullDto> searchEvents(EventSearchParams params, Integer from, Integer size) {
		Pageable pageable = PageRequest.of(from / size, size);
		return eventRepository.findEventsWithParams(params, pageable, true)
				.stream()
				.map(event -> {
					Long confirmedRequests = requestRepository.countConfirmedRequests(event.getId());
					Long views = eventClient.getEventViews(event.getCreatedOn(), event.getId());
					return EventMapper.mapToFullDto(event, confirmedRequests, views);
				})
				.collect(Collectors.toList());
	}

	@Override
	public EventFullDto editEvent(Long eventId, AdminUpdateEventRequest updateEvent) {
		Event event = eventRepository.findById(eventId).orElseThrow(()
				-> new ObjectNotFoundException("Event not found"));
		Category category = categoryRepository.findById(updateEvent.getCategory()).orElseThrow(()
				-> new ObjectNotFoundException("Category not found"));
		fillEvent(event, updateEvent, category);
		Long confirmedRequests = requestRepository
				.countConfirmedRequests(eventId);
		Long views = eventClient.getEventViews(event.getCreatedOn(), event.getId());
		return EventMapper.mapToFullDto(event, confirmedRequests, views);
	}

	private void fillEvent(Event currentEvent, AdminUpdateEventRequest updateEvent, Category category) {
		currentEvent.setCategory(category);
		fillEvent(currentEvent, updateEvent);
	}

	private void fillEvent(Event currentEvent, AdminUpdateEventRequest updateEvent) {
		if (updateEvent.getAnnotation() != null) {
			currentEvent.setAnnotation(updateEvent.getAnnotation());
		}
		if (updateEvent.getDescription() != null) {
			currentEvent.setDescription(updateEvent.getDescription());
		}
		if (updateEvent.getEventDate() != null) {
			currentEvent.setEventDate(updateEvent.getEventDate());
		}
		if (updateEvent.getLocation() != null) {
			currentEvent.setLocation(updateEvent.getLocation());
		}
		if (updateEvent.getPaid() != null) {
			currentEvent.setPaid(updateEvent.getPaid());
		}
		if (updateEvent.getParticipantLimit() != null) {
			currentEvent.setParticipantLimit(updateEvent.getParticipantLimit());
		}
		if (updateEvent.getRequestModeration() != null) {
			currentEvent.setRequestModeration(updateEvent.getRequestModeration());
		}
		if (updateEvent.getTitle() != null) {
			currentEvent.setTitle(updateEvent.getTitle());
		}
	}

	@Override
	public EventFullDto publishEvent(Long eventId) {
		Event event = eventRepository.findById(eventId).orElseThrow(()
				-> new ObjectNotFoundException("Event not found"));
		if (event.getState() != EventState.PENDING) {
			throw new ValidationException("Validation failed");
		}
		event.setState(EventState.PUBLISHED);
		event.setPublishedOn(LocalDateTime.now());
		Long confirmedRequests = requestRepository.countConfirmedRequests(eventId);
		Long views = 0L;
		return EventMapper.mapToFullDto(eventRepository.save(event), confirmedRequests, views);
	}

	@Override
	public EventFullDto rejectEvent(Long eventId) {
		Event event = eventRepository.findById(eventId).orElseThrow(()
				-> new ObjectNotFoundException("Event not found"));
		if (event.getState() != EventState.PENDING) {
			throw new ValidationException("Validation failed");
		}
		event.setState(EventState.CANCELED);
		Long confirmedRequests = requestRepository.countConfirmedRequests(eventId);
		Long views = 0L;
		return EventMapper.mapToFullDto(eventRepository.save(event), confirmedRequests, views);
	}
}
