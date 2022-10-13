package com.alevel.java.ubike.command;

import com.alevel.java.ubike.command.data.CreateWaypointRequest;
import com.alevel.java.ubike.model.dto.WaypointDTO;
import org.hibernate.SessionFactory;

public class CommandFactoryAddData {

    private final SessionFactory sessionFactory;

    public CommandFactoryAddData(SessionFactory session) {
        this.sessionFactory = session;
    }

    public Command<WaypointDTO> ingestAddWaypoint(CreateWaypointRequest waypointRequest) {
        return new IngestWaypointCommand(sessionFactory, waypointRequest);
    }

}
