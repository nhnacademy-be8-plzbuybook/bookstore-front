package com.nhnacademy.bookstorefront.main.client;

import org.json.simple.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//임시로 게이트웨이 안거치고 바로 결제 서버로 이동하도록 설정
@FeignClient(name = "payment-dev", url = "http://localhost:8090")
public interface PaymentClient {
    @PostMapping("/api/payment/confirm")
    JSONObject confirm(@RequestBody String jsonBody);

}
