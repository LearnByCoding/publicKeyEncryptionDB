package com.example.demo.repository;


import com.example.demo.models.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {

    Iterable<Student> findByClassroomid(Long classroomid);
}
