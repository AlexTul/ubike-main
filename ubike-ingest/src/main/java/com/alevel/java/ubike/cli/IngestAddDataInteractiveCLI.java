package com.alevel.java.ubike.cli;

import com.alevel.java.ubike.command.Command;
import com.alevel.java.ubike.command.CommandFactoryAddData;
import com.alevel.java.ubike.command.data.CreateRiderRequest;
import com.alevel.java.ubike.command.data.CreateVehicleRequest;
import com.alevel.java.ubike.command.data.CreateWaypointRequest;
import com.alevel.java.ubike.exceptions.UbikeIngestException;
import com.alevel.java.ubike.model.dto.Coordinates;
import com.alevel.java.ubike.model.dto.RiderDTO;
import com.alevel.java.ubike.model.dto.VehicleDTO;

import java.util.Scanner;

public class IngestAddDataInteractiveCLI {

    private final CommandFactoryAddData commandFactoryAddData;

    public IngestAddDataInteractiveCLI(CommandFactoryAddData commandFactoryAddData) {
        this.commandFactoryAddData = commandFactoryAddData;
    }

    public void run() throws UbikeIngestException {

        var scanner = new Scanner(System.in);

        // Input Waypoint
        System.out.println("Enter Waypoint's altitude:");
        double altitude = scanner.nextDouble();

        System.out.println("Enter Waypoint's longitude:");
        double longitude = scanner.nextDouble();

        Coordinates coordinates = new Coordinates(altitude, longitude);

        Command<Coordinates> commandWaypoint = commandFactoryAddData.ingestAddWaypoint(new CreateWaypointRequest(
                coordinates.altitude(),
                coordinates.longitude()
        ));

        Coordinates waypoint= commandWaypoint.execute();

        // Input Rider
        System.out.println("Enter Rider's nickname:");
        String nickname = scanner.nextLine();

        Command<RiderDTO> commandRider = commandFactoryAddData.ingestAddRider(new CreateRiderRequest(
                nickname
        ));

        RiderDTO rider = commandRider.execute();

        // Input Vehicle's waypoint location id
        System.out.println("Enter Vehicle's waypoint location id:");
        long vehicleId = scanner.nextLong();

        Command<VehicleDTO> commandVehicle = commandFactoryAddData.ingestAddVehicle(new CreateVehicleRequest(
                vehicleId
        ));

        VehicleDTO vehicle = commandVehicle.execute();

        // Output created Waypoint in DataBase
        System.out.printf("Created Waypoint in DataBase with altitude: %f, longitude: %f",
                waypoint.altitude(),
                waypoint.longitude());

        // Output created Rider in DataBase
        System.out.printf("Created Rider in DataBase with nickname: %s",
                rider.nickname());

        // Output created Vehicle's waypoint location id
        System.out.printf("Created Vehicle's waypoint location id in DataBase: %d",
                vehicle.locationId());
    }

}
