package com.gustavopin.todoapi.repositories;

import com.gustavopin.todoapi.models.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TasksRepository extends JpaRepository<Task, UUID> {
}