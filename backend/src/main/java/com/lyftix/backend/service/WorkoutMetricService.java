package com.lyftix.backend.service;

import com.lyftix.backend.dto.CreateWorkoutMetricRequest;
import com.lyftix.backend.dto.WorkoutMetricResponse;
import com.lyftix.backend.model.WorkoutMetric;
import com.lyftix.backend.repository.WorkoutModelRepository;
import org.springframework.stereotype.Service;

@Service
public class WorkoutMetricService {

    private final WorkoutModelRepository workoutModelRepository;

    public WorkoutMetricService(WorkoutModelRepository workoutModelRepository) {
        this.workoutModelRepository = workoutModelRepository;
    }

    public WorkoutMetricResponse createWorkoutMetric(CreateWorkoutMetricRequest request) {

        WorkoutMetric workoutMetric = new WorkoutMetric();

        workoutMetric.setWorkoutType((request.workoutType()));
        workoutMetric.setIntensity(request.intensity());
        workoutMetric.setCaloriesBurned(request.caloriesBurned());
        workoutMetric.setStartedAt(request.startedAt());
        workoutMetric.setEndedAt(request.endedAt());

        WorkoutMetric savedWorkoutMetric = workoutModelRepository.save(workoutMetric);


        return new WorkoutMetricResponse(
                savedWorkoutMetric.getId(),
                savedWorkoutMetric.getWorkoutType(),
                savedWorkoutMetric.getIntensity(),
                savedWorkoutMetric.getCaloriesBurned(),
                savedWorkoutMetric.getStartedAt(),
                savedWorkoutMetric.getEndedAt()

        );

    }
}
