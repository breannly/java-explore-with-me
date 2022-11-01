package com.example.server.event.dto;

import com.example.server.event.model.Location;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminUpdateEventRequest {

	private String annotation;
	private Long category;
	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime eventDate;

	private Location location;
	private Boolean paid;
	private Integer participantLimit;
	private Boolean requestModeration;
	private String title;
}
