package com.gustavopin.todoapi.dtos;

import com.gustavopin.todoapi.models.task.TaskStatus;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.UUID;

public record TaskResponseDto(
        @NotBlank(message = "Id is required")
        UUID id,
        String title,
        String description,
        TaskStatus completed,
        Date createdAt,
        Date updatedAt
) {
}
