package com.alevel.java.ubike.cli;

import com.alevel.java.ubike.exception.UbikeReportException;
import com.alevel.java.ubike.report.ReportFactory;

import java.util.Scanner;

public class CountRidesForRiderByNicknameCLI implements InteractiveCLI {

    private final ReportFactory reportFactory;

    public CountRidesForRiderByNicknameCLI(ReportFactory reportFactory) {
        this.reportFactory = reportFactory;
    }

    @Override
    public void run() throws UbikeReportException {

        var scanner = new Scanner(System.in);

        System.out.println("Please, enter a nickname");

        String nickname = scanner.nextLine();

        Integer result = reportFactory.countRidesForRiderByNickname(nickname).load();

        System.out.printf("Amount rides for rider by nickname: %s is: %d times%n", nickname, result);

    }

}
