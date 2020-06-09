package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public Person update(Long id, Person toBeUpdate) {
        Person savedPerson = getPerson(id);
        BeanUtils.copyProperties(toBeUpdate, savedPerson, "id");

        return repository.save(toBeUpdate);
    }

    public Person updateActive(Long id, Boolean active) {
        Person savedPerson = getPerson(id);
        savedPerson.setActive(active);

        return repository.save(savedPerson);
    }

    private Person getPerson(Long id) {
        Optional<Person> savedPerson = repository.findById(id);
        if (savedPerson.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        return savedPerson.get();
    }
}
