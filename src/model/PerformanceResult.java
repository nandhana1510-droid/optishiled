package model;

public class PerformanceResult {

    private String algorithmName;
    private int makespan;
    private double averageWaitingTime;
    private int lateTasks;
    private long executionTime;

    public PerformanceResult(
            String algorithmName,
            int makespan,
            double averageWaitingTime,
            int lateTasks,
            long executionTime) {

        this.algorithmName = algorithmName;
        this.makespan = makespan;
        this.averageWaitingTime =
                averageWaitingTime;
        this.lateTasks = lateTasks;
        this.executionTime = executionTime;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public int getMakespan() {
        return makespan;
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public int getLateTasks() {
        return lateTasks;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public double calculateScore() {

        return (makespan * 0.5)
                + (averageWaitingTime * 0.3)
                + (lateTasks * 10);
    }

    @Override
    public String toString() {

        return "\nAlgorithm: " + algorithmName
                + "\nMakespan: " + makespan
                + "\nAverage Waiting Time: "
                + String.format(
                        "%.2f",
                        averageWaitingTime)
                + "\nLate Tasks: " + lateTasks
                + "\nExecution Time: "
                + executionTime + " ns"
                + "\nPerformance Score: "
                + String.format(
                        "%.2f",
                        calculateScore());
    }
}