package com.example.ProductService.Repository;

import com.example.ProductService.AppConst.AppCategories;
import com.example.ProductService.Entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Boolean existsByCategoryName(AppCategories category);
    Page<Category> findByCategoryNameEquals(Pageable pageable, AppCategories appCategories);
}
