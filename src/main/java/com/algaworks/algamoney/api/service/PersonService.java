package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public Person update(Long id, Person toBeUpdate) {
        var savedPerson = findBy(id);
        BeanUtils.copyProperties(toBeUpdate, savedPerson, "id");

        return repository.save(toBeUpdate);
    }

    public Person updateActive(Long id, Boolean active) {
        var savedPerson = findBy(id);
        savedPerson.setActive(active);

        return repository.save(savedPerson);
    }

    public Person findBy(Long id) {
        var savedPerson = findOptionalBy(id);
        if (savedPerson.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        return savedPerson.get();
    }

    public Optional<Person> findOptionalBy(Long id) {
        return repository.findById(id);
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person save(Person person) {
        return repository.save(person);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Page<Person> findByNameContaining(String name, Pageable pageable) {
        return repository.findByNameContaining(name, pageable);
    }
}
