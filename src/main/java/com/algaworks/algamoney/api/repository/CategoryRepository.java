package com.algaworks.algamoney.api.repository;

import com.algaworks.algamoney.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
