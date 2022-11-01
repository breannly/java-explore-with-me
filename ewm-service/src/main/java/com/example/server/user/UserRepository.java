package com.example.server.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT U " +
			"FROM User U " +
			"WHERE U.id IN :userIds")
	List<User> findUsersByIds(List<Long> userIds);
}
