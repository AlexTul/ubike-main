package com.alevel.java.ubike.command.data;

public record CreateWaypointRequest(
        Double altitude,
        Double longitude
) {
}
