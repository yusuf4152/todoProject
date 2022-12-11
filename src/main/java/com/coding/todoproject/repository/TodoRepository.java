package com.coding.todoproject.repository;

import com.coding.todoproject.entity.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface TodoRepository extends MongoRepository<Todo, String> {

}
