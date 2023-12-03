package com.example.GraphQL_demo.repositiry;

import com.example.GraphQL_demo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
