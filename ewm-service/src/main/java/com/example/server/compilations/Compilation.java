package com.example.server.compilations;

import com.example.server.event.model.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

	@NotBlank
	@Size(max = 100)
	private String title;

	@NotNull
	private Boolean pinned;

	@ManyToMany
	@JoinTable(name = "compilation_events",
			joinColumns = {
					@JoinColumn(name = "compilation_id", referencedColumnName = "compilation_id")
			},
			inverseJoinColumns = {
					@JoinColumn(name = "event_id", referencedColumnName = "event_id")
			})
	private List<Event> events;
}
