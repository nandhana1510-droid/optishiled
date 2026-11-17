package main;

import algorithms.AbstractScheduler;
import algorithms.AdaptiveScheduler;
import algorithms.FCFSScheduler;
import algorithms.PriorityScheduler;
import algorithms.SJFScheduler;

import exceptions.DependencyException;
import exceptions.DuplicateTaskException;
import exceptions.InvalidTaskException;

import model.Machine;
import model.PerformanceResult;
import model.Schedule;
import model.Task;

import service.BenchmarkService;
import service.DependencyResolver;
import service.PerformanceAnalyzer;
import service.TaskManager;

import threads.ScheduleSimulationThread;
import threads.TaskConsumer;
import threads.TaskProducer;

import util.CSVExporter;
import util.CSVImporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OptiSchedApp {

    private static TaskManager taskManager =
            new TaskManager();

    private static Scanner scanner =
            new Scanner(System.in);

    public static void main(
            String[] args) {

        boolean running = true;

        System.out.println(
                "\n========================================"
        );

        System.out.println(
                "        WELCOME TO OPTISCHED"
        );

        System.out.println(
                " Adaptive Task Scheduling System"
        );

        System.out.println(
                "========================================"
        );

        while (running) {

            displayMenu();

            System.out.print(
                    "\nEnter your choice: "
            );

            int choice;

            try {

                choice =
                        scanner.nextInt();

                scanner.nextLine();

                switch (choice) {

                    case 1:
                        addTask();
                        break;

                    case 2:
                        taskManager.displayTasks();
                        break;

                    case 3:
                        addDependency();
                        break;

                    case 4:
                        generateSchedule();
                        break;

                    case 5:
                        runBenchmark();
                        break;

                    case 6:
                        importCSV();
                        break;

                    case 7:
                        runSimulation();
                        break;

                    case 8:

                        running = false;

                        System.out.println(
                                "Thank you for using OptiSched!"
                        );

                        break;

                    default:

                        System.out.println(
                                "Invalid option."
                        );
                }

            } catch (Exception e) {

                System.out.println(
                        "Invalid input. "
                                + "Please try again."
                );

                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void displayMenu() {

        System.out.println(
                "\n------------- MENU -------------"
        );

        System.out.println(
                "1. Add Task"
        );

        System.out.println(
                "2. View Tasks"
        );

        System.out.println(
                "3. Add Task Dependency"
        );

        System.out.println(
                "4. Generate Schedule"
        );

        System.out.println(
                "5. Run Algorithm Benchmark"
        );

        System.out.println(
                "6. Import Tasks from CSV"
        );

        System.out.println(
                "7. Run Thread Simulation"
        );

        System.out.println(
                "8. Exit"
        );
    }

    private static void addTask() {

        try {

            System.out.print(
                    "Enter task name: "
            );

            String name =
                    scanner.nextLine();

            System.out.print(
                    "Enter duration: "
            );

            int duration =
                    scanner.nextInt();

            System.out.print(
                    "Enter priority (1-10): "
            );

            int priority =
                    scanner.nextInt();

            System.out.print(
                    "Enter deadline: "
            );

            int deadline =
                    scanner.nextInt();

            scanner.nextLine();

            taskManager.addTask(
                    name,
                    duration,
                    priority,
                    deadline
            );

        } catch (
                InvalidTaskException |
                DuplicateTaskException e) {

            System.out.println(
                    "Error: "
                            + e.getMessage()
            );
        }
    }

    private static void addDependency() {

        try {

            taskManager.displayTasks();

            System.out.print(
                    "\nEnter task ID: "
            );

            int taskId =
                    scanner.nextInt();

            System.out.print(
                    "Enter dependency task ID: "
            );

            int dependencyId =
                    scanner.nextInt();

            scanner.nextLine();

            taskManager.addDependency(
                    taskId,
                    dependencyId
            );

        } catch (DependencyException e) {

            System.out.println(
                    "Dependency Error: "
                            + e.getMessage()
            );
        }
    }

    private static List<Machine>
    createMachines() {

        List<Machine> machines =
                new ArrayList<>();

        machines.add(
                new Machine(
                        1,
                        "Machine-A"
                )
        );

        machines.add(
                new Machine(
                        2,
                        "Machine-B"
                )
        );

        return machines;
    }

    private static void
    generateSchedule() {

        if (taskManager.getTasks()
                .isEmpty()) {

            System.out.println(
                    "Please add tasks first."
            );

            return;
        }

        try {

            DependencyResolver resolver =
                    new DependencyResolver();

            List<Task> tasks =
                    resolver.resolveDependencies(
                            taskManager.getTasks()
                    );

            List<Machine> machines =
                    createMachines();

            System.out.println(
                    "\nChoose Algorithm:"
            );

            System.out.println(
                    "1. FCFS"
            );

            System.out.println(
                    "2. SJF"
            );

            System.out.println(
                    "3. Priority"
            );

            System.out.println(
                    "4. Adaptive OptiSched"
            );

            int choice =
                    scanner.nextInt();

            scanner.nextLine();

            AbstractScheduler scheduler;

            switch (choice) {

                case 1:

                    scheduler =
                            new FCFSScheduler(
                                    machines
                            );

                    break;

                case 2:

                    scheduler =
                            new SJFScheduler(
                                    machines
                            );

                    break;

                case 3:

                    scheduler =
                            new PriorityScheduler(
                                    machines
                            );

                    break;

                case 4:

                    scheduler =
                            new AdaptiveScheduler(
                                    machines
                            );

                    break;

                default:

                    System.out.println(
                            "Invalid algorithm."
                    );

                    return;
            }

            /*
             * Dynamic Method Dispatch:
             * scheduler is an AbstractScheduler
             * reference, but schedule() calls the
             * actual child class implementation.
             */

            Schedule schedule =
                    scheduler.schedule(tasks);

            schedule.displaySchedule();

            PerformanceAnalyzer analyzer =
                    new PerformanceAnalyzer();

            PerformanceResult result =
                    analyzer.analyze(
                            schedule,
                            0
                    );

            analyzer.displayPerformance(
                    result
            );

            System.out.print(
                    "\nExport schedule to CSV? (yes/no): "
            );

            String export =
                    scanner.nextLine();

            if (export.equalsIgnoreCase(
                    "yes")) {

                CSVExporter exporter =
                        new CSVExporter(
                                schedule
                        );

                exporter.exportData(
                        "OptiSched_Schedule.csv"
                );
            }

        } catch (DependencyException e) {

            System.out.println(
                    "Scheduling Error: "
                            + e.getMessage()
            );
        }
    }

    private static void runBenchmark() {

        if (taskManager.getTasks()
                .isEmpty()) {

            System.out.println(
                    "Please add tasks first."
            );

            return;
        }

        try {

            DependencyResolver resolver =
                    new DependencyResolver();

            List<Task> tasks =
                    resolver.resolveDependencies(
                            taskManager.getTasks()
                    );

            List<AbstractScheduler> schedulers =
                    new ArrayList<>();

            schedulers.add(
                    new FCFSScheduler(
                            createMachines()
                    )
            );

            schedulers.add(
                    new SJFScheduler(
                            createMachines()
                    )
            );

            schedulers.add(
                    new PriorityScheduler(
                            createMachines()
                    )
            );

            schedulers.add(
                    new AdaptiveScheduler(
                            createMachines()
                    )
            );

            BenchmarkService service =
                    new BenchmarkService();

            List<PerformanceResult> results =
                    service.runBenchmark(
                            schedulers,
                            tasks
                    );

            service.displayComparison(
                    results
            );

        } catch (DependencyException e) {

            System.out.println(
                    "Benchmark failed: "
                            + e.getMessage()
            );
        }
    }

    private static void importCSV() {

        System.out.print(
                "Enter CSV file path: "
        );

        String fileName =
                scanner.nextLine();

        CSVImporter importer =
                new CSVImporter();

        importer.importTasks(
                fileName,
                taskManager
        );
    }

    private static void runSimulation() {

        List<Task> tasks =
                taskManager.getTasks();

        if (tasks.isEmpty()) {

            System.out.println(
                    "No tasks available."
            );

            return;
        }

        ScheduleSimulationThread simulation =
                new ScheduleSimulationThread();

        TaskProducer producer =
                new TaskProducer(
                        simulation,
                        tasks
                );

        TaskConsumer consumer =
                new TaskConsumer(
                        simulation
                );

        producer.start();

        consumer.start();

        try {

            producer.join();

            consumer.join();

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();
        }

        System.out.println(
                "Thread simulation completed."
        );
    }
}