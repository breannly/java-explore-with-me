package com.example.server.event.model;

public enum EventSort {
	EVENT_DATE,
	VIEWS,
	NOT_SUPPORT;

	public static EventSort from(String sort) {
		for (EventSort eventSort : EventSort.values()) {
			if (eventSort.toString().equals(sort)) {
				return eventSort;
			}
		}
		return NOT_SUPPORT;
	}
}
