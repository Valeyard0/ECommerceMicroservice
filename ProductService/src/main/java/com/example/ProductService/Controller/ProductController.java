package com.example.ProductService.Controller;

import com.example.ProductService.AppConst.AppConst;
import com.example.ProductService.Payload.DTO.ProductDTO;
import com.example.ProductService.Payload.Response.ProductResponse;
import com.example.ProductService.Service.Interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @PostMapping("categories/{categoryId}/products")
    public ResponseEntity<String> createProductByCategoryId(@PathVariable Long categoryId,@RequestBody ProductDTO productDTO) {
        String message = productService.createProductByCategoryId(categoryId,productDTO);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/products")
    public ResponseEntity<ProductResponse> gatherAllProducts(
            @RequestParam(name = "pageNumber",defaultValue = AppConst.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConst.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConst.PRODUCT_SORT_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConst.SORT_ORDER,required = false)String sortOrder){
        ProductResponse productResponse = productService.gatherAllProducts(pageNumber,pageSize,sortBy,sortOrder);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> gatherProductByProductId(@PathVariable Long productId){
        ProductDTO productDTO = productService.gatherProductByProductId(productId);
        return ResponseEntity.ok(productDTO);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> updateProductById(@PathVariable Long productId,@RequestBody ProductDTO productDTO){
        ProductDTO product = productService.updateProductById(productId,productDTO);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId, @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO updatedProduct = productService.updateProductImage(productId, image);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String>  deleteProduct(@PathVariable Long productId){
        String message = productService.deleteProduct(productId);
        return ResponseEntity.ok(message);
    }
}