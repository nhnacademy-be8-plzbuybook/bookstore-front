package com.nhnacademy.bookstorefront.main.dto.Member;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MemberAddressRequestDto {
    private Boolean defaultAddress = false;

    @NotBlank(message = "도로명 주소를 입력하세요.")
    private String locationAddress;

    @NotBlank(message = "상세 주소를 입력하세요.")
    private String detailAddress;

    @NotBlank(message = "우편번호를 입력하세요.")
    private String zipCode;

    @NotBlank(message = "주소 별칭을 입력하세요.")
    private String nickName;

    @NotBlank(message = "수령인을 입력하세요.")
    private String recipient;

    @NotBlank(message = "수령인 전화번호를 입력하세요.")
    private String recipientPhone;
}
