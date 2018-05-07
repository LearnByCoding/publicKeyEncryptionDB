package com.example.demo.repository;

import com.example.demo.models.Emessage;
import org.springframework.data.repository.CrudRepository;

public interface EmessageRepository extends CrudRepository<Emessage, Long> {
}
