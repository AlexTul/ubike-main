package com.alevel.java.ubike.command;

import com.alevel.java.ubike.command.data.CreateVehicleRequest;
import com.alevel.java.ubike.exceptions.UbikeIngestException;
import com.alevel.java.ubike.model.Vehicle;
import com.alevel.java.ubike.model.Waypoint;
import com.alevel.java.ubike.model.dto.VehicleDTO;
import jakarta.persistence.EntityTransaction;
import org.hibernate.SessionFactory;

public class IngestVehicleCommand implements Command<VehicleDTO> {

    private final SessionFactory sessionFactory;

    private final CreateVehicleRequest vehicleRequest;

    public IngestVehicleCommand(SessionFactory sessionFactory, CreateVehicleRequest vehicleRequest) {
        this.sessionFactory = sessionFactory;
        this.vehicleRequest = vehicleRequest;
    }

    @Override
    public VehicleDTO execute() throws UbikeIngestException {

        if (vehicleRequest.locationId() == null) {
            throw new UbikeIngestException("No Vehicle's waypoint location id");
        }

        EntityTransaction tx = null;

        try (var session = sessionFactory.openSession()) {

            tx = session.beginTransaction();

            Waypoint waypoint = session.find(Waypoint.class, vehicleRequest.locationId());

            var vehicle = new Vehicle();
            vehicle.setLocation(waypoint);

            session.persist(vehicle);

            var result = new VehicleDTO(
                    vehicle.getLocation().getId()
            );

            tx.commit();

            return result;

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new UbikeIngestException(e);
        }

    }

}
