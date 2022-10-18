package com.alevel.java.ubike.report;

import com.alevel.java.ubike.exception.UbikeReportException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

public class CountRidesForRiderByNickname implements Report<Integer> {

    private final Supplier<Connection> connectionSupplier;

    private final String nickname;

    public CountRidesForRiderByNickname(Supplier<Connection> connectionSupplier, String nickname) {
        this.connectionSupplier = connectionSupplier;
        this.nickname = nickname;
    }

    @Override
    public Integer load() throws UbikeReportException {

        String sql = """
                select count(r.id) as result
                from rides r
                where r.rider_id = (select rr.id from riders rr where rr.nickname = '?')
                """;

        try (PreparedStatement query = connectionSupplier.get().prepareStatement(sql)) {
            query.setString(1, nickname);

            ResultSet resultSet = query.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                throw new UbikeReportException("No result found");
            }
        } catch (SQLException e) {
            throw new UbikeReportException(e);
        }

    }

}
