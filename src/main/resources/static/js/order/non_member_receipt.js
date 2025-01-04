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

        const feeDeliveryThreshold = document.getElementById("feeDeliveryThreshold").value;
        const defaultDeliveryFee = document.getElementById("defaultDeliveryFee").value;
        const shippingFee = orderAmount < feeDeliveryThreshold ? defaultDeliveryFee : 0;
        // 결제 금액 계산
        const totalAmount = orderAmount + shippingFee;

        // 화면에 업데이트
        orderAmountEl.innerText = orderAmount.toLocaleString();
        shippingFeeEl.innerText = shippingFee.toLocaleString();
        totalAmountEl.innerText = totalAmount.toLocaleString();
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