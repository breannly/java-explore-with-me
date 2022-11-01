package com.example.server.event.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LocationDto {

	private final Float lat;
	private final Float lon;
}
