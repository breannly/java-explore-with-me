package com.example.server.event.model;

public enum RequestStatus {
	PENDING,
	CONFIRMED,
	CANCELED,
	REJECTED,
	NOT_SUPPORT;

	public RequestStatus from(String status) {
		for (RequestStatus reqStatus : RequestStatus.values()) {
			if (reqStatus.toString().equals(status)) {
				return reqStatus;
			}
		}
		return NOT_SUPPORT;
	}
}
