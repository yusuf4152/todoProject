package com.coding.todoproject.repository;

import com.coding.todoproject.entity.Todo;
import com.coding.todoproject.service.TodoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Testcontainers
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class TodoRepositoryTest {

    @Container
    public static MongoDBContainer container = new MongoDBContainer();

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
    }

    @Autowired
    private TodoRepository todoRepository;


    @AfterEach
    void cleanUp() {
        this.todoRepository.deleteAll();
    }

    @Test
    public void testSave() {

        Todo todo = new Todo();
        todo.setTitle("Test");

        // Save the entity to the repository
        Todo savedTodo = todoRepository.save(todo);

        // Verify that th
        assertEquals(todo.getTitle(), savedTodo.getTitle());
    }

    @Test
    public void testFindById(){
        Todo todo1=new Todo();
        todo1.setTitle("asdf");
        todo1.setDate(LocalDate.of(2022,3,2));

        Todo todo2=new Todo();
        todo1.setTitle("asdf");
        todo1.setDate(LocalDate.of(2022,3,2));
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        List<Todo> todos=todoRepository.findAll();
        assertEquals(2,todos.size());
    }
}