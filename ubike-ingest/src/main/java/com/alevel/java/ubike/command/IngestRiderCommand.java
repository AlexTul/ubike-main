package com.alevel.java.ubike.command;

import com.alevel.java.ubike.command.data.CreateRiderRequest;
import com.alevel.java.ubike.exceptions.UbikeIngestException;
import com.alevel.java.ubike.model.Rider;
import com.alevel.java.ubike.model.dto.RiderDTO;
import jakarta.persistence.EntityTransaction;
import org.hibernate.SessionFactory;

public class IngestRiderCommand implements Command<RiderDTO> {

    private final SessionFactory sessionFactory;

    private final CreateRiderRequest riderRequest;

    public IngestRiderCommand(SessionFactory sessionFactory, CreateRiderRequest riderRequest) {
        this.sessionFactory = sessionFactory;
        this.riderRequest = riderRequest;
    }

    @Override
    public RiderDTO execute() throws UbikeIngestException {

        if (riderRequest.nickname() == null) {
            throw new UbikeIngestException("No Rider");
        }

        EntityTransaction tx = null;

        try (var session = sessionFactory.openSession()) {

            tx = session.beginTransaction();

            var rider = new Rider();
            rider.setNickname(riderRequest.nickname());

            session.persist(rider);

            var result = new RiderDTO(
                  rider.getNickname()
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
