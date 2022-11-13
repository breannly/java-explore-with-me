package com.example.server.event.controller;

import com.example.server.event.dto.EventShortDto;
import com.example.server.event.model.EventSearchParams;
import com.example.server.event.service.EventService;
import com.example.server.event.dto.EventFullDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

	private final EventService eventService;

	@GetMapping
	public List<EventShortDto> getEvents(@RequestParam("text") String text,
										 @RequestParam("categories") List<Long> categories,
										 @RequestParam("paid") Boolean paid,
										 @RequestParam("rangeStart") String rangeStart,
										 @RequestParam("rangeEnd") String rangeEnd,
										 @RequestParam(value = "onlyAvailable", defaultValue = "false")
										 Boolean onlyAvailable,
										 @RequestParam("sort") String sort,
										 @RequestParam(value = "from", defaultValue = "0") Integer from,
										 @RequestParam(value = "size", defaultValue = "10") Integer size) {
		EventSearchParams params =
				new EventSearchParams(text, null, null, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort);
		return eventService.getEvents(params, from, size);
	}

	@GetMapping("/{eventId}")
	public EventFullDto getEvent(@PathVariable("eventId") Long eventId,
								 HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		String userIp = request.getRemoteAddr();
		return eventService.getEvent(eventId, requestUri, userIp);
	}
}
