package com.nhnacademy.bookstorefront.main.service.impl;

import com.nhnacademy.bookstorefront.main.client.DeliveryFeePolicyClient;
import com.nhnacademy.bookstorefront.main.dto.DeliveryFeePolicyDto;
import com.nhnacademy.bookstorefront.main.service.DeliveryFeePolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
