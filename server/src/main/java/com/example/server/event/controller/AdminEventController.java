package com.example.server.event.controller;

import com.example.server.event.dto.AdminUpdateEventRequest;
import com.example.server.event.model.EventSearchParams;
import com.example.server.event.model.EventState;
import com.example.server.event.service.EventService;
import com.example.server.event.dto.EventFullDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class AdminEventController {

	private final EventService eventService;

	@GetMapping
	public List<EventFullDto> searchEvents(@RequestParam("users") List<Long> users,
									@RequestParam("states") List<String> states,
									@RequestParam("categories") List<Long> categories,
									@RequestParam("rangeStart") String rangeStart,
									@RequestParam("rangeEnd") String rangeEnd,
									@RequestParam(value = "from", defaultValue = "0") Integer from,
									@RequestParam(value = "size", defaultValue = "10") Integer size) {
		List<EventState> reqStates = states
				.stream()
				.map(EventState::from)
				.filter(reqState -> reqState != EventState.NOT_SUPPORT)
				.collect(Collectors.toList());
		EventSearchParams params =
				new EventSearchParams(null, users, reqStates, categories, null, rangeStart, rangeEnd, null, null);
		return eventService.searchEvents(params, from, size);
	}

	@PutMapping("/{eventId}")
	public EventFullDto editEvent(@PathVariable("eventId") Long eventId,
								  @RequestBody AdminUpdateEventRequest event) {
		return eventService.editEvent(eventId, event);
	}

	@PatchMapping("/{eventId}/publish")
	public EventFullDto publishEvent(@PathVariable("eventId") Long eventId) {
		return eventService.publishEvent(eventId);
	}

	@PatchMapping("/{eventId}/reject")
	public EventFullDto rejectEvent(@PathVariable("eventId") Long eventId) {
		return eventService.rejectEvent(eventId);
	}
}
