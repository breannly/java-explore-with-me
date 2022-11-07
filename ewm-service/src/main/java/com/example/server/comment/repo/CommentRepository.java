package com.example.server.comment.repo;

import com.example.server.comment.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query(value = "SELECT c " +
					"FROM Comment c " +
					"WHERE c.event.id = :eventId AND c.available = true")
	List<Comment> findCommentsByEventId(Long eventId, Pageable pageable);

	@Query(value = "SELECT c " +
			"FROM Comment c " +
			"WHERE c.owner.id = :userId")
	List<Comment> findCommentsByUserId(Long userId, Pageable pageable);
}
