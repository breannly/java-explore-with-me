package com.example.server.event;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/events")
public class AuthorizedEventController {

    @GetMapping
    public List<Event> getUserEvents(@PathVariable("userId") Long userId) {
        return null;
    }

    @PostMapping
    public Event createEvent(@PathVariable("userId") Long userId) {
        return null;
    }

    @PatchMapping
    public Event updateEvent(@PathVariable("userId") Long userId) {
        return null;
    }

    @GetMapping("/{eventId}")
    public Event getUserEvent(@PathVariable("userId") Long userId,
                              @PathVariable("eventId") Long eventId) {
        return null;
    }

    @PatchMapping("/{eventId}")
    public Event cancelUserEvent(@PathVariable("userId") Long userId,
                                 @PathVariable("eventId") Long eventId) {
        return null;
    }

    @GetMapping("/{eventId}/requests")
    public Request getUserEventRequests(@PathVariable("userId") Long userId,
                                        @PathVariable("eventId") Long eventId) {
        return null;
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public Request confirmEventRequest(@PathVariable("userId") Long userId,
                                       @PathVariable("eventId") Long eventId,
                                       @PathVariable("reqId") Long reqId) {
        return null;
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public Request rejectEventRequest(@PathVariable("userId") Long userId,
                                      @PathVariable("eventId") Long eventId,
                                      @PathVariable("reqId") Long reqId) {
        return null;
    }
}
