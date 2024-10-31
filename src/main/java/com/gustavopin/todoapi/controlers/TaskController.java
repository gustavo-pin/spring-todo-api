package com.gustavopin.todoapi.controlers;

import com.gustavopin.todoapi.dtos.TaskRequestDto;
import com.gustavopin.todoapi.dtos.TaskResponseDto;
import com.gustavopin.todoapi.models.task.Task;
import com.gustavopin.todoapi.services.TaskServices;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController {

    @Autowired
    TaskServices taskServices;

    @PostMapping
    public ResponseEntity<TaskResponseDto> createNew(@Valid @RequestBody TaskRequestDto task) {
        try {
            return ResponseEntity.ok(taskServices.createNew(task));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Task>> listAll() {
        try {
            return ResponseEntity.ok(taskServices.listAll());
        } catch (EntityExistsException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTask(@Valid @PathVariable("id") UUID id) {
        try {
            return ResponseEntity.ok(taskServices.getTask(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTask(@Valid @RequestBody TaskRequestDto task, @Valid @PathVariable("id") UUID id) {
        try {
            return new ResponseEntity<>(taskServices.updateTask(task, id));
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateTaskStatus(@Valid @PathVariable("id") UUID id) {
        try {
            return new ResponseEntity<>(taskServices.updateTaskStatus(id));
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@Valid @PathVariable("id") UUID id) {
        try {
            return new ResponseEntity<>(taskServices.deleteTask(id));
        } catch (EntityExistsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
