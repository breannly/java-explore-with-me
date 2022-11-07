package com.example.server.comment.controller;

import com.example.server.comment.dto.CommentDto;
import com.example.server.comment.CommentService;
import com.example.server.comment.dto.NewCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/{userId}/comments")
@RequiredArgsConstructor
public class AuthorizedCommentController {

	private final CommentService commentService;

	@PostMapping("/{eventId}")
	public CommentDto addComment(@PathVariable("userId") Long userId,
								 @PathVariable("eventId") Long eventId,
								 @Valid @RequestBody NewCommentDto newCommentDto) {
		return commentService.addComment(userId, eventId, newCommentDto);
	}

	@PatchMapping("/{commentId}")
	public CommentDto updateComment(@PathVariable("userId") Long userId,
									@PathVariable("commentId") Long commentId,
									@Valid @RequestBody NewCommentDto newCommentDto) {

		return commentService.updateComment(userId, commentId, newCommentDto);
	}
}
