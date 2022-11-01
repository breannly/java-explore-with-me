package com.example.server.event;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/events")
public class AdminEventController {

    @GetMapping
    public List<Event> searchEvents() {
        return null;
    }

    @PutMapping
    public Event editEvent() {
        return null;
    }

    @PatchMapping
    public Event publishEvent() {
        return null;
    }

    @PatchMapping
    public Event rejectEvent() {
        return null;
    }
}
