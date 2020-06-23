package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.event.ResourceCreatedEvent;
import com.algaworks.algamoney.api.event.ResourceUpdateEvent;
import com.algaworks.algamoney.api.model.Entry;
import com.algaworks.algamoney.api.repository.filter.EntryFilter;
import com.algaworks.algamoney.api.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/entries")
public class EntryResource {

    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private EntryService service;

    @GetMapping
    public Page<Entry> search(EntryFilter filter, Pageable pageable) {
        return service.search(filter, pageable);
    }

    @PostMapping
    public ResponseEntity<Entry> create(@Valid @RequestBody Entry person, HttpServletResponse response) {
        var saved = service.save(person);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, saved.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBy(@PathVariable Long id) {
        return ResponseEntity.ok(service.findBy(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entry> update(@PathVariable Long id, @Valid @RequestBody Entry entry, HttpServletResponse response) {
        var updated = service.update(id, entry);

        publisher.publishEvent(new ResourceUpdateEvent(this, response, updated.getId()));
        return ResponseEntity.ok(updated);

    }


}
