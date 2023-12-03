package com.example.GraphQL_demo.repositiry;

import com.example.GraphQL_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
