package com.alevel.java.ubike.report;

import com.alevel.java.ubike.exception.UbikeReportException;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.function.Supplier;

public class CountVehicleByNicknameForMonth implements Report<Integer> {

    private final Supplier<Connection> connectionSupplier;

    private final String nickname;

    private final LocalDate date;

    public CountVehicleByNicknameForMonth(Supplier<Connection> connectionSupplier, String nickname, LocalDate date) {
        this.connectionSupplier = connectionSupplier;
        this.nickname = nickname;
        this.date = date;
    }

    @Override
    public Integer load() throws UbikeReportException {

        Timestamp start = Timestamp.from(date.atStartOfDay().toInstant(ZoneOffset.UTC));

        Timestamp finish = Timestamp.from(date.atStartOfDay().plusMonths(1).toInstant(ZoneOffset.UTC));

        String sql = """
                select count(distinct (r.vehicle_id)) as result
                from rides r
                where r.rider_id = (select rr.id from riders rr where rr.nickname = '?')
                and r.started_at >= ? and r.finished_at <= ?
                """;

        try (PreparedStatement query = connectionSupplier.get().prepareStatement(sql)) {
            query.setString(1, nickname);
            query.setTimestamp(1, start);
            query.setTimestamp(2, finish);

            ResultSet resultSet = query.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("vehicle_id");
            } else {
                throw new UbikeReportException("No result found");
            }
        } catch (SQLException e) {
            throw new UbikeReportException(e);
        }

    }

}
