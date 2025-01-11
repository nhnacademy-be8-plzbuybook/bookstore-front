package com.nhnacademy.bookstorefront.main.service;

import com.nhnacademy.bookstorefront.main.dto.DeliveryFeePolicyCreateRequestDto;
import com.nhnacademy.bookstorefront.main.dto.DeliveryFeePolicyDto;
import com.nhnacademy.bookstorefront.main.dto.DeliveryFeePolicyModifyRequestDto;

import java.util.List;

public interface DeliveryFeePolicyService {
    DeliveryFeePolicyDto getGeneralPolicy();

    void createPolicy(DeliveryFeePolicyCreateRequestDto createRequest);

    void modifyPolicy(Long deliveryFeePolicyId, DeliveryFeePolicyModifyRequestDto modifyRequest);

    void removePolicy(Long deliveryFeePolicyId);

    List<DeliveryFeePolicyDto> getPolicies();

}

