package com.example.server.comment;

import com.example.server.comment.dto.*;
import com.example.server.comment.model.Comment;
import com.example.server.comment.repo.CommentRepository;
import com.example.server.event.model.Event;
import com.example.server.event.repo.EventRepository;
import com.example.server.exception.ForbiddenAccessException;
import com.example.server.exception.ObjectNotFoundException;
import com.example.server.exception.ValidationException;
import com.example.server.user.User;
import com.example.server.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final UserRepository userRepository;
	private final EventRepository eventRepository;
	private final CommentRepository commentRepository;

	private static final Boolean NOT_AVAILABLE = false;

	@Override
	public List<CommentShortDto> getEventComments(Long eventId, Integer from, Integer size) {
		eventRepository.findById(eventId).orElseThrow(()
				-> new ObjectNotFoundException("Event not found"));
		Pageable pageable = PageRequest.of(from / size, size);
		return commentRepository.findCommentsByEventId(eventId, pageable)
				.stream()
				.map(CommentMapper::mapToCommentShortDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<CommentDto> getUserComments(Long userId, Integer from, Integer size) {
		userRepository.findById(userId).orElseThrow(()
				-> new ObjectNotFoundException("User not found"));
		Pageable pageable = PageRequest.of(from / size, size);
		return commentRepository.findCommentsByUserId(userId, pageable)
				.stream()
				.map(CommentMapper::mapToCommentDto)
				.collect(Collectors.toList());
	}

	@Override
	public CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
		User user = userRepository.findById(userId).orElseThrow(()
				-> new ObjectNotFoundException("User not found"));
		Event event = eventRepository.findById(eventId).orElseThrow(()
				-> new ObjectNotFoundException("Event not found"));
		Comment comment = CommentMapper.mapToComment(newCommentDto, user, event);
		return CommentMapper.mapToCommentDto(commentRepository.save(comment));
	}

	@Override
	public CommentDto updateComment(Long userId, Long commentId, NewCommentDto newCommentDto) {
		userRepository.findById(userId).orElseThrow(()
				-> new ObjectNotFoundException("User not found"));
		Comment comment = commentRepository.findById(commentId).orElseThrow(()
				-> new ObjectNotFoundException("Comment not found"));
		if (!comment.getOwner().getId().equals(userId)) {
			throw new ForbiddenAccessException("Access denied");
		}
		if (Duration.between(comment.getCreatedOn(), LocalDateTime.now()).toHours() > 24) {
			throw new ValidationException("Validation failed");
		}
		fillComment(comment, newCommentDto);
		return CommentMapper.mapToCommentDto(commentRepository.save(comment));
	}

	@Override
	public void deleteComment(Long commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(()
				-> new ObjectNotFoundException("Comment not found"));
		comment.setAvailable(NOT_AVAILABLE);
		commentRepository.save(comment);
	}

	private void fillComment(Comment comment, NewCommentDto newCommentDto) {
		comment.setLastUpdate(LocalDateTime.now());
		comment.setDescription(newCommentDto.getDescription());
	}
}
