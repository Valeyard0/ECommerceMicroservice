package com.example.ProductService.Controller;

import com.example.ProductService.AppConst.AppCategories;
import com.example.ProductService.AppConst.AppConst;
import com.example.ProductService.Entity.Category;
import com.example.ProductService.Payload.DTO.CategoryDTO;
import com.example.ProductService.Payload.Response.CategoryResponse;
import com.example.ProductService.Service.Interfaces.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class CategoryController {
    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService){
        this.categoryService=categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity<String> createCategory(@RequestBody CategoryDTO category){
        String message = categoryService.createCategory(category);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/categories/categoryId/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        String message = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoryResponse> gatherAllCategories(
            @RequestParam(name = "pageNumber",defaultValue = AppConst.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConst.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConst.SORT_BY,required = false) String sortBy,
            @RequestParam(name = "sizeOrder",defaultValue = AppConst.SORT_ORDER,required = false) String sortOrder) {
        CategoryResponse categoryResponse = categoryService.gatherAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return ResponseEntity.ok(categoryResponse);
    }

    @GetMapping("/categories/categoryId/{categoryId}")
    public ResponseEntity<CategoryDTO> gatherCategoryById(@PathVariable Long categoryId){
        CategoryDTO categoryDTO = categoryService.gatherCategoryById(categoryId);
        return ResponseEntity.ok(categoryDTO);
    }

    @PutMapping("/categories/categoryId/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId,@RequestBody CategoryDTO categoryDTO){
        CategoryDTO category = categoryService.updateCategory(categoryId,categoryDTO);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/categories/categoryName/{categoryName}")
    public ResponseEntity<CategoryResponse> gatherCategoriesByCategoryName(
            @RequestParam(name = "pageNumber",defaultValue = AppConst.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConst.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConst.SORT_BY,required = false) String sortBy,
            @RequestParam(name = "sizeOrder",defaultValue = AppConst.SORT_ORDER,required = false) String sortOrder,
            @PathVariable AppCategories categoryName){
        CategoryResponse categoryResponse = categoryService.gatherCategoriesByCategoryName(pageNumber,pageSize, sortBy, sortOrder, categoryName);
        return new ResponseEntity<>(categoryResponse, HttpStatus.FOUND);
    }
}
