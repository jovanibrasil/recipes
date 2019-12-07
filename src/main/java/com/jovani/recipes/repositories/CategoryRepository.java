package com.jovani.recipes.repositories;

import com.jovani.recipes.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> { }
