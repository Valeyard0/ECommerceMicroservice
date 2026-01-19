package com.example.OrderService.Service.Services;

import com.example.OrderService.Entity.Cart;
import com.example.OrderService.Entity.CartItem;
import com.example.OrderService.Entity.Order;
import com.example.OrderService.Entity.OrderItem;
import com.example.OrderService.Event.Publisher.MessageEventPublisher;
import com.example.OrderService.Event.Publisher.OrderEventPublisher;
import com.example.OrderService.GlobalExceptionHandler.Exceptions.ResourceNotFoundException;
import com.example.OrderService.Payload.OrderPayload.OrderDTO;
import com.example.OrderService.Payload.OrderPayload.OrderItemDetail;
import com.example.OrderService.Repository.CartItemRepository;
import com.example.OrderService.Repository.CartRepository;
import com.example.OrderService.Repository.OrderRepository;
import com.example.OrderService.Service.Interfaces.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;
    private final OrderEventPublisher orderEventPublisher;
    private final MessageEventPublisher messageEventPublisher;

    @Override
    @Transactional
    public OrderDTO createOrder(String userId) {
        Cart cart = cartRepository.findCartByUserId(userId);
        List<CartItem> cartItems = cartItemRepository.findCartItemsByCart(cart);

        if (cart == null)
            throw new ResourceNotFoundException("Couldn't find the cart: " + userId);

        List<OrderItemDetail> itemsToDecrease = cart.getCartItems().stream()
                .map(item -> new OrderItemDetail(item.getProductId(), item.getQuantity()))
                .collect(Collectors.toList());

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(cart.getTotalAmount());

        for(CartItem cartItem : cartItems){
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setProductId(cartItem.getProductId());
            order.addOrderItem(orderItem);
        }
        Order savedOrder = orderRepository.save(order);
        cartRepository.delete(cart);
        orderEventPublisher.publishOrderCreatedEvent(savedOrder.getOrderId(), savedOrder.getUserId(), itemsToDecrease);
        messageEventPublisher.publishMessageCreatedEvent(savedOrder.getOrderId(),savedOrder.getUserId(),savedOrder.getTotalAmount());
        return modelMapper.map(savedOrder, OrderDTO.class);
    }


}