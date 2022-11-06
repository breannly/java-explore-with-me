package com.example.server.event.dto;

import com.example.server.category.Category;
import com.example.server.category.dto.CategoryDto;
import com.example.server.event.model.Event;
import com.example.server.user.User;
import com.example.server.user.dto.UserShortDto;

public class EventMapper {

	public static Event mapToEvent(NewEventDto newEventDto, User user, Category category) {
		Event event = new Event();
		event.setInitiator(user);
		event.setTitle(newEventDto.getTitle());
		event.setAnnotation(newEventDto.getAnnotation());
		event.setCategory(category);
		event.setDescription(newEventDto.getDescription());
		event.setEventDate(newEventDto.getEventDate());
		event.setLocation(newEventDto.getLocation());
		event.setPaid(newEventDto.getPaid());
		event.setParticipantLimit(newEventDto.getParticipantLimit());
		event.setRequestModeration(newEventDto.getRequestModeration());

		return event;
	}

	public static EventFullDto mapToFullDto(Event event, Long confirmedRequests, Long views) {
		return new EventFullDto(
				event.getId(),
				event.getCreatedOn(),
				event.getAnnotation(),
				event.getDescription(),
				new CategoryDto(
						event.getCategory().getId(),
						event.getCategory().getName()
				),
				event.getState(),
				confirmedRequests,
				event.getEventDate(),
				new LocationDto(
						event.getLocation().getLat(),
						event.getLocation().getLon()
				),
				event.getPublishedOn(),
				new UserShortDto(
						event.getInitiator().getId(),
						event.getInitiator().getName()
				),
				event.getPaid(),
				event.getParticipantLimit(),
				event.getRequestModeration(),
				event.getTitle(),
				views
		);
	}

	public static EventShortDto mapToShortDto(Event event, Long confirmedRequests, Long views) {
		return new EventShortDto(
				event.getId(),
				event.getAnnotation(),
				new CategoryDto(
						event.getCategory().getId(),
						event.getCategory().getName()
				),
				confirmedRequests,
				event.getParticipantLimit(),
				event.getEventDate(),
				new UserShortDto(
						event.getInitiator().getId(),
						event.getInitiator().getName()
				),
				event.getPaid(),
				event.getTitle(),
				views
		);
	}
}
