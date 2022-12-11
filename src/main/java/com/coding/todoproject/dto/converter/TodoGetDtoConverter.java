package com.coding.todoproject.dto.converter;

import com.coding.todoproject.dto.Response.TodoGetDto;
import com.coding.todoproject.entity.Todo;
import org.springframework.stereotype.Component;


@Component
public class TodoGetDtoConverter {

    public TodoGetDto convert(Todo from) {
        TodoGetDto todoGetDto = new TodoGetDto();
        todoGetDto.setId(from.getId());
        todoGetDto.setDate(from.getDate());
        todoGetDto.setTitle(from.getTitle());
        todoGetDto.setCreatedAt(from.getCreatedAt());
        todoGetDto.setMarked(from.isMarked());
        return todoGetDto;
    }
}
