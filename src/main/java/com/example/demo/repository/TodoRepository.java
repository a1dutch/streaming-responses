package com.example.demo.repository;

import com.example.demo.entity.Todo;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Stream;

@Repository

public interface TodoRepository extends CrudRepository<Todo, UUID> {

    @Query("FROM Todo")
//    @QueryHints({@QueryHint(name = "org.hibernate.fetchSize", value = "10")})
    Stream<Todo> streamAll();
}
