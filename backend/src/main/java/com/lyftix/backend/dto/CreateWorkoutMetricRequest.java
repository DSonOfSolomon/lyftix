package com.lyftix.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CreateWorkoutMetricRequest(

        @NotBlank
        String workoutType,

        @NotNull
        @Min(1)
        @Max(10)
        Integer intensity,

        @NotNull
        @Min(0)
        Integer caloriesBurned,

        @NotNull
        Instant startedAt,

        @NotNull
        Instant endedAt

) {
}