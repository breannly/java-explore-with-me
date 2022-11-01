package com.example.server.event;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    @GetMapping
    public Event getEvents() {
        return null;
    }

    @GetMapping("/{eventId}")
    public Event getEvent(@PathVariable("eventId") Long eventId) {
        return null;
    }
}
