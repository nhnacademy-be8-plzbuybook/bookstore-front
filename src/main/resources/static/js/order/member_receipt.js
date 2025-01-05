
// // const dummyOrderData =
// //     {
// //         deliveryWishDate: "2024-12-31",
// //         usedPoint: 0,
// //         orderProducts: [
// //             {
// //                 productId: 250,
// //                 price: 15000,
// //                 quantity: 1,
// //                 wrapping: {
// //                     wrappingPaperId: 1,
// //                     quantity: 1,
// //                     price: 3000
// //                 }
// //             }
// //         ],
// //         orderDeliveryAddress: {
// //             locationAddress: "광주광역시 동구 필문대로 309(서석동, 조선대학교) 조선대5길 65",
// //             zipCode: "61452",
// //             detailAddress: "IT융합대학 별관 1층 컴퓨터공학과",
// //             recipient: "김태현",
// //             recipientPhone: "062-230-7381"
// //         },
// //         deliveryFee: 3000,
// //         orderPrice: 18000
// //     }
//
//


let receipt = document.getElementById("receipt");
let receiptPhone = document.getElementById("receiptPhone");
let zipCode = document.getElementById("zipCode");
let localAddress = document.getElementById("localAddress");
let detailAddress = document.getElementById("detailAddress");

const deliveryAddressRadios = document.querySelectorAll("input[name='deliveryAddress']");
deliveryAddressRadios.forEach(radio => {
    radio.addEventListener('change', () => {
        if (radio.value === "new" && radio.checked) {
            // 새 배송지 선택 시 기존 배송지 정보 지우기
            receipt.value = "";          // 받는 사람
            receiptPhone.value = "";     // 전화번호
            zipCode.value = "";          // 우편번호
            localAddress.value = "";     // 주소
            detailAddress.value = "";    // 상세주소
        }
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const orderTable = document.getElementById("orderTable"); // 상품 테이블
    const orderAmountEl = document.getElementById("orderAmount"); // 주문금액 표시 요소
    const shippingFeeEl = document.getElementById("shippingFee"); // 배송비 표시 요소
    const totalAmountEl = document.getElementById("totalAmount"); // 총결제금액 표시 요소

    // 주문 총액 계산 함수
    function calculateTotal() {
        let orderAmount = 0;

        // 각 상품의 금액 계산
        orderTable.querySelectorAll("tbody tr").forEach((row) => {
            const quantity = parseInt(row.querySelector(".book-quantity").innerText) || 0;
            const price = parseInt(row.querySelector(".book-price").innerText) || 0;

            // 포장지 추가 금액
            const wrappingSelect = row.querySelector(".wrapping-select");
            const wrappingPrice = wrappingSelect
                ? parseInt(wrappingSelect.options[wrappingSelect.selectedIndex]?.getAttribute("data-price")) || 0
                : 0;

            // 포장지 수량
            const wrappingQuantityInput = row.querySelector(".wrapping-quantity");
            const wrappingQuantity = wrappingQuantityInput ? parseInt(wrappingQuantityInput.value) || 0 : 0;

            // 상품 총액 = (상품 가격 * 수량) + (포장지 가격 * 포장지 수량)
            orderAmount += (price * quantity) + (wrappingPrice * wrappingQuantity);
        });

        const feeDeliveryThreshold = parseInt(document.getElementById("freeDeliveryThreshold").value) || 0;
        const defaultDeliveryFee = parseInt(document.getElementById("defaultDeliveryFee").value) || 0;

        // 배송비 계산 (기준 금액을 넘으면 무료 배송)
        const shippingFee = orderAmount < feeDeliveryThreshold ? defaultDeliveryFee : 0;

        // 결제 금액 계산
        const totalAmount = orderAmount + shippingFee;

        // 화면에 업데이트
        orderAmountEl.innerText = orderAmount;
        shippingFeeEl.innerText = shippingFee;
        totalAmountEl.innerText = totalAmount;
    }

    // 포장지 변경 또는 수량 변경 시 이벤트 핸들링
    orderTable.addEventListener("change", (event) => {
        if (event.target.classList.contains("wrapping-select") || event.target.classList.contains("wrapping-quantity")) {
            calculateTotal();
        }
    });

    // 초기 계산 실행
    calculateTotal();
});