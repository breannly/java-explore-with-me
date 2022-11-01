package com.example.server.compilations;

import com.example.server.event.model.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compilations")
public class Compilation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "compilation_id")
	private Long id;

	private String title;

	private Boolean pinned;

	@ManyToMany
	@JoinTable(name = "compilation_events",
			joinColumns =
					{@JoinColumn(name = "compilation_id", referencedColumnName = "compilation_id")},
			inverseJoinColumns =
					{@JoinColumn(name = "event_id", referencedColumnName = "event_id")})
	private List<Event> events;
}
