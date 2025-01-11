package com.nhnacademy.bookstorefront.main.controller;

import com.nhnacademy.bookstorefront.main.dto.DeliveryFeePolicyCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.DeliveryFeePolicyDto;
import com.nhnacademy.bookstorefront.main.dto.DeliveryFeePolicyModifyRequestDto;
import com.nhnacademy.bookstorefront.main.service.DeliveryFeePolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class DeliveryFeePolicyController {
    private final DeliveryFeePolicyService deliveryFeePolicyService;


    @GetMapping("/admin/delivery-fee-policies")
    public String getDeliveryFeePolicies(Model model) {
        List<DeliveryFeePolicyDto> policies = deliveryFeePolicyService.getPolicies();
        model.addAttribute("policies", policies);

        return "admin/deliveryFeePolicy/delivery_fee_policy";
    }

    @ResponseBody
    @PostMapping("/api/delivery-fee-policies")
    public ResponseEntity<Void> createDeliveryFeePolicy(@RequestBody DeliveryFeePolicyCreateRequestDto createRequest) {
        deliveryFeePolicyService.createPolicy(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ResponseBody
    @PutMapping("/api/delivery-fee-policies/{policy-id}")
    public ResponseEntity<Void> modifyDeliveryFeePolicy(@PathVariable("policy-id") Long deliveryFeePolicyId,
                                                        @RequestBody DeliveryFeePolicyModifyRequestDto modifyRequest) {
        deliveryFeePolicyService.modifyPolicy(deliveryFeePolicyId, modifyRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @DeleteMapping("/api/delivery-fee-policies/{policy-id}")
    public ResponseEntity<Void> removeDeliveryFeePolicy(@PathVariable("policy-id") Long deliveryFeePolicyId) {
        deliveryFeePolicyService.removePolicy(deliveryFeePolicyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
