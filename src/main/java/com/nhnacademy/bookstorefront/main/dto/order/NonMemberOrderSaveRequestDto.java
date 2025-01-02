//package com.nhnacademy.bookstorefront.main.dto.order;
//
//import jakarta.annotation.Nullable;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//
//@Getter
//public class NonMemberOrderSaveRequestDto {
//    @NotBlank
//    private BigDecimal totalPrice;
//
//    @Nullable
//    private LocalDate deliveryWishDate;
//
//    @NotNull
//    private Integer usedPoint;
//
//    @NotNull
//    private List<OrderProductSaveRequestDto> orderProducts;
//
//    @NotNull
//    private OrderDeliveryAddress orderDeliveryAddress;
//
//    @NotBlank
//    private String nonMemberOrderPassword;
//
//    @Builder
//    public NonMemberOrderSaveRequestDto(BigDecimal totalPrice, @Nullable LocalDate deliveryWishDate, Integer usedPoint,
//                                        List<OrderProductSaveRequestDto> orderProducts, OrderDeliveryAddress orderDeliveryAddress,
//                                        String nonMemberOrderPassword) {
//        this.totalPrice = totalPrice;
//        this.deliveryWishDate = deliveryWishDate;
//        this.usedPoint = usedPoint;
//        this.orderProducts = orderProducts;
//        this.orderDeliveryAddress = orderDeliveryAddress;
//        this.nonMemberOrderPassword = nonMemberOrderPassword;
//    }
//}
//
//@Getter
//class OrderDeliveryAddress {
//    @NotBlank
//    private String locationAddress;
//
//    @NotBlank
//    private String zipCode;
//
//    @NotBlank
//    private String detailAddress;
//
//    @NotBlank
//    private String recipient;
//
//    @NotBlank
//    private String recipientPhone;
//}
//
//
//@Getter
//class OrderProductSaveRequestDto {
//    @NotNull
//    private Long sellingBookId;
//
//    @Min(1)
//    @NotNull
//    private Integer quantity;
//
//    @Nullable
//    private List<Long> appliedCouponIds;
//
//    @Nullable
//    private BigDecimal couponDiscount;
//
//    @Nullable
//    @Valid
//    private OrderProductWrappingDto wrapping;
//}
//
//@Getter
//public class OrderProductWrappingDto {
//    //wrapping이 null일 수도 있지만 null이 아니면 내부 값들은 조건을 만족해야함
//    @NotNull
//    private Long wrappingPaperId;
//
//    @Min(1)
//    @NotNull
//    private Integer quantity;
//}
//
