package com.example.GraphQL_demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Task(String title, String description, Status status, User user) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.user = user;
    }
}
