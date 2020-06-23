package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Category;
import com.algaworks.algamoney.api.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category update(Long id, Category toBeUpdate) {
        var saved = findBy(id);
        BeanUtils.copyProperties(toBeUpdate, saved, "id");

        return repository.save(toBeUpdate);
    }

    public Category save(Category category) {
        return repository.save(category);
    }


    public Category findBy(Long id) {
        var saved = repository.findById(id);
        if (saved.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        return saved.get();
    }

    public List<Category> findAll() {
        var all = repository.findAll();
        if (all.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        return all;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
