document.querySelectorAll('.wishDate').forEach(radio => {
    radio.addEventListener('change', function () {
        const dateInput = document.getElementById('delivery-wish-date');
        dateInput.style.display = (this.value === 'custom') ? 'block' : 'none';
    });
});


document.addEventListener("DOMContentLoaded", function () {
    const orderTable = document.getElementById("orderTable"); // 상품 테이블
    const orderAmountEl = document.getElementById("orderAmount"); // 주문금액 표시 요소
    const shippingFeeEl = document.getElementById("shippingFee"); // 배송비 표시 요소
    const totalAmountEl = document.getElementById("totalAmount"); // 총결제금액 표시 요소

    const deliveryWishDateRadios = document.querySelectorAll('.wishDate');
    const deliveryWishDateInput = document.getElementById('delivery-wish-date');

    // 배송 희망 날짜 지정 관련
    deliveryWishDateRadios.forEach(radio => {
        radio.addEventListener('change', function () {
            if (this.value === 'custom') {
                deliveryWishDateInput.style.display = 'block';
                deliveryWishDateInput.disabled = false;

                // 날짜 설정 (오늘 이후, 최대 7일까지)
                const today = new Date();
                const maxDate = new Date();
                today.setDate(today.getDate() + 1);
                maxDate.setDate(maxDate.getDate() + 7);
                deliveryWishDateInput.min = today.toISOString().split('T')[0];
                deliveryWishDateInput.max = maxDate.toISOString().split('T')[0];
            } else {
                deliveryWishDateInput.style.display = 'none';
                deliveryWishDateInput.disabled = true;
                deliveryWishDateInput.value = '';
            }
        });
    });

    // 페이지 로드 시 기본 상태 설정
    const checkedRadio = document.querySelector('input[name="delivery-wish-date"]:checked');
    if (checkedRadio && checkedRadio.value === 'custom') {
        deliveryWishDateInput.style.display = 'block';
        deliveryWishDateInput.disabled = false;
    }

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


function getOrderRequest() {
    const receipt = document.getElementById("receipt").value;
    const receiptPhone = document.getElementById("receiptPhone").value;
    const zipCode = document.getElementById("zipCode").value;
    const localAddress = document.getElementById("localAddress").value;
    const detailAddress = document.getElementById("detailAddress").value;

    // 배송 희망 날짜 처리
    const selectedDeliveryOption = document.querySelector('input[name="delivery-wish-date"]:checked');
    let deliveryWishDate = null;

    if (selectedDeliveryOption && selectedDeliveryOption.value === 'custom') {
        const deliveryWishDateInput = document.getElementById('delivery-wish-date').value;

        // 날짜가 없는 경우 null로 설정
        deliveryWishDate = deliveryWishDateInput ? deliveryWishDateInput : null;
    }

    const orderAmount = document.getElementById("orderAmount").innerText;
    const deliveryFee = document.getElementById("shippingFee").innerText;

    const nonMemberPassword = document.getElementById("nonMemberPassword").value;

    const orderProducts = getOrderProducts();

    return {
        deliveryWishDate: deliveryWishDate,
        orderProducts: orderProducts,
        orderDeliveryAddress: {
            locationAddress: localAddress,
            zipCode: zipCode,
            detailAddress: detailAddress,
            recipient: receipt,
            recipientPhone: receiptPhone
        },
        usedPoint: 0,
        deliveryFee: deliveryFee,
        orderPrice: orderAmount,
        nonMemberPassword: nonMemberPassword
    }
}

function getOrderProducts() {
    const orderProducts = [];

    // 테이블의 모든 행 순회
    document.querySelectorAll("#orderTable tbody tr").forEach((row) => {
        // 상품 ID, 가격, 수량 가져오기
        const productId = parseInt(row.dataset.productId) || null;
        const price = parseInt(row.querySelector(".book-price").innerText) || 0;
        const quantity = parseInt(row.querySelector(".book-quantity").innerText) || 0;

        // 포장지 정보 가져오기
        const wrappingSelect = row.querySelector(".wrapping-select");
        const wrappingPaperId = wrappingSelect ? parseInt(wrappingSelect.value) || null : null;
        const wrappingPrice = wrappingSelect
            ? parseInt(wrappingSelect.options[wrappingSelect.selectedIndex]?.getAttribute("data-price")) || 0
            : 0;

        const wrappingQuantityInput = row.querySelector(".wrapping-quantity");
        const wrappingQuantity = wrappingQuantityInput ? parseInt(wrappingQuantityInput.value) || 0 : 0;

        // wrapping을 조건부로 설정
        const wrapping =
            wrappingPaperId !== null && wrappingQuantity > 0 && wrappingPrice > 0
                ? {
                    wrappingPaperId: wrappingPaperId,
                    quantity: wrappingQuantity,
                    price: wrappingPrice,
                }
                : null;

        // 상품 객체 구성
        const product = {
            productId: productId,
            price: price,
            quantity: quantity,
            appliedCoupons: null,
            wrapping: wrapping,
        };

        orderProducts.push(product);
    });

    return orderProducts;
}

// 주문 버튼 클릭 이벤트 핸들러
document.getElementById("orderBtn").addEventListener("click", async function () {
    const selectedDeliveryOption = document.querySelector('input[name="delivery-wish-date"]:checked');
    const deliveryWishDateInput = document.getElementById("delivery-wish-date");

    // 배송 희망 날짜 검증
    if (selectedDeliveryOption && selectedDeliveryOption.value === "custom" && !deliveryWishDateInput.value) {
        alert("배송 희망 날짜를 지정해야 합니다.");
        event.preventDefault(); // 요청 중단
        return;
    }

    const orderRequest = getOrderRequest();
    console.log(orderRequest); // 결과 확인용

    const response = await fetch("/api/orders/non-member", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(orderRequest),
    });

    if (!response.ok) {
        alert("주문 정보를 저장하는데 실패했습니다. 다시 시도해 주세요.");
        return;
    }

    // 응답 본문을 문자열로 읽기
    const responseData = await response.json();

    // JSON 데이터를 기반으로 쿼리 문자열 생성
    const queryString = new URLSearchParams({
        orderId: responseData.orderId,
        amount: responseData.amount,
        orderName: responseData.orderName
    });
    window.location.replace(`/payments/toss?${queryString}`);
});