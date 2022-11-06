package com.example.server.event.model;

import com.example.server.category.Category;
import com.example.server.compilations.Compilation;
import com.example.server.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

	@NotNull
	@ManyToOne
	@JoinColumn(name = "initiator_id")
	private User initiator;

	@NotBlank
	@Size(max = 120)
	private String title;

	@NotBlank
	@Size(max = 2000)
	private String annotation;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@NotBlank
	@Size(max = 7000)
	private String description;

	@Column(name = "created_on")
	private LocalDateTime createdOn = LocalDateTime.now();

	@Column(name = "event_date")
	private LocalDateTime eventDate;

	@ManyToMany(mappedBy = "events")
	private List<Compilation> compilations;

	@OneToOne
	@JoinTable(name = "event_locations",
			joinColumns = {
					@JoinColumn(name = "event_id", referencedColumnName = "event_id")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "location_id", referencedColumnName = "location_id")
			})
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
