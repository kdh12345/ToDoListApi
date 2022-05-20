package com.example.demo.persistence;

import org.springframework.stereotype.Repository;

import com.example.demo.TodoEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ToDoRepository extends JpaRepository<TodoEntity,String> {
	List<TodoEntity> findByUserId(String userId);
}
