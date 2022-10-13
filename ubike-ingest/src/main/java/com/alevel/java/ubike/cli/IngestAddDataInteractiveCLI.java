package com.alevel.java.ubike.cli;

import com.alevel.java.ubike.command.Command;
import com.alevel.java.ubike.command.CommandFactoryAddData;
import com.alevel.java.ubike.command.IngestWaypointCommand;
import com.alevel.java.ubike.command.data.CreateWaypointRequest;
import com.alevel.java.ubike.exceptions.UbikeIngestException;
import com.alevel.java.ubike.model.dto.Coordinates;
import com.alevel.java.ubike.model.dto.RideDTO;
import com.alevel.java.ubike.model.dto.WaypointDTO;

import java.util.Objects;
import java.util.Scanner;

public class IngestAddDataInteractiveCLI {

    private final CommandFactoryAddData commandFactoryAddData;

    public IngestAddDataInteractiveCLI(CommandFactoryAddData commandFactoryAddData) {
        this.commandFactoryAddData = commandFactoryAddData;
    }

    public void run() throws UbikeIngestException {

        var scanner = new Scanner(System.in);

        System.out.println("Enter Waypoint altitude:");
        double altitude = scanner.nextDouble();

        System.out.println("Enter Waypoint longitude:");
        double longitude = scanner.nextDouble();

        Coordinates coordinates = new Coordinates(altitude, longitude);

        Command<WaypointDTO> command = commandFactoryAddData.ingestAddWaypoint(new CreateWaypointRequest(
                coordinates.altitude(),
                coordinates.longitude()
        ));

        WaypointDTO waypoint= command.execute();

        System.out.printf("Created Waypoint int DataBase with altitude: %f, longitude: %f",
                waypoint.altitude(),
                waypoint.longitude());



    }

}
