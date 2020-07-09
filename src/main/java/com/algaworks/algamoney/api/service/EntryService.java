package com.algaworks.algamoney.api.service;

import com.algaworks.algamoney.api.model.Entry;
import com.algaworks.algamoney.api.model.Person;
import com.algaworks.algamoney.api.repository.EntryRepository;
import com.algaworks.algamoney.api.repository.filter.EntryFilter;
import com.algaworks.algamoney.api.repository.projection.EntryResume;
import com.algaworks.algamoney.api.service.exception.InactiveOrNonExistentPersonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class EntryService {

    @Autowired
    private EntryRepository repository;
    @Autowired
    private PersonService personService;

    public Entry save(Entry entry) {
        validatePerson(entry);
        return repository.save(entry);
    }

    public Entry update(Long id, Entry entry) {
        var saved = findBy(id);
        if (!entry.getPerson().equals(saved.getPerson())) {
            validatePerson(entry);
        }

        BeanUtils.copyProperties(entry, saved, "id");

        return repository.save(entry);
    }


    private void validatePerson(Entry entry) {
        Person person = null;
        if (Objects.nonNull(entry.getPerson().getId())) {
            person = personService.findBy(entry.getPerson().getId());
        }

        if (Objects.isNull(person) || person.isInactive()) {
            throw new InactiveOrNonExistentPersonException();
        }
    }

    public Entry findBy(Long id) {
        var saved = repository.findById(id);
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

    public Page<EntryResume> resume(EntryFilter filter, Pageable pageable) {
        return repository.resume(filter, pageable);
    }
}
