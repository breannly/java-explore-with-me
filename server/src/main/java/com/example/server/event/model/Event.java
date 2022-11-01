package com.example.server.event.model;

import com.example.server.category.Category;
import com.example.server.compilations.Compilation;
import com.example.server.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "initiator_id")
	private User initiator;

	private String title;

	private String annotation;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	private String description;

	@Column(name = "created_on")
	private LocalDateTime createdOn = LocalDateTime.now();

	@Column(name = "event_date")
	private LocalDateTime eventDate;

	@ManyToMany(mappedBy = "events")
	private List<Compilation> compilations;

	@OneToOne
	@JoinTable(name = "event_locations",
			joinColumns =
					{@JoinColumn(name = "event_id", referencedColumnName = "event_id")},
			inverseJoinColumns =
					{@JoinColumn(name = "location_id", referencedColumnName = "location_id")})
	private Location location;

	@Column(name = "published_on")
	private LocalDateTime publishedOn;

	@Enumerated(value = EnumType.STRING)
	private EventState state = EventState.PENDING;

	private Boolean paid;

	@Column(name = "part_limit")
	private Integer participantLimit;

	@Column(name = "req_moderation")
	private Boolean requestModeration;
}
