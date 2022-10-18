package com.alevel.java.ubike.cli;

import com.alevel.java.ubike.exception.UbikeReportException;
import com.alevel.java.ubike.report.ReportFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CountVehicleByNicknameForMonthCLI implements InteractiveCLI {

    private final ReportFactory reportFactory;

    public CountVehicleByNicknameForMonthCLI(ReportFactory reportFactory) {
        this.reportFactory = reportFactory;
    }

    @Override
    public void run() throws UbikeReportException {

        var scanner = new Scanner(System.in);

        System.out.println("Please, enter a nickname");

        String nickname = scanner.nextLine();

        System.out.println("Please, enter a date like '2011-12-03'");

        LocalDate date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);

        Integer result = reportFactory.countVehicleByNicknameForMonth(nickname, date).load();

        System.out.printf("Amount vehicle by nickname: %s and last month is: %d times%n", nickname, result);

    }

}
