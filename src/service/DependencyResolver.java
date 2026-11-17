package service;

import exceptions.DependencyException;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DependencyResolver {

    public List<Task> resolveDependencies(
            List<Task> tasks)
            throws DependencyException {

        Map<Integer, Task> taskMap =
                new HashMap<>();

        for (Task task : tasks) {
            taskMap.put(task.getTaskId(), task);
        }

        List<Task> orderedTasks =
                new ArrayList<>();

        Set<Integer> visited =
                new HashSet<>();

        Set<Integer> visiting =
                new HashSet<>();

        for (Task task : tasks) {

            if (!visited.contains(
                    task.getTaskId())) {

                visit(
                        task,
                        taskMap,
                        visited,
                        visiting,
                        orderedTasks
                );
            }
        }

        return orderedTasks;
    }

    private void visit(
            Task task,
            Map<Integer, Task> taskMap,
            Set<Integer> visited,
            Set<Integer> visiting,
            List<Task> orderedTasks)
            throws DependencyException {

        int taskId = task.getTaskId();

        if (visiting.contains(taskId)) {

            throw new DependencyException(
                    "Circular dependency detected for task: "
                            + task.getTaskName()
            );
        }

        if (visited.contains(taskId)) {
            return;
        }

        visiting.add(taskId);

        for (Integer dependencyId :
                task.getDependencies()) {

            Task dependency =
                    taskMap.get(dependencyId);

            if (dependency == null) {

                throw new DependencyException(
                        "Dependency task with ID "
                                + dependencyId
                                + " does not exist."
                );
            }

            visit(
                    dependency,
                    taskMap,
                    visited,
                    visiting,
                    orderedTasks
            );
        }

        visiting.remove(taskId);

        visited.add(taskId);

        orderedTasks.add(task);
    }
}