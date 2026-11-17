package threads;

import model.Task;

public class TaskConsumer extends Thread {

    private ScheduleSimulationThread simulation;

    public TaskConsumer(
            ScheduleSimulationThread simulation) {

        this.simulation = simulation;
    }

    @Override
    public void run() {

        while (true) {

            Task task =
                    simulation.getTask();

            if (task == null) {
                break;
            }

            System.out.println(
                    "Processing task: "
                            + task.getTaskName()
            );

            task.setStatus(
                    "PROCESSING"
            );

            try {

                Thread.sleep(
                        task.getDuration() * 100
                );

            } catch (InterruptedException e) {

                Thread.currentThread()
                        .interrupt();

                break;
            }

            task.setStatus(
                    "COMPLETED"
            );

            System.out.println(
                    "Completed task: "
                            + task.getTaskName()
            );
        }
    }
}