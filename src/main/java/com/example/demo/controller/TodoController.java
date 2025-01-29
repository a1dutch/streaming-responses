package com.example.demo.controller;

import com.example.demo.entity.Todo;
import com.example.demo.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.Globals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Todo> create(Todo todo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(todo));
    }

    @PostMapping("/report")
    public ResponseEntity<StreamingResponseBody> report(HttpServletRequest request) {
        request.setAttribute(Globals.ASYNC_SUPPORTED_ATTR, true);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "text/csv");
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.csv");

        final StreamingResponseBody body = new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                outputStream.write("id,task\n".getBytes());
                service.streamAll().forEach(todo -> {
                    try {
                        outputStream.write(todo.getId().getBytes());
                        outputStream.write(",".getBytes());
                        outputStream.write(todo.getTask().getBytes());
                        outputStream.write("\n".getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        };

        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }
}
