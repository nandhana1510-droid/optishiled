package util;

import interfaces.Exportable;
import model.Schedule;
import model.ScheduleEntry;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVExporter
        implements Exportable {

    private Schedule schedule;

    public CSVExporter(
            Schedule schedule) {

        this.schedule = schedule;
    }

    @Override
    public void exportData(
            String fileName) {

        try (
                BufferedWriter writer =
                        new BufferedWriter(
                                new FileWriter(
                                        fileName))) {

            writer.write(
                    "Task ID,Task Name,Machine,"
                            + "Start Time,End Time"
            );

            writer.newLine();

            for (ScheduleEntry entry :
                    schedule.getEntries()) {

                writer.write(
                        entry.getTask()
                                .getTaskId()
                                + ","
                                + entry.getTask()
                                .getTaskName()
                                + ","
                                + entry.getMachine()
                                .getMachineName()
                                + ","
                                + entry.getStartTime()
                                + ","
                                + entry.getEndTime()
                );

                writer.newLine();
            }

            System.out.println(
                    "Schedule exported successfully to "
                            + fileName
            );

        } catch (IOException e) {

            System.out.println(
                    "Error exporting CSV: "
                            + e.getMessage()
            );
        }
    }
}