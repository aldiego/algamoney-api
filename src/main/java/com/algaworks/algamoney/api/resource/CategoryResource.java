package com.algaworks.algamoney.api.resource;

import com.algaworks.algamoney.api.model.Category;
import com.algaworks.algamoney.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    @Autowired
    private CategoryRepository repository;

    @GetMapping
    public List<Category> list() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category category) {
        Category categorySaved = repository.save(category);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(categorySaved.getId()).toUri();

        return ResponseEntity.created(uri).body(categorySaved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBy(@PathVariable Long id) {
        Optional<Category> byId = repository.findById(id);

       return byId.isPresent() ? ResponseEntity.ok(byId.get()) : ResponseEntity.notFound().build();
    }
}
