package com.alevel.java.ubike.command;

import com.alevel.java.ubike.command.data.CreateWaypointRequest;
import com.alevel.java.ubike.exceptions.UbikeIngestException;
import com.alevel.java.ubike.model.Waypoint;
import com.alevel.java.ubike.model.dto.Coordinates;
import jakarta.persistence.EntityTransaction;
import org.hibernate.SessionFactory;

public class IngestWaypointCommand implements Command<Coordinates> {

    private final SessionFactory sessionFactory;

    private final CreateWaypointRequest waypointRequest;

    public IngestWaypointCommand(SessionFactory sessionFactory, CreateWaypointRequest waypointRequest) {
        this.sessionFactory = sessionFactory;
        this.waypointRequest = waypointRequest;
    }

    @Override
    public Coordinates execute() throws UbikeIngestException {

        if (waypointRequest.altitude() == null || waypointRequest.longitude() == null) {
            throw new UbikeIngestException("No Waypoint");
        }

        EntityTransaction tx = null;

        try (var session = sessionFactory.openSession()) {

            tx = session.beginTransaction();

            var waypoint = new Waypoint();
            waypoint.setAltitude(waypointRequest.altitude());
            waypoint.setLongitude(waypointRequest.longitude());

            session.persist(waypoint);

            var result = new Coordinates(
                    waypoint.getAltitude(),
                    waypoint.getLongitude()
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
