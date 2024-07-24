package com.example.finance_tracker.repository;

import com.example.finance_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
  List<User> findByDeletedFalse();
}
