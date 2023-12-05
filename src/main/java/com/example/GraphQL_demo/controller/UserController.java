package com.example.GraphQL_demo.controller;

import com.example.GraphQL_demo.entity.User;
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
public class UserController {

    private final UserRepository userRepository;

    @QueryMapping
    public String health() {
        return "Service is working";
    }

    @QueryMapping
    public Iterable<User> users() {
        log.info("Returning all users");
        return userRepository.findAll();
    }

    @QueryMapping
    public User findUserById(@Argument Long id) {
        log.info("Returning user with {} id", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with id %d is not found", id)));
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
