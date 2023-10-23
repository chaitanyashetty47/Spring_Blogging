package com.chaireads.blogs.services.impl;

import com.chaireads.blogs.entities.Category;
import com.chaireads.blogs.entities.User;
import com.chaireads.blogs.exceptions.ResourceNotFoundException;
import com.chaireads.blogs.payloads.categoryDto;
import com.chaireads.blogs.payloads.userDto;
import com.chaireads.blogs.repositories.categoryRepo;
import com.chaireads.blogs.services.categoryService;
import com.chaireads.blogs.services.userService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class categoryServiceImpl implements categoryService {

    @Autowired
    private categoryRepo repo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public categoryDto createCategory(categoryDto cateDto) {
        Category cate = this.modelMapper.map(cateDto,Category.class);
        Category addedCate = this.repo.save(cate);

        return this.modelMapper.map(addedCate,categoryDto.class);
    }

    @Override
    public categoryDto updateCategory(categoryDto cateDto, Integer categoryId) {
        Category cate = this.repo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", categoryId));

        cate.setCategoryTitle(cateDto.getTitle());
        cate.setCategoryDescription(cateDto.getDescription());

        Category updatedCategory = this.repo.save(cate);

        return this.modelMapper.map(updatedCategory,categoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category cate = this.repo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", categoryId));
        this.repo.delete(cate);
    }

    @Override
    public categoryDto getCate(Integer Id) {
        Category cate = this.repo.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", Id));
        return this.modelMapper.map(cate,categoryDto.class);
    }

    @Override
    public List<categoryDto> getAllCate() {
        List<Category> cate = this.repo.findAll();
        List<categoryDto> cateDtos = cate.stream().map(singleCate -> this.modelMapper.map(singleCate,categoryDto.class)).collect(Collectors.toList());
        return cateDtos;
    }
}
