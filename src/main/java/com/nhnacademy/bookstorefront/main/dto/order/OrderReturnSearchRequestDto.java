package com.nhnacademy.bookstorefront.main.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Setter
@Getter
public class OrderReturnSearchRequestDto {
    private String trackingNumber;
    private String status;
}
