package com.example.server.comment.model;

import com.example.server.event.model.Event;
import com.example.server.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;

	@NotNull
	@Size(max = 1000)
	private String description;

	@Column(name = "created_on")
	private LocalDateTime createdOn = LocalDateTime.now();

	@Column(name = "last_update")
	private LocalDateTime lastUpdate;

	private Boolean available = true;
}
