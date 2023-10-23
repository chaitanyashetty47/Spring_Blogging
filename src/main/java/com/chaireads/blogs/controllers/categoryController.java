package com.chaireads.blogs.controllers;

import com.chaireads.blogs.payloads.ApiResponse;
import com.chaireads.blogs.payloads.categoryDto;
import com.chaireads.blogs.services.categoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class categoryController {
    @Autowired
    private categoryService cateService;

    //create category
    @PostMapping(value="/")
    public ResponseEntity<categoryDto> createCategory(@Valid @RequestBody categoryDto cateDto){
        categoryDto createUser = this.cateService.createCategory(cateDto);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    //update category
    @PutMapping(value = "/{categoryId}")
    public ResponseEntity<categoryDto> updateCategory(@Valid @RequestBody categoryDto cateDto, @PathVariable("categoryId") Integer cid){
        categoryDto updateCate = this.cateService.updateCategory(cateDto,cid);
        return ResponseEntity.ok(updateCate);
    }

    //delete category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("categoryId") Integer cid){
        this.cateService.deleteCategory(cid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category " + cid + " Deleted Successfully",true),HttpStatus.OK);

    }

    //get all category
    @GetMapping("/")
    public ResponseEntity<List<categoryDto>> getAllCategories(){
        List<categoryDto> allCategories = this.cateService.getAllCate();
        return ResponseEntity.ok(allCategories);
    }

    //get category
    @GetMapping("/{categoryId}")
    public ResponseEntity<categoryDto> getSingleCategory(@PathVariable("categoryId") Integer cid){
        return ResponseEntity.ok(this.cateService.getCate(cid));
    }



}
