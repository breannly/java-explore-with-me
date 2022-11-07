package com.example.server.comment;

import com.example.server.comment.dto.*;

import java.util.List;

public interface CommentService {

	List<CommentShortDto> getEventComments(Long eventId, Integer from, Integer size);

	List<CommentDto> getUserComments(Long userId, Integer from, Integer size);

	CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto);

	CommentDto updateComment(Long userId, Long commentId, NewCommentDto newCommentDto);

	void deleteComment(Long commentId);

}
