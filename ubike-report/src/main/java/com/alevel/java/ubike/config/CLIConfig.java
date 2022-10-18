package com.alevel.java.ubike.config;

import com.alevel.java.ubike.cli.*;
import com.alevel.java.ubike.report.ReportFactory;

import java.util.Map;

public class CLIConfig {

    private Map<String, InteractiveCLI> reports(ReportFactory reportFactory) {

        return Map.of(
                "avg per day", new AverageRideDurationForDateCLI(reportFactory),
                "count rides by nickname", new CountRidesForRiderByNicknameCLI(reportFactory),
                "count vehicle by nickname and last month", new CountVehicleByNicknameForMonthCLI(reportFactory)
        );
    }

    public InteractiveCLI cli(ReportFactory reportFactory) {
        return new ReportSelectionCLI(reports(reportFactory));
    }

}
