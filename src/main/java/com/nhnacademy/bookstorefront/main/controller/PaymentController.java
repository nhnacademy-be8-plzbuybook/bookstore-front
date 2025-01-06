package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.dto.payment.PaymentConfirmResponseDto;
import com.nhnacademy.bookstorefront.main.dto.payment.PaymentConfirmRequestDto;
import com.nhnacademy.bookstorefront.main.service.OrderService;
import com.nhnacademy.bookstorefront.main.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RequiredArgsConstructor
@Controller
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderService orderService;

    // 결제위젯 요청
    @GetMapping("/payments/toss")
    public String toss(@RequestParam("orderId") String orderId,
                       @RequestParam("amount") BigDecimal amount,
                       @RequestParam("orderName") String orderName,
                       Model model) {


        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);
        model.addAttribute("orderName", orderName);

        return "payment/checkout";
    }


    @GetMapping("/payments/success")
    public String confirmPayment(@RequestParam("orderId") String orderId,
                                 @RequestParam("paymentKey") String paymentKey,
                                 @RequestParam("amount") BigDecimal amount,
                                 Model model) {

        //TODO: /api/payments/confirm/widget에 결제 승인 요청
        PaymentConfirmResponseDto response = paymentService.confirmPayment(new PaymentConfirmRequestDto(paymentKey, orderId, amount));

        //주문 완료
//        orderService.completeOrder(orderId);
        model.addAttribute("amount", response.getAmount());
        model.addAttribute("orderId", orderId);

//        return "redirect:/orders/" + orderId;
        return "payment/success";
    }


    @RequestMapping(value = "/payments/fail", method = RequestMethod.GET)
    public String failPayment(HttpServletRequest request, Model model) {
        model.addAttribute("code", request.getParameter("code"));
        model.addAttribute("message", request.getParameter("message"));
        return "payment/fail";
    }
}
