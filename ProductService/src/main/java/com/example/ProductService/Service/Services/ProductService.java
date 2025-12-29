package com.example.ProductService.Service.Services;

import com.example.ProductService.Entity.Category;
import com.example.ProductService.Entity.Product;
import com.example.ProductService.Event.Publisher.ProductEventPublisher;
import com.example.ProductService.GlobalExceptionHandler.Exceptions.ResourceNotFound;
import com.example.ProductService.Payload.DTO.ProductDTO;
import com.example.ProductService.Payload.Response.ProductResponse;
import com.example.ProductService.Repository.CategoryRepository;
import com.example.ProductService.Repository.ProductRepository;
import com.example.ProductService.Service.Interfaces.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;

@Service
public class ProductService implements IProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;
    private final ProductEventPublisher productEventPublisher;

    public ProductService(CategoryRepository categoryRepository, ProductRepository productRepository,
                          ModelMapper modelMapper,FileService fileService,
                          ProductEventPublisher productEventPublisher) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
        this.productEventPublisher = productEventPublisher;
    }

    @Value("${project.image}")
    private String path;

    @Override
    public ProductResponse gatherAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> productPage = productRepository.findAll(pageable);

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productPage.getContent().stream().map(product -> modelMapper.map(product,ProductDTO.class)).toList());
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

    @Override
    public String createProductByCategoryId(Long categoryId, ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO,Product.class);
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFound("Category not found categoryId:"+categoryId));
        product.setCategory(category);
        productRepository.save(product);
        return "Product successfully created.";
    }

    @Override
    public ProductDTO updateProductById(Long productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product not found productId:" + productId));

        BigDecimal oldPrice = existingProduct.getProductPrice();

        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setImage(productDTO.getImage());
        existingProduct.setProductPrice(productDTO.getProductPrice());
        existingProduct.setQuantity(productDTO.getQuantity());

        if (oldPrice.compareTo(existingProduct.getProductPrice()) != 0)
            productEventPublisher.publishProductPriceUpdatedEvent(productId, existingProduct.getProductPrice());

        productRepository.save(existingProduct);

        return modelMapper.map(existingProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO gatherProductByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFound("Product not found productId:"+productId));
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("Product not found:"+productId));

        String fileName = fileService.uploadImage(path, image);
        productFromDb.setImage(fileName);

        Product updatedProduct = productRepository.save(productFromDb);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public String deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFound("Product not found productId:"+productId));
        productRepository.delete(product);
        return "Product successfully deleted";
    }
}