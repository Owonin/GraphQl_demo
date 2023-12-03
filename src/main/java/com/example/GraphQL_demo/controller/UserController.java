package com.example.GraphQL_demo.controller;

import com.example.GraphQL_demo.entity.User;
import com.example.GraphQL_demo.repositiry.TaskRepository;
import com.example.GraphQL_demo.repositiry.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @QueryMapping
    public String health() {
        return "Service is working";
    }

    @QueryMapping
    public Iterable<User> users() {
        return userRepository.findAll();
    }

    @MutationMapping
    public User addUser(@Argument("user") UserInput userInput) {

        User user = new User(userInput.name, userInput.email);

        log.info("Saving new user with name {}", userInput.name);

        return userRepository.save(user);
    }

    record UserInput(String name, String email) {
    }

}
