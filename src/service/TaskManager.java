package service;

import exceptions.DependencyException;
import exceptions.DuplicateTaskException;
import exceptions.InvalidTaskException;

import model.Task;

import util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private List<Task> tasks;

    public TaskManager() {

        tasks =
                new ArrayList<>();
    }

    public void addTask(
            String name,
            int duration,
            int priority,
            int deadline)
            throws InvalidTaskException,
            DuplicateTaskException {

        validateTask(
                name,
                duration,
                priority,
                deadline
        );

        for (Task task : tasks) {

            if (task.getTaskName()
                    .equalsIgnoreCase(name)) {

                throw new DuplicateTaskException(
                        "A task with this name "
                                + "already exists."
                );
            }
        }

        Task newTask =
                new Task(
                        name,
                        duration,
                        priority,
                        deadline
                );

        tasks.add(newTask);

        System.out.println(
                "Task added successfully."
        );
    }

    private void validateTask(
            String name,
            int duration,
            int priority,
            int deadline)
            throws InvalidTaskException {

        if (!InputValidator
                .isValidTaskName(name)) {

            throw new InvalidTaskException(
                    "Task name cannot be empty."
            );
        }

        if (!InputValidator
                .isValidDuration(duration)) {

            throw new InvalidTaskException(
                    "Duration must be greater than zero."
            );
        }

        if (!InputValidator
                .isValidPriority(priority)) {

            throw new InvalidTaskException(
                    "Priority must be between 1 and 10."
            );
        }

        if (!InputValidator
                .isValidDeadline(deadline)) {

            throw new InvalidTaskException(
                    "Deadline must be greater than zero."
            );
        }
    }

    public void addDependency(
            int taskId,
            int dependencyId)
            throws DependencyException {

        Task task =
                findTask(taskId);

        Task dependency =
                findTask(dependencyId);

        if (task == null) {

            throw new DependencyException(
                    "Task not found."
            );
        }

        if (dependency == null) {

            throw new DependencyException(
                    "Dependency task not found."
            );
        }

        if (taskId == dependencyId) {

            throw new DependencyException(
                    "A task cannot depend on itself."
            );
        }

        task.addDependency(
                dependencyId
        );

        System.out.println(
                "Dependency added successfully."
        );
    }

    public void removeTask(
            int taskId) {

        tasks.removeIf(
                task ->
                        task.getTaskId()
                                == taskId
        );
    }

    public Task findTask(
            int taskId) {

        for (Task task : tasks) {

            if (task.getTaskId()
                    == taskId) {

                return task;
            }
        }

        return null;
    }

    public List<Task> getTasks() {

        return new ArrayList<>(
                tasks
        );
    }

    public void displayTasks() {

        if (tasks.isEmpty()) {

            System.out.println(
                    "\nNo tasks available."
            );

            return;
        }

        System.out.println(
                "\n================================"
        );

        System.out.println(
                "CURRENT TASK LIST"
        );

        System.out.println(
                "================================"
        );

        for (Task task : tasks) {

            System.out.println(
                    "\n" + task
            );

            System.out.println(
                    "--------------------------------"
            );
        }
    }
}