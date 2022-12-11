package com.coding.todoproject.controller;

import com.coding.todoproject.dto.Request.TodoCreateDto;
import com.coding.todoproject.dto.Request.TodoUpdateDto;
import com.coding.todoproject.dto.Response.TodoGetDto;
import com.coding.todoproject.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
@AutoConfigureMockMvc
class TodoControllerTest {

    @MockBean
    private TodoService todoService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void CreateTodo() throws Exception {

        TodoCreateDto todoCreateDto = new TodoCreateDto();
        todoCreateDto.setTitle("kosu yap");
        todoCreateDto.setDate(LocalDate.of(2022, 12, 1));

        TodoGetDto todoGetDto = new TodoGetDto();
        todoGetDto.setId("asdf");
        todoGetDto.setMarked(false);
        todoGetDto.setTitle("kosu yap");
        todoGetDto.setCreatedAt(LocalDateTime.of(2022, 4, 12, 12, 12));
        todoGetDto.setDate(LocalDate.of(2022, 12, 1));

        when(todoService.createTodo(todoCreateDto)).thenReturn(todoGetDto);
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/todo/createTodo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoCreateDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        verify(todoService).createTodo(eq(todoCreateDto));
        then(result).isEqualTo(objectMapper.writeValueAsString(todoGetDto));
    }

    @Test
    void findAll() throws Exception {
        List<TodoGetDto> todoGetDtos = new ArrayList<>();
        TodoGetDto todoGetDto = new TodoGetDto();
        todoGetDto.setId("asdf");
        todoGetDto.setMarked(false);
        todoGetDto.setTitle("kosu yap");
        todoGetDto.setCreatedAt(LocalDateTime.of(2022, 4, 12, 12, 12));
        todoGetDto.setDate(LocalDate.of(2022, 12, 1));

        TodoGetDto todoGetDto1 = new TodoGetDto();
        todoGetDto1.setId("1234");
        todoGetDto1.setDate(LocalDate.of(2022, 4, 1));
        todoGetDto1.setMarked(true);
        todoGetDto1.setCreatedAt(LocalDateTime.of(2022, 3, 1, 12, 20));
        todoGetDto1.setTitle("temiz");
        todoGetDtos.add(todoGetDto);
        todoGetDtos.add(todoGetDto1);

        when(todoService.findAll()).thenReturn(todoGetDtos);
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/todo/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())

                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        String expected = objectMapper.writeValueAsString(todoGetDtos);
        then(result).isEqualTo(expected);
    }

    @Test
    void deleteById() throws Exception {
        String id = "asdf";
        TodoGetDto todoGetDto = new TodoGetDto();
        todoGetDto.setId("asdf");
        todoGetDto.setMarked(false);
        todoGetDto.setTitle("kosu yap");
        todoGetDto.setCreatedAt(LocalDateTime.of(2022, 4, 12, 12, 12));
        todoGetDto.setDate(LocalDate.of(2022, 12, 1));
        when(todoService.deleteTodoById(id)).thenReturn(todoGetDto);
        MvcResult mvcResult = mockMvc.perform(delete("/api/v1/todo/deleteById/asdf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        verify(todoService).deleteTodoById(id);
        then(result).isEqualTo(objectMapper.writeValueAsString(todoGetDto));

    }

    @Test
    void updateTodoById() throws Exception {
        TodoUpdateDto todoUpdateDto = new TodoUpdateDto();
        todoUpdateDto.setId("asdf");
        todoUpdateDto.setMarked(false);
        todoUpdateDto.setTitle("kosu yap");
        todoUpdateDto.setDate(LocalDate.of(2022, 12, 1));

        TodoGetDto todoGetDto = new TodoGetDto();
        todoGetDto.setId("asdf");
        todoGetDto.setMarked(false);
        todoGetDto.setTitle("kosu yap");
        todoGetDto.setCreatedAt(LocalDateTime.of(2022, 4, 12, 12, 12));
        todoGetDto.setDate(LocalDate.of(2022, 12, 1));

        when(todoService.updateTodoById(any())).thenReturn(todoGetDto);

        MvcResult mvcResult = mockMvc.perform(put("/api/v1/todo/updateTodoById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoUpdateDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
       then(result).isEqualTo(objectMapper.writeValueAsString(todoGetDto));

    }
}
