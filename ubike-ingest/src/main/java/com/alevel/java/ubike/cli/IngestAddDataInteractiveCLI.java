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

        Mode mode = getMode(scanner);

        var output = switch (mode) {
            case ADDWaypoint -> getWaypointFromUser(scanner); // Input Waypoint
            case ADDRider -> getRiderFromUser(scanner); // Input Rider
            case ADDVehicle -> getVehicleFromUser(scanner); // Input Vehicle's waypoint location id
        };

        // Output created
        outputResultAddFromUser(output);

    }

    private static void outputResultAddFromUser(Record output) {

        // Output created Waypoint in DataBase
        if (output instanceof Coordinates) {
            System.out.printf("Created Waypoint in DataBase with altitude: %f, longitude: %f",
                    ((Coordinates) output).altitude(),
                    ((Coordinates) output).longitude());
        }

        // Output created Rider in DataBase
        if (output instanceof RiderDTO) {
            System.out.printf("Created Rider in DataBase with nickname: %s",
            ((RiderDTO) output).nickname());
        }

        // Output created Vehicle's waypoint location id
        if (output instanceof VehicleDTO) {
            System.out.printf("Created Vehicle's waypoint location id in DataBase: %d",
                    ((VehicleDTO) output).locationId());
        }

    }

    private VehicleDTO getVehicleFromUser(Scanner scanner) throws UbikeIngestException {
        System.out.println("Enter Vehicle's waypoint location id:");
        long vehicleId = scanner.nextLong();

        Command<VehicleDTO> commandVehicle = commandFactoryAddData.ingestAddVehicle(new CreateVehicleRequest(
                vehicleId
        ));

        return commandVehicle.execute();
    }

    private RiderDTO getRiderFromUser(Scanner scanner) throws UbikeIngestException {
        System.out.println("Enter Rider's nickname:");
        String nickname = scanner.nextLine();

        Command<RiderDTO> commandRider = commandFactoryAddData.ingestAddRider(new CreateRiderRequest(
                nickname
        ));

        return commandRider.execute();
    }

    private Coordinates getWaypointFromUser(Scanner scanner) throws UbikeIngestException {

        System.out.println("Enter Waypoint's altitude:");
        double altitude = scanner.nextDouble();

        System.out.println("Enter Waypoint's longitude:");
        double longitude = scanner.nextDouble();

        Coordinates coordinates = new Coordinates(altitude, longitude);

        Command<Coordinates> commandWaypoint = commandFactoryAddData.ingestAddWaypoint(new CreateWaypointRequest(
                coordinates.altitude(),
                coordinates.longitude()
        ));

        return commandWaypoint.execute();
    }

    private static Mode getMode(Scanner scanner) {
        Mode mode;

        do {
            System.out.printf("Input mode:%n0: add Waypoint%n1: add Rider%n2: add Vehicle%n");
            mode = switch (scanner.nextInt()) {
                case 0 -> Mode.ADDWaypoint;
                case 1 -> Mode.ADDRider;
                case 2 -> Mode.ADDVehicle;
                default -> null;
            };
            scanner.nextLine();

        } while (mode == null);

        return mode;
    }

    private enum Mode {
        ADDWaypoint, ADDRider, ADDVehicle
    }

}

