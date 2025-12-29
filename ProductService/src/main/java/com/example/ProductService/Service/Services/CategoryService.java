package com.example.ProductService.Service.Services;

import com.example.ProductService.AppConst.AppCategories;
import com.example.ProductService.Entity.Category;
import com.example.ProductService.GlobalExceptionHandler.Exceptions.AlreadyExistException;
import com.example.ProductService.GlobalExceptionHandler.Exceptions.ResourceNotFound;
import com.example.ProductService.Payload.DTO.CategoryDTO;
import com.example.ProductService.Payload.Response.CategoryResponse;
import com.example.ProductService.Repository.CategoryRepository;
import com.example.ProductService.Service.Interfaces.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository,ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDTO gatherCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFound("Category not found:"+categoryId));
        return modelMapper.map(category,CategoryDTO.class);
    }

    @Override
    public CategoryResponse gatherAllCategories(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Category> pageRequest = categoryRepository.findAll(pageable);

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(pageRequest.getContent().stream().map(category -> modelMapper.map(category,CategoryDTO.class)).toList());
        categoryResponse.setPageNumber(pageRequest.getNumber());
        categoryResponse.setPageSize(pageRequest.getSize());
        categoryResponse.setTotalElements(pageRequest.getTotalElements());
        categoryResponse.setTotalPages(pageRequest.getTotalPages());
        categoryResponse.setLastPage(pageRequest.isLast());

        return categoryResponse;
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFound("This categoryId does not exists in the system"));
        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        categoryRepository.save(existingCategory);
        return modelMapper.map(existingCategory,CategoryDTO.class);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new RuntimeException("This categoryId does not exists in the system"));
        categoryRepository.delete(category);
        return "Category successfully deleted";
    }

    @Override
    public String createCategory(CategoryDTO category) {
        Category categoryEntity = modelMapper.map(category,Category.class);
        Boolean isCategoryExists = categoryRepository.existsByCategoryName(categoryEntity.getCategoryName());

        if(isCategoryExists)
            throw new AlreadyExistException("This category already exists in the system");

        categoryRepository.save(categoryEntity);
        return "Category successfully added";
    }

    @Override
    public CategoryResponse gatherCategoriesByCategoryName(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder,AppCategories categoryName) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Category> pageRequest = categoryRepository.findByCategoryNameEquals(pageable,categoryName);

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(pageRequest.getContent().stream().map(category -> modelMapper.map(category,CategoryDTO.class)).toList());
        categoryResponse.setPageNumber(pageRequest.getNumber());
        categoryResponse.setPageSize(pageRequest.getSize());
        categoryResponse.setTotalElements(pageRequest.getTotalElements());
        categoryResponse.setTotalPages(pageRequest.getTotalPages());
        categoryResponse.setLastPage(pageRequest.isLast());

        return categoryResponse;
    }
}
