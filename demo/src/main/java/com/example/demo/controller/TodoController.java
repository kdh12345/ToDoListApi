package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.swing.RepaintManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.TodoEntity;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	@GetMapping("/test")
	public ResponseEntity<?> testTodo(){
		String str = todoService.testService();
		List<String> list = new ArrayList<String>();
		list.add(str);
		ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping
	public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
		try {
			
			TodoEntity todoEntity = TodoDTO.toEntity(dto);
			
			todoEntity.setId(null); // 초기화
			
			todoEntity.setUserId(userId);
			
			List<TodoEntity> entities = todoService.create(todoEntity);
			
			//todoDTO 리스트로 변환
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok(response);
		}catch(Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
		
		List<TodoEntity> entities = todoService.retrieve(userId);
		
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok(response);
	}
	
	@PutMapping
	public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
		
		TodoEntity todoEntity = TodoDTO.toEntity(dto);
		
		todoEntity.setUserId(userId);
		
		List<TodoEntity> entities = todoService.update(todoEntity);
		
		//todoDTO 리스트로 변환
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto){
	
		try {
			
			TodoEntity todoEntity = TodoDTO.toEntity(dto);
			
			todoEntity.setUserId(userId);
			
			List<TodoEntity> entities = todoService.delete(todoEntity);
			
			//todoDTO 리스트로 변환
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			
			return ResponseEntity.ok(response);
		}catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
}
