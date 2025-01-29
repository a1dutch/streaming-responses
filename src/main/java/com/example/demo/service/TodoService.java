package com.example.demo.service;

import com.example.demo.entity.Todo;
import com.example.demo.repository.TodoRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.Globals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
public class TodoService {

    @Autowired
    private TodoRepository repository;

    @PostConstruct
    public void init() {
        IntStream.range(0, 100).forEach(c -> repository.save(Todo.builder().task("task " + c).build()));
    }

    @Transactional
    public Todo create(Todo todo) {
        return repository.save(todo);
    }

    @Transactional
    public Stream<Todo> streamAll() {
        return repository.streamAll();
    }
}
