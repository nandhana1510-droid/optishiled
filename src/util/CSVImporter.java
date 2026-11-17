package util;

import exceptions.DuplicateTaskException;
import exceptions.InvalidTaskException;
import service.TaskManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVImporter {

    public void importTasks(
            String fileName,
            TaskManager manager) {

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(fileName)
                    );

            try {

                String line;

                boolean firstLine = true;

                while ((line =
                        reader.readLine()) != null) {

                    if (firstLine) {

                        firstLine = false;

                        continue;
                    }

                    String[] data =
                            line.split(",");

                    try {

                        String name =
                                data[0].trim();

                        int duration =
                                Integer.parseInt(
                                        data[1].trim()
                                );

                        int priority =
                                Integer.parseInt(
                                        data[2].trim()
                                );

                        int deadline =
                                Integer.parseInt(
                                        data[3].trim()
                                );

                        manager.addTask(
                                name,
                                duration,
                                priority,
                                deadline
                        );

                    } catch (
                            NumberFormatException e) {

                        System.out.println(
                                "Invalid number format "
                                        + "in CSV."
                        );

                    } catch (
                            InvalidTaskException |
                            DuplicateTaskException e) {

                        System.out.println(
                                "Task could not be imported: "
                                        + e.getMessage()
                        );
                    }
                }

            } finally {

                reader.close();

                System.out.println(
                        "CSV file processing completed."
                );
            }

        } catch (IOException e) {

            System.out.println(
                    "File error: "
                            + e.getMessage()
            );
        }
    }
}