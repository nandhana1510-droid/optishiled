package threads;

import algorithms.AbstractScheduler;
import model.PerformanceResult;
import model.Schedule;
import model.Task;
import service.PerformanceAnalyzer;

import java.util.List;

public class BenchmarkThread extends Thread {

    private AbstractScheduler scheduler;
    private List<Task> tasks;
    private PerformanceAnalyzer analyzer;
    private ResultCollector collector;

    public BenchmarkThread(
            AbstractScheduler scheduler,
            List<Task> tasks,
            PerformanceAnalyzer analyzer,
            ResultCollector collector) {

        this.scheduler = scheduler;
        this.tasks = tasks;
        this.analyzer = analyzer;
        this.collector = collector;
    }

    @Override
    public void run() {

        System.out.println(
                "\nThread started for "
                        + scheduler.getAlgorithmName()
        );

        long startTime =
                System.nanoTime();

        Schedule schedule =
                scheduler.schedule(tasks);

        long endTime =
                System.nanoTime();

        PerformanceResult result =
                analyzer.analyze(
                        schedule,
                        endTime - startTime
                );

        // Synchronized access to shared results
        collector.addResult(result);

        System.out.println(
                "Thread completed for "
                        + scheduler.getAlgorithmName()
        );
    }
}