package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.DeliveryFeePolicyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "BOOKSTORE", contextId = "deliveryFeePolicyClient")
public interface DeliveryFeePolicyClient {
    @GetMapping("/api/delivery-fee-policies/general")
    ResponseEntity<DeliveryFeePolicyDto> getGeneralPolicy();
}
