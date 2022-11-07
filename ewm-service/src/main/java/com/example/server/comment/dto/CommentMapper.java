package com.example.server.comment.dto;

import com.example.server.comment.model.Comment;
import com.example.server.event.model.Event;
import com.example.server.event.model.EventTinyDto;
import com.example.server.user.User;
import com.example.server.user.dto.UserShortDto;

public class CommentMapper {

	public static Comment mapToComment(NewCommentDto newCommentDto, User user, Event event) {
		Comment comment = new Comment();
		comment.setOwner(user);
		comment.setEvent(event);
		comment.setDescription(newCommentDto.getDescription());

		return comment;
	}

	public static CommentShortDto mapToCommentShortDto(Comment comment) {
		return new CommentShortDto(
				comment.getId(),
				new UserShortDto(
						comment.getOwner().getId(),
						comment.getOwner().getName()
				),
				comment.getDescription(),
				comment.getAvailable()
		);
	}

	public static CommentDto mapToCommentDto(Comment comment) {
		return new CommentDto(
				comment.getId(),
				new UserShortDto(
						comment.getOwner().getId(),
						comment.getOwner().getName()
				),
				new EventTinyDto(
						comment.getEvent().getId(),
						comment.getEvent().getTitle()
				),
				comment.getDescription(),
				comment.getCreatedOn(),
				comment.getLastUpdate() != null
		);
	}
}
