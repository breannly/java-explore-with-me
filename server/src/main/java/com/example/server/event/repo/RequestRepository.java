package com.example.server.event.repo;

import com.example.server.event.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

	@Query(value =
			"SELECT COUNT(R) " +
			"FROM Request R " +
			"WHERE R.event.id = :eventId AND R.status = 'CONFIRMED'")
	Long countConfirmedRequests(Long eventId);

	List<Request> findRequestsByEventId(Long eventId);

	List<Request> findRequestsByRequesterId(Long userId);
}
