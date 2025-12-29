package com.example.ProductService.Payload.DTO;

import com.example.ProductService.AppConst.AppCategories;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    private AppCategories categoryName;
}
