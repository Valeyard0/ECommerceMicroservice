package com.example.OrderService.Controller;

import com.example.OrderService.Payload.OrderPayload.OrderDTO;
import com.example.OrderService.Service.Interfaces.IOrderService;
import com.example.OrderService.Util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    private final AuthUtil authUtil;
    @PostMapping("/order")
    public ResponseEntity<OrderDTO> createOrder(){
        String userId = authUtil.findUserIdByLoggedUser();
        OrderDTO orderDTO = orderService.createOrder(userId);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }
}
