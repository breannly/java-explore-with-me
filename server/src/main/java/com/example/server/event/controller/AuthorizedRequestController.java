package com.example.server.event;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/requests")
public class AuthorizedRequestController {

    @GetMapping
    public List<Request> getUserRequests(@PathVariable("userId") Long userId) {
        return null;
    }

    @PostMapping
    public Request getUserRequest(@PathVariable("userId") Long userId,
                                  @RequestParam("eventId") Long eventId) {
        return null;
    }

    @PatchMapping
    public Request cancelUserRequest(@PathVariable("userId") Long userId,
                                     @RequestParam("requestId") Long requestId) {
        return null;
    }
}
