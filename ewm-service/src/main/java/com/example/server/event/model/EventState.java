package com.example.server.event.model;

public enum EventState {
    PENDING,
    PUBLISHED,
    CANCELED,
    NOT_SUPPORT;

    public static EventState from(String state) {
        for (EventState requestState : EventState.values()) {
            if (requestState.toString().equals(state)) {
                return requestState;
            }
        }
        return NOT_SUPPORT;
    }
}
