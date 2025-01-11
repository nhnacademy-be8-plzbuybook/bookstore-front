package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.main.client.DeliveryFeePolicyClient;
import com.nhnacademy.bookstorefront.main.dto.DeliveryFeePolicyCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.DeliveryFeePolicyDto;
import com.nhnacademy.bookstorefront.main.dto.DeliveryFeePolicyModifyRequestDto;
import com.nhnacademy.bookstorefront.main.service.DeliveryFeePolicyService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeliveryFeePolicyServiceImpl implements DeliveryFeePolicyService {
    private final DeliveryFeePolicyClient deliveryFeePolicyClient;

    @Override
    public DeliveryFeePolicyDto getGeneralPolicy() {
        ResponseEntity<DeliveryFeePolicyDto> response = deliveryFeePolicyClient.getGeneralPolicy();
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("배송비정책 정보를 가져오는데 실패했습니다.");
        }
        return response.getBody();
    }

    @Override
    public List<DeliveryFeePolicyDto> getPolicies() {
        try {
            ResponseEntity<List<DeliveryFeePolicyDto>> response = deliveryFeePolicyClient.getPolicies();
            return response.getBody();
        } catch (FeignException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void createPolicy(DeliveryFeePolicyCreateRequestDto createRequest) {
        try {
            deliveryFeePolicyClient.createPolicy(createRequest);
        } catch (FeignException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void modifyPolicy(Long policyId, DeliveryFeePolicyModifyRequestDto modifyRequest) {
        try {
            deliveryFeePolicyClient.modifyPolicy(policyId, modifyRequest);
        } catch (FeignException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void removePolicy(Long deliveryFeePolicyId) {
        try {
            deliveryFeePolicyClient.removePolicy(deliveryFeePolicyId);
        } catch (FeignException e) {
            throw new RuntimeException(e.getMessage());
        }

    }


}
