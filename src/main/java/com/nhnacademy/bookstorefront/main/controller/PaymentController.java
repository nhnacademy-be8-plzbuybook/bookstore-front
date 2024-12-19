package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.client.PaymentClient;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PaymentController {
    @Autowired
    private PaymentClient paymentClient;

    // 결제위젯 요청
    @GetMapping("/payments/toss")
    public String toss() {
        return "payment/checkout";
    }

    // 결제하기 버튼 누를 때 호출, 결제서버에 결제 승인 요청
    @RequestMapping(value = {"/confirm/widget", "/confirm/payment"})
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) {
        //결제 서버로 승인요청
        JSONObject response = paymentClient.confirm(jsonBody);

        int statusCode = response.containsKey("error") ? 400 : 200;
        return ResponseEntity.status(statusCode).body(response);
    }
}
