package com.algaworks.algamoney.api.repository.entry;

import com.algaworks.algamoney.api.model.Entry;
import com.algaworks.algamoney.api.repository.filter.EntryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepositoryQuery {

    public Page<Entry> filter(EntryFilter entryFilter, Pageable pageable);
}
