package com.alevel.java.ubike.command;

import com.alevel.java.ubike.command.data.CreateRiderRequest;
import com.alevel.java.ubike.command.data.CreateVehicleRequest;
import com.alevel.java.ubike.command.data.CreateWaypointRequest;
import com.alevel.java.ubike.model.dto.Coordinates;
import com.alevel.java.ubike.model.dto.RiderDTO;
import com.alevel.java.ubike.model.dto.VehicleDTO;
import org.hibernate.SessionFactory;

public class CommandFactoryAddData {

    private final SessionFactory sessionFactory;

    public CommandFactoryAddData(SessionFactory session) {
        this.sessionFactory = session;
    }

    public Command<Coordinates> ingestAddWaypoint(CreateWaypointRequest waypointRequest) {
        return new IngestWaypointCommand(sessionFactory, waypointRequest);
    }

    public Command<RiderDTO> ingestAddRider(CreateRiderRequest riderRequest) {
        return new IngestRiderCommand(sessionFactory, riderRequest);
    }

    public Command<VehicleDTO> ingestAddVehicle(CreateVehicleRequest vehicleRequest) {
        return new IngestVehicleCommand(sessionFactory, vehicleRequest);
    }
}
