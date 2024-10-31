package com.gustavopin.todoapi.services;

import com.gustavopin.todoapi.dtos.TaskRequestDto;
import com.gustavopin.todoapi.dtos.TaskResponseDto;
import com.gustavopin.todoapi.models.task.Task;
import com.gustavopin.todoapi.models.task.TaskStatus;
import com.gustavopin.todoapi.repositories.TasksRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServices {
    @Autowired
    TasksRepository tasksRepository;

    public TaskResponseDto createNew(TaskRequestDto data) {
        Task task = new Task();
        task.setTitle(data.title());
        task.setDescription(data.description());
        tasksRepository.save(task);

        UUID taskId = task.getId();

        System.out.println("Task created with id: " + taskId);
        return this.getTask(taskId);
    }

    public List<Task> listAll() {
        Optional<List<Task>> tasks = Optional.of(tasksRepository.findAll());

        if(tasks.get().isEmpty()) {
            throw new EntityExistsException("No tasks found");
        }

        return tasks.get();
    }

    public TaskResponseDto getTask(UUID id) {
        Optional<Task> task =  tasksRepository.findById(id);

        if (task.isPresent()) {
            return new TaskResponseDto(
                    task.get().getId(),
                    task.get().getTitle(),
                    task.get().getDescription(),
                    task.get().getCompleted(),
                    task.get().getCreatedAt(),
                    task.get().getUpdatedAt()
            );
        } else {
            throw new EntityExistsException("Task not found");
        }
    }

    public HttpStatus updateTask(TaskRequestDto data, UUID id) {
        TaskResponseDto task = this.getTask(id);

        Task updatedTask = new Task();

        updatedTask.setTitle(data.title());
        updatedTask.setDescription(data.description());
        updatedTask.setUpdatedAt(new Date());

        tasksRepository.save(updatedTask);

        return HttpStatus.OK;
    }

    public HttpStatus updateTaskStatus(UUID id) {
        TaskResponseDto task = this.getTask(id);

        Task updatedTask = new Task();

        if(updatedTask.getCompleted() == TaskStatus.PENDING) {
            updatedTask.setCompleted(TaskStatus.COMPLETED);
        } else {
            updatedTask.setCompleted(TaskStatus.PENDING);
        }

        updatedTask.setUpdatedAt(new Date());

        tasksRepository.save(updatedTask);

        return HttpStatus.OK;
    }

    public HttpStatus deleteTask(UUID id) {
        TaskResponseDto task = this.getTask(id);

        Task taskToDelete = new Task();

        taskToDelete.setId(task.id());
        taskToDelete.setTitle(task.title());
        taskToDelete.setDescription(task.description());
        taskToDelete.setCompleted(task.completed());
        taskToDelete.setCreatedAt(task.createdAt());
        taskToDelete.setUpdatedAt(task.updatedAt());

        tasksRepository.delete(taskToDelete);
        return HttpStatus.OK;
    }
}
