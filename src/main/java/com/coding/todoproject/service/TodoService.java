package com.coding.todoproject.service;

import com.coding.todoproject.dto.Request.TodoCreateDto;
import com.coding.todoproject.dto.Request.TodoUpdateDto;
import com.coding.todoproject.dto.Response.TodoGetDto;
import com.coding.todoproject.dto.converter.TodoGetDtoConverter;
import com.coding.todoproject.entity.Todo;
import com.coding.todoproject.repository.TodoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNullElse;


@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final TodoGetDtoConverter todoGetDtoConverter;

    public TodoService(TodoRepository todoRepository, TodoGetDtoConverter todoGetDtoConverter) {
        this.todoRepository = todoRepository;
        this.todoGetDtoConverter = todoGetDtoConverter;
    }

    public TodoGetDto createTodo(TodoCreateDto todoCreateDto) {
        Todo todo = new Todo();
        todo.setDate(todoCreateDto.getDate());
        todo.setTitle(todoCreateDto.getTitle());
        Todo saved = todoRepository.save(todo);
        return todoGetDtoConverter.convert(saved);
    }

    public List<TodoGetDto> findAll() {
        List<Todo> todos = todoRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
        return todos.stream().map(todoGetDtoConverter::convert).collect(Collectors.toList());
    }


    public TodoGetDto deleteTodoById(String id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(id + " " + "not found"));
        todoRepository.delete(todo);
        return todoGetDtoConverter.convert(todo);
    }

    public List<TodoGetDto> findAllWithStrikeTrough() {
        List<Todo> todos = todoRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
        List<Todo> strikeThroughTodos = new ArrayList<>();
        for (Todo currentTodo : todos) {
            if (currentTodo.isMarked()) {
                currentTodo.setTitle("-------");
            }
            strikeThroughTodos.add(currentTodo);
        }
        return strikeThroughTodos.stream().map(todoGetDtoConverter::convert).collect(Collectors.toList());
    }

    public TodoGetDto updateTodoById(TodoUpdateDto todoUpdateDto) {
        Todo todo = todoRepository.findById(todoUpdateDto.getId())
                .orElseThrow(() -> new IllegalArgumentException(todoUpdateDto.getId() + " " + "not found"));
        todo.setMarked(requireNonNullElse(todoUpdateDto.isMarked(), todo.isMarked()));
        todo.setDate(requireNonNullElse(todoUpdateDto.getDate(), todo.getDate()));
        todo.setTitle(requireNonNullElse(todoUpdateDto.getTitle(), todo.getTitle()));
        Todo saved = todoRepository.save(todo);
        return todoGetDtoConverter.convert(saved);
    }
}
