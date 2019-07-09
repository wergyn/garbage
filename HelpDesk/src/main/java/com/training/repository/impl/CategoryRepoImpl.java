package com.training.repository.impl;

import com.training.model.Category;
import com.training.repository.CategoryRepo;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepoImpl extends GenericRepoImpl<Category, Long> implements CategoryRepo {
}
