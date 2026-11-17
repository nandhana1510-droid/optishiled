package model;

import java.util.ArrayList;
import java.util.List;

public class Task {

    private static int taskCounter = 100;

    private final int taskId;
    private String taskName;
    private int duration;
    private int priority;
    private int deadline;
    private String status;

    // Stores IDs of tasks that must finish first
    private List<Integer> dependencies;

    public Task(String taskName, int duration,
                int priority, int deadline) {

        this.taskId = ++taskCounter;
        this.taskName = taskName;
        this.duration = duration;
        this.priority = priority;
        this.deadline = deadline;
        this.status = "PENDING";
        this.dependencies = new ArrayList<>();
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getDuration() {
        return duration;
    }

    public int getPriority() {
        return priority;
    }

    public int getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }

    public List<Integer> getDependencies() {
        return new ArrayList<>(dependencies);
    }

    public void addDependency(int taskId) {

        if (!dependencies.contains(taskId)
                && taskId != this.taskId) {

            dependencies.add(taskId);
        }
    }

    public void removeDependency(int taskId) {
        dependencies.remove(Integer.valueOf(taskId));
    }

    public boolean hasDependencies() {
        return !dependencies.isEmpty();
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static int getTaskCounter() {
        return taskCounter;
    }

    @Override
    public String toString() {

        return "Task ID: " + taskId +
                "\nName: " + taskName +
                "\nDuration: " + duration +
                "\nPriority: " + priority +
                "\nDeadline: " + deadline +
                "\nStatus: " + status +
                "\nDependencies: " + dependencies;
    }
}