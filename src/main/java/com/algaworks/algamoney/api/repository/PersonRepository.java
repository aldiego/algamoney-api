package com.algaworks.algamoney.api.repository;

import com.algaworks.algamoney.api.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Page<Person> findByNameContaining(String name, Pageable pageable);
}
