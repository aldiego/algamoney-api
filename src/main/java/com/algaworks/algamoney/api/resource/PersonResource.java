package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.ResourceCreatedEvent;
import com.algaworks.algamoney.api.event.ResourceUpdateEvent;
import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.PersonRepository;
import com.algaworks.algamoney.api.service.PersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PersonResource {

    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private PersonRepository repository;
    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> list() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Person> create(@Valid @RequestBody Person person, HttpServletResponse response) {
        Person personSaved = repository.save(person);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, personSaved.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(personSaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBy(@PathVariable Long id) {
        Optional<Person> byId = repository.findById(id);

        return byId.isPresent() ? ResponseEntity.ok(byId.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable Long id, @Valid @RequestBody Person person, HttpServletResponse response) {
        Person updated = personService.update(id, person);

        publisher.publishEvent(new ResourceUpdateEvent(this, response, updated.getId()));
        return ResponseEntity.ok(updated);

    }

    @PutMapping("/{id}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Boolean active, HttpServletResponse response) {
        Person updated = personService.updateActive(id, active);

        publisher.publishEvent(new ResourceUpdateEvent(this, response, updated.getId()));

    }
}
