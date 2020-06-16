package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.ResourceCreatedEvent;
import com.algaworks.algamoney.api.event.ResourceUpdateEvent;
import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonResource {

    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> list() {
        return personService.findAll();
    }

    @PostMapping
    public ResponseEntity<Person> create(@Valid @RequestBody Person person, HttpServletResponse response) {
        Person personSaved = personService.save(person);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, personSaved.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(personSaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBy(@PathVariable Long id) {
        return ResponseEntity.ok(personService.findBy(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        personService.deleteById(id);
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
