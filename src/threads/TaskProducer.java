package threads;

import model.Task;

import java.util.List;

public class TaskProducer extends Thread {

    private ScheduleSimulationThread simulation;
    private List<Task> tasks;

    public TaskProducer(
            ScheduleSimulationThread simulation,
            List<Task> tasks) {

        this.simulation = simulation;
        this.tasks = tasks;
    }

    @Override
    public void run() {

        for (Task task : tasks) {

            simulation.addTask(task);

            try {

                Thread.sleep(300);

            } catch (InterruptedException e) {

                Thread.currentThread()
                        .interrupt();
            }
        }

        simulation.finishSimulation();
    }
}