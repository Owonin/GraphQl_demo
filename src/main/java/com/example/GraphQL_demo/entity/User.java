package com.example.GraphQL_demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String Name;

    private String Email;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks = new ArrayList<>();

    public User(String name, String email) {
        Name = name;
        Email = email;
    }
}
