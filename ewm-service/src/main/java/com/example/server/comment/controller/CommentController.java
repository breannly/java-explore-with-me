package com.example.server.comment.controller;

import com.example.server.comment.CommentService;
import com.example.server.comment.dto.CommentShortDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@GetMapping("/{eventId}")
	public List<CommentShortDto> getComments(@PathVariable("eventId") Long eventId,
											 @RequestParam("from") Integer from,
											 @RequestParam("from") Integer size) {
		return commentService.getEventComments(eventId, from, size);
	}
}
