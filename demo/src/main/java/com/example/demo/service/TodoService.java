package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.TodoEntity;
import com.example.demo.persistence.ToDoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService extends Exception{
	
	@Autowired
	private ToDoRepository toDoRepository;
	
	public String testService() {
		// ToDoEntity생성
		TodoEntity entity = TodoEntity.builder().title("My fisrt todo item").build();
		// ToDoEntity저장
		toDoRepository.save(entity);
		// ToDoEntity검색
		TodoEntity savedEntity = toDoRepository.findById(entity.getId()).get();
		return savedEntity.getTitle();
	}
	
	public List<TodoEntity> create(final TodoEntity entity){
		//검증
		validate(entity);
		
		toDoRepository.save(entity);
		
		log.info("Entity Id : {} is saved.", entity.getId());
		
		return toDoRepository.findByUserId(entity.getUserId());
	}
	
	private void validate(final TodoEntity entity) {
		
		//검증
		if(entity == null) {
			log.warn("Entity cannot be null!");
			throw new RuntimeException("Entity cannot be null!");
		}
		
		if(entity.getUserId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}
	
	public List<TodoEntity> retrieve(final String userId){
		return toDoRepository.findByUserId(userId);
	}
	
	public List<TodoEntity> update(final TodoEntity entity){
		//검증
		validate(entity);
		
		final Optional<TodoEntity> original = toDoRepository.findById(entity.getId());
		
		if(original.isPresent()) {
			
			final TodoEntity todo = original.get();
			
			todo.setTitle(entity.getTitle());
			todo.setDone(todo.isDone());
			
			toDoRepository.save(todo);
		}
		
		return retrieve(entity.getUserId());
	}
	
	public List<TodoEntity> delete(final TodoEntity entity){
		//검증
		validate(entity);
		
		try {
			toDoRepository.delete(entity);
		}catch(Exception e) {
			
			log.error("error deleting entity",entity.getId(),e);
			
			throw new RuntimeException("error deleting entity "+entity.getId());
		}
		return retrieve(entity.getUserId());
	}


}
