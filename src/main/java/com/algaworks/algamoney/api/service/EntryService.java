package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Entry;
import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.EntryRepository;
import com.algaworks.algamoney.api.repository.filter.EntryFilter;
import com.algaworks.algamoney.api.service.exception.InactiveOrNonExistentPersonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class EntryService {

    @Autowired
    private EntryRepository repository;
    @Autowired
    private PersonService service;

    public Entry update(Long id, Entry toBeUpdate) {
        Entry saved = findBy(id);
        BeanUtils.copyProperties(toBeUpdate, saved, "id");

        return repository.save(toBeUpdate);
    }

    public Entry save(Entry entry) {
        Optional<Person> optionalBy = service.findOptionalBy(entry.getPerson().getId());
        if (optionalBy.isEmpty() || optionalBy.get().isInactive()) {
            throw new InactiveOrNonExistentPersonException();
        }

        return repository.save(entry);
    }


    public Entry findBy(Long id) {
        Optional<Entry> saved = repository.findById(id);
        if (saved.isEmpty()) {
            throw new EmptyResultDataAccessException(1);
        }
        return saved.get();
    }

    public Page<Entry> search(EntryFilter filter, Pageable pageable) {
        return repository.filter(filter, pageable);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
