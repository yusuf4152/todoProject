package com.coding.todoproject.controller;

import com.coding.todoproject.dto.Request.TodoCreateDto;
import com.coding.todoproject.dto.Request.TodoUpdateDto;
import com.coding.todoproject.dto.Response.TodoGetDto;
import com.coding.todoproject.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/createTodo")
    public ResponseEntity<TodoGetDto> createTodo(@RequestBody TodoCreateDto todo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(todo));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<TodoGetDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.findAll());
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<TodoGetDto> deleteById(@PathVariable String id) {

        return ResponseEntity.status(HttpStatus.OK).body(todoService.deleteTodoById(id));
    }

    @GetMapping("/findAllByStrikeThrough")
    public ResponseEntity<List<TodoGetDto>> findAllByStrikeThrough() {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.findAllWithStrikeTrough());
    }

    @PutMapping("/updateTodoById")
    public ResponseEntity<TodoGetDto> updateTodoById( @RequestBody TodoUpdateDto todoUpdateDto) {
        return ResponseEntity.status(HttpStatus.OK).body(todoService.updateTodoById(todoUpdateDto));
    }

}
