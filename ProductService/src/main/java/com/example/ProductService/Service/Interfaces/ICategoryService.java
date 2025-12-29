package com.example.ProductService.Service.Interfaces;

import com.example.ProductService.AppConst.AppCategories;
import com.example.ProductService.Entity.Category;
import com.example.ProductService.Payload.DTO.CategoryDTO;
import com.example.ProductService.Payload.Response.CategoryResponse;

public interface ICategoryService {
    String createCategory(CategoryDTO category);
    String deleteCategory(Long categoryId);
    CategoryResponse gatherAllCategories(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);
    CategoryResponse gatherCategoriesByCategoryName(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder,AppCategories categoryName);
    CategoryDTO gatherCategoryById(Long categoryId);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);
}
