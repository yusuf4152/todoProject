package com.coding.todoproject.service;

import com.coding.todoproject.dto.Request.TodoCreateDto;
import com.coding.todoproject.dto.Request.TodoUpdateDto;
import com.coding.todoproject.dto.Response.TodoGetDto;
import com.coding.todoproject.dto.converter.TodoGetDtoConverter;
import com.coding.todoproject.entity.Todo;
import com.coding.todoproject.repository.TodoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;
    @Mock
    private TodoRepository todoRepository;
    @Mock
    private TodoGetDtoConverter todoGetDtoConverter;

    @Test
    void createTodo() {
        //given
        TodoCreateDto todoCreateDto = new TodoCreateDto();
        todoCreateDto.setTitle("kosu yap");
        todoCreateDto.setDate(LocalDate.of(2022, 12, 1));

        Todo todo = new Todo();
        todo.setId("asdf");
        todo.setMarked(false);
        todo.setCreatedAt(LocalDateTime.of(2022, 4, 12, 12, 12));
        todo.setTitle("kosu yap");
        todo.setDate(LocalDate.of(2022, 12, 1));

        TodoGetDto todoGetDto = new TodoGetDto();
        todoGetDto.setId("asdf");
        todoGetDto.setMarked(false);
        todoGetDto.setTitle("kosu yap");
        todoGetDto.setCreatedAt(LocalDateTime.of(2022, 4, 12, 12, 12));
        todoGetDto.setDate(LocalDate.of(2022, 12, 1));

        //when
        when(todoRepository.save(todo)).thenReturn(todo);
        when(todoGetDtoConverter.convert(todo)).thenReturn(todoGetDto);
        TodoGetDto result = todoService.createTodo(todoCreateDto);
        //then
        Mockito.verify(todoRepository).save(todo);
        Mockito.verify(todoGetDtoConverter).convert(todo);
        then(result).isEqualTo(todoGetDto);

    }

    @Test
    void findAll() {
        List<Todo> todos = new ArrayList<>();
        Todo todo = new Todo();
        todo.setId("asdf");
        todo.setMarked(false);
        todo.setTitle("kosu yap");
        todo.setDate(LocalDate.of(2022, 12, 1));

        Todo todo1 = new Todo();
        todo1.setId("1234");
        todo1.setMarked(false);
        todo1.setTitle("kosu yap");
        todo1.setDate(LocalDate.of(2022, 12, 2));
        todos.add(todo);
        todos.add(todo1);

        List<TodoGetDto> todoGetDtos = new ArrayList<>();

        TodoGetDto todoGetDto = new TodoGetDto();
        todoGetDto.setId("asdf");
        todoGetDto.setMarked(false);
        todoGetDto.setTitle("kosu yap");
        todoGetDto.setDate(LocalDate.of(2022, 12, 1));

        TodoGetDto todoGetDto1 = new TodoGetDto();
        todoGetDto1.setId("1234");
        todoGetDto1.setMarked(false);
        todoGetDto1.setTitle("kosu yap");
        todoGetDto1.setDate(LocalDate.of(2022, 12, 2));
        todoGetDtos.add(todoGetDto);
        todoGetDtos.add(todoGetDto1);

        when(todoRepository.findAll(Sort.by(Sort.Direction.DESC, "date"))).thenReturn(todos);
        when(todoGetDtoConverter.convert(todo)).thenReturn(todoGetDto);
        when(todoGetDtoConverter.convert(todo1)).thenReturn(todoGetDto1);
        List<TodoGetDto> results = todoService.findAll();
        then(results).isEqualTo(todoGetDtos);
    }

    @Test
    void deleteTodoByIdWhenTodoExist() {
        //given

        Todo todo = new Todo();
        todo.setId("asdf");
        todo.setMarked(false);
        todo.setCreatedAt(LocalDateTime.of(2022, 4, 12, 12, 12));
        todo.setTitle("kosu yap");
        todo.setDate(LocalDate.of(2022, 12, 1));

        TodoGetDto todoGetDto = new TodoGetDto();
        todoGetDto.setId("asdf");
        todoGetDto.setMarked(false);
        todoGetDto.setTitle("kosu yap");
        todoGetDto.setCreatedAt(LocalDateTime.of(2022, 4, 12, 12, 12));
        todoGetDto.setDate(LocalDate.of(2022, 12, 1));

        //when
        when(todoRepository.findById("asdf")).thenReturn(Optional.of(todo));
        when(todoGetDtoConverter.convert(todo)).thenReturn(todoGetDto);
        TodoGetDto result = todoService.deleteTodoById(todo.getId());
        //then
        verify(todoRepository).findById(todo.getId());
        verify(todoRepository).delete(todo);
        then(result).isEqualTo(todoGetDto);
    }

    @Test
    void deleteTodoByIdWhenTodoNotExist() {
        //given
        String Id = "12345";
        String message = Id + " " + "not found";
        //when
        when(todoRepository.findById(Id)).thenReturn(Optional.empty());
        IllegalArgumentException result = assertThrows(IllegalArgumentException.class, () -> todoService.deleteTodoById(Id));
        //then
        verify(todoRepository).findById(Id);
        then(result.getMessage()).isEqualTo(message);
    }

    @Test
    void findAllWithStrikeTrough() {
    }

    @Test
    void updateTodoByIdWhenTodoExist() {
        TodoUpdateDto todoUpdateDto = new TodoUpdateDto();
        todoUpdateDto.setMarked(true);
        todoUpdateDto.setTitle("kosu yap");
        todoUpdateDto.setDate(LocalDate.of(2022, 12, 1));
        todoUpdateDto.setId("asdf");

        TodoGetDto getDto = new TodoGetDto();
        getDto.setId("asdf");
        getDto.setMarked(true);
        getDto.setDate(LocalDate.of(2022, 12, 1));
        getDto.setTitle("kosu yap");
        getDto.setCreatedAt(LocalDateTime.of(2022, 4, 12, 12, 12));

        Todo updateTodo = new Todo();
        updateTodo.setId("asdf");
        updateTodo.setDate(LocalDate.of(2022, 12, 1));
        updateTodo.setTitle("kosu yap");
        updateTodo.setMarked(true);
        updateTodo.setCreatedAt(LocalDateTime.of(2022, 4, 12, 12, 12));

        when(todoRepository.findById("asdf")).thenReturn(Optional.of(updateTodo));
        when(todoRepository.save(updateTodo)).thenReturn(updateTodo);
        when(todoGetDtoConverter.convert(updateTodo)).thenReturn(getDto);
        TodoGetDto result = todoService.updateTodoById(todoUpdateDto);
        then(result).isEqualTo(getDto);
    }

    @Test
    void updateTodoIdWhenNotExist() {

        TodoUpdateDto todoUpdateDto = new TodoUpdateDto();
        todoUpdateDto.setMarked(true);
        todoUpdateDto.setTitle("kosu yap");
        todoUpdateDto.setDate(LocalDate.of(2022, 12, 1));
        todoUpdateDto.setId("asdf");
        String message = todoUpdateDto.getId() + " " + "not found";
        //when
        when(todoRepository.findById(todoUpdateDto.getId())).thenReturn(Optional.empty());
        IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
                () -> todoService.updateTodoById(todoUpdateDto));
        //then
        verify(todoRepository).findById(todoUpdateDto.getId());
        then(result.getMessage()).isEqualTo(message);

    }
}