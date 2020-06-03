package com.algaworks.algamoney.api.repository;

import com.algaworks.algamoney.api.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
