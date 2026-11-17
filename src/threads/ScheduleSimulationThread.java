package threads;

import model.Task;

import java.util.LinkedList;
import java.util.Queue;

public class ScheduleSimulationThread {

    private Queue<Task> taskQueue;

    private boolean completed;

    public ScheduleSimulationThread() {

        taskQueue =
                new LinkedList<>();

        completed = false;
    }

    public synchronized void addTask(
            Task task) {

        taskQueue.add(task);

        System.out.println(
                "Task added to simulation: "
                        + task.getTaskName()
        );

        notifyAll();
    }

    public synchronized Task getTask() {

        while (taskQueue.isEmpty()
                && !completed) {

            try {

                System.out.println(
                        "Worker waiting for tasks..."
                );

                wait();

            } catch (InterruptedException e) {

                Thread.currentThread()
                        .interrupt();

                return null;
            }
        }

        return taskQueue.poll();
    }

    public synchronized void finishSimulation() {

        completed = true;

        notifyAll();
    }
}