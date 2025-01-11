package com.nhnacademy.bookstorefront.main.client;

import com.nhnacademy.bookstorefront.main.dto.DeliveryFeePolicyCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.DeliveryFeePolicyDto;
import com.nhnacademy.bookstorefront.main.dto.DeliveryFeePolicyModifyRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "GATEWAY", contextId = "deliveryFeePolicyClient")
public interface DeliveryFeePolicyClient {
    @GetMapping("/api/delivery-fee-policies/general")
    ResponseEntity<DeliveryFeePolicyDto> getGeneralPolicy();

    @GetMapping("/api/delivery-fee-policies")
    ResponseEntity<List<DeliveryFeePolicyDto>> getPolicies();

    @GetMapping("/api/delivery-fee-policies/{policy-id}")
    ResponseEntity<List<DeliveryFeePolicyDto>> getPolicy(@PathVariable("policy-id") Long policyId);

    @PostMapping("/api/delivery-fee-policies")
    void createPolicy(@RequestBody DeliveryFeePolicyCreateRequestDto createRequest);

    @PutMapping("/api/delivery-fee-policies/{policy-id}")
    void modifyPolicy(@PathVariable("policy-id") Long policyId,
                      @RequestBody DeliveryFeePolicyModifyRequestDto modifyRequest);

    @DeleteMapping("/api/delivery-fee-policies/{policy-id}")
    void removePolicy(@PathVariable("policy-id") Long policyId);

}
