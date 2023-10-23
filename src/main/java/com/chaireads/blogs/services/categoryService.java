package com.chaireads.blogs.services;

import com.chaireads.blogs.payloads.categoryDto;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface categoryService {
    categoryDto createCategory(categoryDto category);

    categoryDto updateCategory(categoryDto category,Integer categoryId);

    void deleteCategory(Integer categoryId);

    categoryDto getCate(Integer Id);

    List<categoryDto> getAllCate();
}
