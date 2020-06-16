package com.algaworks.algamoney.api.repository.entry;

import com.algaworks.algamoney.api.model.Entry;
import com.algaworks.algamoney.api.model.Entry_;
import com.algaworks.algamoney.api.repository.filter.EntryFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EntryRepositoryImpl implements EntryRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Entry> filter(EntryFilter entryFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Entry> criteria = builder.createQuery(Entry.class);
        Root<Entry> root = criteria.from(Entry.class);

        Predicate[] predicates = createRestriction(entryFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Entry> query = manager.createQuery(criteria);
        addPageRestriction(query, pageable);


        return new PageImpl<>(query.getResultList(), pageable, total(entryFilter));
    }


    private Predicate[] createRestriction(EntryFilter entryFilter, CriteriaBuilder builder, Root<Entry> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.isNotBlank(entryFilter.getDescription()))
            predicates.add(builder.like(
                    builder.lower(root.get(Entry_.description)), "%" + entryFilter.getDescription().toLowerCase() + "%"
            ));

        if (!Objects.isNull(entryFilter.getDueDateFrom()))
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get(Entry_.dueDate), entryFilter.getDueDateFrom())
            );

        if (!Objects.isNull(entryFilter.getDueDateTo()))
            predicates.add(
                    builder.lessThanOrEqualTo(root.get(Entry_.dueDate), entryFilter.getDueDateTo())
            );

        return predicates.toArray(new Predicate[predicates.size()]);
    }


    private void addPageRestriction(TypedQuery<Entry> query, Pageable pageable) {
        int actualPage = pageable.getPageNumber();
        int totalRegistriesPerPage = pageable.getPageSize();
        int firstRegistryOfPage = actualPage * totalRegistriesPerPage;

        query.setFirstResult(firstRegistryOfPage);
        query.setMaxResults(totalRegistriesPerPage);
    }


    private Long total(EntryFilter entryFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Entry> root = criteria.from(Entry.class);

        Predicate[] predicates = createRestriction(entryFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();
    }

}
