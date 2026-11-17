package service;

import model.PerformanceResult;
import model.Schedule;
import model.ScheduleEntry;

public class PerformanceAnalyzer {

    public int calculateMakespan(
            Schedule schedule) {

        int maximumEndTime = 0;

        for (ScheduleEntry entry :
                schedule.getEntries()) {

            if (entry.getEndTime()
                    > maximumEndTime) {

                maximumEndTime =
                        entry.getEndTime();
            }
        }

        return maximumEndTime;
    }

    public double calculateAverageWaitingTime(
            Schedule schedule) {

        if (schedule.getEntries().isEmpty()) {
            return 0;
        }

        double totalWaitingTime = 0;

        for (ScheduleEntry entry :
                schedule.getEntries()) {

            totalWaitingTime +=
                    entry.getStartTime();
        }

        return totalWaitingTime
                / schedule.getEntries().size();
    }

    public int calculateLateTasks(
            Schedule schedule) {

        int lateTasks = 0;

        for (ScheduleEntry entry :
                schedule.getEntries()) {

            if (entry.getEndTime()
                    > entry.getTask()
                    .getDeadline()) {

                lateTasks++;
            }
        }

        return lateTasks;
    }

    public PerformanceResult analyze(
            Schedule schedule,
            long executionTime) {

        return new PerformanceResult(
                schedule.getAlgorithmName(),
                calculateMakespan(schedule),
                calculateAverageWaitingTime(
                        schedule),
                calculateLateTasks(schedule),
                executionTime
        );
    }

    public void displayPerformance(
            PerformanceResult result) {

        System.out.println(
                "\n================================"
        );

        System.out.println(
                "PERFORMANCE REPORT"
        );

        System.out.println(
                "================================"
        );

        System.out.println(result);
    }
}