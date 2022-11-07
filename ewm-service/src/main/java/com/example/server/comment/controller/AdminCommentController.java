package com.example.server.comment.controller;

import com.example.server.comment.CommentService;
import com.example.server.comment.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
public class AdminCommentController {

	private final CommentService commentService;

	@GetMapping("/users/{userId}")
	public List<CommentDto> getComments(@PathVariable("userId") Long userId,
										@RequestParam("from") Integer from,
										@RequestParam("size") Integer size) {
		return commentService.getUserComments(userId, from, size);
	}

	@DeleteMapping("/{commentId}")
	public void deleteComment(@PathVariable("commentId") Long commentId) {
		commentService.deleteComment(commentId);
	}
}
