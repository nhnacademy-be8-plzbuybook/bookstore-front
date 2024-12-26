package com.nhnacademy.bookstorefront.main.controller;

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

    // 결제정보 임시 저장
//    @PostMapping("/save-payment")
//    public ResponseEntity<?> saveAmount(@RequestBody SaveAmountDto saveAmount) {
//        paymentService.savePaymentAmountTemporary(saveAmount);
//        return ResponseEntity.ok().build();
//    }


    // 결제 승인 요청
    @GetMapping("/payments/success")
    public String confirmPayment(@RequestBody PaymentConfirmRequestDto confirmRequest, Model model) {
        //TODO: /api/payments/confirm/widget에 결제 승인 요청
        paymentService.confirmPayment(confirmRequest);

        BigDecimal amount = BigDecimal.ONE;
        model.addAttribute("amount", amount);

        return "payment/success";
    }


    @RequestMapping(value = "/payments/fail", method = RequestMethod.GET)
    public String failPayment(HttpServletRequest request, Model model) {
        model.addAttribute("code", request.getParameter("code"));
        model.addAttribute("message", request.getParameter("message"));
        return "payment/fail";
    }
}
