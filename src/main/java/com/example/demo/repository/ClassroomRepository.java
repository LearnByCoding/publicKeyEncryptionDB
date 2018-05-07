package com.example.demo.repository;

import com.example.demo.models.Classroom;
import org.springframework.data.repository.CrudRepository;

public interface ClassroomRepository extends CrudRepository< Classroom, Long> {
}
