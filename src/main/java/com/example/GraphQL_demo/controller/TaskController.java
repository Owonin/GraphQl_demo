package com.example.GraphQL_demo.controller;

import com.example.GraphQL_demo.entity.Status;
import com.example.GraphQL_demo.entity.Task;
import com.example.GraphQL_demo.entity.User;
import com.example.GraphQL_demo.repositiry.TaskRepository;
import com.example.GraphQL_demo.repositiry.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @QueryMapping
    private Task findTaskById(@Argument Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NotFound"));
    }

    @QueryMapping
    public Iterable<Task> tasks(){
        return taskRepository.findAll();
    }

    @MutationMapping
    public Task addTask(@Argument("task") TaskInput taskInput) {
        User user = userRepository.findById(taskInput.userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NotFound"));

        Task task = new Task(taskInput.title, taskInput.description, taskInput.status, user);

        log.info("Creating new task with title {}",taskInput.title);

        return taskRepository.save(task);
    }


    record TaskInput(String title, String description, Status status, Long userId) {
    }
}
