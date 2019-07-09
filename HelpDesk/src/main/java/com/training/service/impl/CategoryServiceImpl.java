package com.training.service.impl;

import com.training.model.Category;
import com.training.repository.CategoryRepo;
import com.training.service.CategoryService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CategoryServiceImpl implements CategoryService {

    CategoryRepo categoryRepo;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }
}
