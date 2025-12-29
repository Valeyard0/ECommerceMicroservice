package com.example.ProductService.Service.Interfaces;

import com.example.ProductService.Payload.DTO.ProductDTO;
import com.example.ProductService.Payload.Response.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IProductService {
    String createProductByCategoryId(Long categoryId, ProductDTO productDTO);
    ProductDTO gatherProductByProductId(Long productId);
    ProductDTO updateProductById(Long productId, ProductDTO productDTO);
    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
    String deleteProduct(Long productId);
    ProductResponse gatherAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
}