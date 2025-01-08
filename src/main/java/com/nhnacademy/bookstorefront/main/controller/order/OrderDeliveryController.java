package com.nhnacademy.bookstorefront.main.controller.order;

import com.nhnacademy.bookstorefront.main.dto.OrderDeliveryRegisterRequestDto;
import com.nhnacademy.bookstorefront.main.service.OrderDeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class OrderDeliveryController {
    private final OrderDeliveryService orderDeliveryService;

    @PostMapping("/api/orders/{order-id}/deliveries")
    public String registerOrderDelivery(@PathVariable("order-id") String orderId,
                                        @Valid @ModelAttribute OrderDeliveryRegisterRequestDto registerRequest) {
        orderDeliveryService.registerOrderDelivery(orderId, registerRequest);
        return "redirect:/admin/orders";
    }
}
