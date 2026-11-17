package service;

import algorithms.AbstractScheduler;
import model.PerformanceResult;
import model.Task;
import threads.BenchmarkThread;
import threads.ResultCollector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BenchmarkService {

    public List<PerformanceResult> runBenchmark(
            List<AbstractScheduler> schedulers,
            List<Task> tasks) {

        PerformanceAnalyzer analyzer =
                new PerformanceAnalyzer();

        ResultCollector collector =
                new ResultCollector();

        List<BenchmarkThread> threads =
                new ArrayList<>();

        System.out.println(
                "\nStarting Multithreaded Benchmark..."
        );

        for (AbstractScheduler scheduler :
                schedulers) {

            BenchmarkThread thread =
                    new BenchmarkThread(
                            scheduler,
                            tasks,
                            analyzer,
                            collector
                    );

            threads.add(thread);

            thread.start();
        }

        for (BenchmarkThread thread :
                threads) {

            try {

                thread.join();

            } catch (InterruptedException e) {

                Thread.currentThread()
                        .interrupt();

                System.out.println(
                        "Benchmark interrupted."
                );
            }
        }

        return collector.getResults();
    }

    public PerformanceResult
    findBestAlgorithm(
            List<PerformanceResult> results) {

        if (results.isEmpty()) {
            return null;
        }

        return results.stream()
                .min(
                        Comparator.comparingDouble(
                                PerformanceResult
                                        ::calculateScore
                        )
                )
                .orElse(null);
    }

    public void displayComparison(
            List<PerformanceResult> results) {

        System.out.println(
                "\n=========================================="
        );

        System.out.println(
                "BENCHMARK COMPARISON"
        );

        System.out.println(
                "=========================================="
        );

        for (PerformanceResult result :
                results) {

            System.out.println(result);
            System.out.println(
                    "--------------------------------"
            );
        }

        PerformanceResult best =
                findBestAlgorithm(results);

        if (best != null) {

            System.out.println(
                    "\nBEST ALGORITHM: "
                            + best.getAlgorithmName()
            );
        }
    }
}