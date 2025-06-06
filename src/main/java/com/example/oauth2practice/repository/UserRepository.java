package com.example.oauth2practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.oauth2practice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByUsername(String username);
}
