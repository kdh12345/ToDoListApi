package com.example.demo.dto;

import com.example.demo.TodoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {

	private String id;
	private String title;
	private boolean done;
	
	public TodoDTO(final TodoEntity todoEntity) {
		this.id    = todoEntity.getId();
		this.title = todoEntity.getTitle();
		this.done  = todoEntity.isDone();
	}
	
	public static TodoEntity toEntity(final TodoDTO dto) {
		return TodoEntity.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.done(dto.isDone())
				.build();
	}
}
