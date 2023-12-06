package com.example.GraphQL_demo.controller;

import com.example.GraphQL_demo.entity.Status;
import com.example.GraphQL_demo.entity.Task;
import com.example.GraphQL_demo.entity.User;
import com.example.GraphQL_demo.repositiry.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@GraphQlTest(UserController.class)
class UserControllerTest {

    @Autowired
    private GraphQlTester graphQlTest;

    @MockBean
    private UserRepository userRepository;


    @Test
    void testShowAllUsers() {
        User expectedUser1 = new User("Name", "Email");
        User expectedUser2 = new User("Name 2", "Email");
        List<User> expectedUsers = new ArrayList<>(List.of(expectedUser1, expectedUser2));
        String query = "{ users {name, email} }";

        when(userRepository.findAll()).thenReturn(expectedUsers);


        List<User> actualUsers = graphQlTest.document(query)
                .execute()
                .path("users[*]")
                .entityList(User.class)
                .get();

        assertTrue(actualUsers.size() > 1);
        assertEquals(expectedUser1.getName(), actualUsers.get(0).getName());
        assertEquals(expectedUser2.getName(), actualUsers.get(1).getName());
    }

    @Test
    void testFindUserByIdSuccess() {
        Long userId = 1L;
        User expectedUser = new User("Name", "Email");


        Task task = new Task(1L, "Temp Title", "Test description", Status.CLOSED, expectedUser);
        Task task2 = new Task(2L, "Temp Title", "Test description", Status.CLOSED, expectedUser);
        ArrayList<Task> tasks = new ArrayList<>(List.of(task, task2));

        expectedUser.setTasks(tasks);

        String pathName = "findUserById";
        String documentName = "UserDetails";

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        User user = graphQlTest.documentName(documentName)
                .execute()
                .path(pathName)
                .entity(User.class)
                .get();

        assertEquals(expectedUser.getName(), user.getName());
        assertEquals(expectedUser.getEmail(), user.getEmail());
        assertEquals(expectedUser.getTasks().get(0).getId(), user.getTasks().get(0).getId());
    }

    @Test
    void testFindUserByIdThrowsError() {
        Long userId = 1L;
        User expectedUser = new User("Name", "Email");

        String pathName = "findUserById";
        String documentName = "UserDetails";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(AssertionError.class,
                () -> {
                    User user = graphQlTest.documentName(documentName)
                            .execute()
                            .errors()
                            .verify()
                            .path(pathName)
                            .entity(User.class)
                            .get();
                });


    }

    @Test
    void testAddUser() {
        String name = "Name";
        String email = "Email";
        User expectedUser = new User("Name", "Email");


        String pathName = "addUser";
        String query = String.format("mutation { addUser(user: {name: \"%s\", email: \"%s\"}) {name, email} }", name, email);

        when(userRepository.save(any())).thenReturn(expectedUser);

        User user = graphQlTest.document(query).execute().path(pathName).entity(User.class).get();

        assertNotNull(user);
        assertEquals(user.getName(), name);
        assertEquals(user.getEmail(),email);
    }
}