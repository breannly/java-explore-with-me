package com.example.server.event.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EventTinyDto {

	private final Long id;
	private final String title;
}
