package com.algaworks.algamoney.api.repository;

import com.algaworks.algamoney.api.model.Entry;
import com.algaworks.algamoney.api.repository.entry.EntryRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long>, EntryRepositoryQuery {
}
