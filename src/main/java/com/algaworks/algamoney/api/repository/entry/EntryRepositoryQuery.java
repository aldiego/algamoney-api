package com.algaworks.algamoney.api.repository.entry;

import com.algaworks.algamoney.api.model.Entry;
import com.algaworks.algamoney.api.repository.filter.EntryFilter;
import com.algaworks.algamoney.api.repository.projection.EntryResume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntryRepositoryQuery {

    Page<Entry> filter(EntryFilter entryFilter, Pageable pageable);

    Page<EntryResume> resume(EntryFilter entryFilter, Pageable pageable);
}
