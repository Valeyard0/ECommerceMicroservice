package com.example.OrderService.Service.Services;

import com.example.OrderService.Client.ProductClient.ProductServiceClient;
import com.example.OrderService.Entity.Cart;
import com.example.OrderService.Entity.CartItem;
import com.example.OrderService.GlobalExceptionHandler.Exceptions.APIException;
import com.example.OrderService.GlobalExceptionHandler.Exceptions.ResourceNotFoundException;
import com.example.OrderService.Payload.CartPayload.CartRequest;
import com.example.OrderService.Payload.CartPayload.DTO.CartDTO;
import com.example.OrderService.Payload.ProductPayload.ProductDTO;
import com.example.OrderService.Repository.CartItemRepository;
import com.example.OrderService.Repository.CartRepository;
import com.example.OrderService.Service.Interfaces.ICartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;

    public CartService(CartRepository cartRepository,ProductServiceClient productServiceClient,
                       CartItemRepository cartItemRepository,ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.productServiceClient = productServiceClient;
        this.cartItemRepository = cartItemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String createCart(String authToken,String userId, CartRequest cartRequest) {
        Cart cart = getCart(userId);
        ProductDTO productDTO = productServiceClient
                .gatherProductByProductId(authToken,cartRequest.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Product does not found:"+cartRequest.getProductId()));
        Optional<CartItem> cartItemOptional = cartItemRepository.findCartItemByUserIdAndProductId(userId, cartRequest.getProductId().toString());

        if (cartItemOptional.isPresent())
            throw new APIException("This product is already in your cart: " + cartRequest.getProductId());

        if(cartRequest.getQuantity()==0)
            throw new APIException("Quantity cannot be zero");

        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        cartItem.setProductId(productDTO.getProductId().toString());
        cartItem.setQuantity(cartRequest.getQuantity());
        cartItem.setPrice(productDTO.getProductPrice().multiply(new BigDecimal(cartRequest.getQuantity())));
        cart.setTotalAmount(cart.getTotalAmount()
                .add(cartItem.getPrice()));
        cart.addCartItem(cartItem);
        cartRepository.save(cart);

        return "Product added to cart successfully";
    }

    @Override
    public List<CartDTO> gatherAllCarts() {
        List<Cart> cartList = cartRepository.findAll();
        return cartList.stream().map(cart -> modelMapper.map(cart, CartDTO.class)).toList();
    }

    @Override
    public CartDTO gatherCart(String userId) {
        Cart cart = cartRepository.findCartByUserId(userId);
        return modelMapper.map(cart, CartDTO.class);
    }

    @Override
    public CartDTO removeProductFromCart(String authToken,String userId, CartRequest cartRequest) {
        ProductDTO productDTO = productServiceClient
                .gatherProductByProductId(authToken,cartRequest.getProductId())
                .orElseThrow(()->new ResourceNotFoundException("Product not found:" + cartRequest.getProductId()));
        Cart cart = cartRepository.findCartByUserId(userId);
        Optional<CartItem> cartItem = cartItemRepository
                .findCartItemByUserIdAndProductId(userId,cartRequest.getProductId().toString());

        if(cartItem.isEmpty())
            throw new ResourceNotFoundException("This product does not include in your cart");

        cart.setTotalAmount(cart.getTotalAmount().subtract(cartItem.get().getPrice()));
        cart.deleteCartItem(cartItem.get());

        return modelMapper.map(cart, CartDTO.class);
    }

    public CartDTO updateProductQuantityFromCart(String authToken,String userId, CartRequest cartRequest) {
        ProductDTO productDTO = productServiceClient.gatherProductByProductId(authToken,cartRequest.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Product not found: " + cartRequest.getProductId()));

        Long newQuantity = cartRequest.getQuantity();
        if (newQuantity < 0)
            throw new APIException("Quantity cannot be negative");
        else if (productDTO.getQuantity() < newQuantity)
            throw new APIException("Not enough stock. Available: " + productDTO.getQuantity());

        Cart cart = cartRepository.findCartByUserId(userId);
        CartItem cartItem = cartItemRepository.findCartItemByUserIdAndProductId(userId, cartRequest.getProductId().toString())
                .orElseThrow(() -> new APIException("Product not included in your cart: " + cartRequest.getProductId()));

        BigDecimal productPrice = productDTO.getProductPrice();
        BigDecimal oldItemTotal = cartItem.getPrice();
        BigDecimal newItemTotal = productPrice.multiply(new BigDecimal(newQuantity));

        if (newQuantity == 0) {
            cart.setTotalAmount(cart.getTotalAmount().subtract(oldItemTotal));
            cartItemRepository.delete(cartItem);
        }
        else {
            BigDecimal totalAmountChange = newItemTotal.subtract(oldItemTotal);
            cart.setTotalAmount(cart.getTotalAmount().add(totalAmountChange));
            cartItem.setQuantity(newQuantity);
            cartItem.setPrice(newItemTotal);
            cartRepository.save(cart);
        }
        return modelMapper.map(cart,CartDTO.class);
    }

    private Cart getCart(String userId){
        Cart cart = cartRepository.findCartByUserId(userId);
        if(cart == null){
            cart = new Cart();
            cart.setTotalAmount(new BigDecimal(0));
            cart.setUserId(userId);
        }
        return cart;
    }
}