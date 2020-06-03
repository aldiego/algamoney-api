package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/people")
public class PersonResource {

    @Autowired
    private PersonRepository repository;

    @GetMapping
    public List<Person> list() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        Person personSaved = repository.save(person);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(personSaved.getId()).toUri();

        return ResponseEntity.created(uri).body(personSaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBy(@PathVariable Long id) {
        Optional<Person> byId = repository.findById(id);

        return byId.isPresent() ? ResponseEntity.ok(byId.get()) : ResponseEntity.notFound().build();
    }
}
