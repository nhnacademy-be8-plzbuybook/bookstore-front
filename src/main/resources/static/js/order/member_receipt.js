document.querySelectorAll('.wishDate').forEach(radio => {
    radio.addEventListener('change', function () {
        const dateInput = document.getElementById('delivery-wish-date');
        dateInput.style.display = (this.value === 'custom') ? 'block' : 'none';
    });
});

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
        } else if (radio.value === 'default' && radio.checked) {
            receipt.value = document.getElementById("defaultReceipt").value;
            receiptPhone.value = document.getElementById("defaultReceiptPhone").value;
            zipCode.value = document.getElementById("defaultZipCode").value;
            localAddress.value = document.getElementById("defaultLocalAddress").value;
            detailAddress.value = document.getElementById("defaultDetailAddress").value;
        } else if (radio.value === 'list' && radio.checked) {
            // 배송지 목록 선택 시 모달 띄우기
            const myModal = new bootstrap.Modal(document.getElementById('addressModal'));
            myModal.show();
        }

    });
});

document.querySelectorAll('.address-row').forEach(row => {
    row.addEventListener('click', () => {
        // 데이터 속성에서 값 읽기
        const recipient = row.getAttribute('data-recipient');
        const phone = row.getAttribute('data-phone');
        const zipCode = row.getAttribute('data-zipcode');
        const localAddress = row.getAttribute('data-localaddress');
        const detailAddress = row.getAttribute('data-detailaddress');

        // 입력 필드에 값 채우기
        document.getElementById('receipt').value = recipient;
        document.getElementById('receiptPhone').value = phone;
        document.getElementById('zipCode').value = zipCode;
        document.getElementById('localAddress').value = localAddress;
        document.getElementById('detailAddress').value = detailAddress;

        // 모달 닫기
        const modal = bootstrap.Modal.getInstance(document.getElementById('addressModal'));
        modal.hide();
    });
});

const couponMap = new Map(); // 쿠폰 데이터 관리 Map

document.addEventListener("DOMContentLoaded", function () {
    const orderTable = document.getElementById("orderTable"); // 상품 테이블
    const orderAmountEl = document.getElementById("orderAmount"); // 결제금액에서 주문금액 표시 요소
    const discountEl = document.getElementById("discount"); // 결제금액에서 할인금액 표시 요소
    const shippingFeeEl = document.getElementById("shippingFee"); // 결제금액에서 배송비 표시 요소
    const totalAmountEl = document.getElementById("totalAmount"); // 결제금액에서 최종 결제금액 표시 요소

    const usedPointInput = document.getElementById("usedPoint"); // 사용할 포인트 입력 필드



    // 주문금액(상품, 포장지) 계산
    function calculateOrderAmount() {
        let orderAmount = 0; // 주문금액 초기화

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

        return orderAmount;
    }

    // 할인 금액(포인트, 쿠폰) 계산
    function calculateTotalDiscount() {
        let totalDiscount = 0;

        document.querySelectorAll("#orderTable tbody tr").forEach((row) => {
            const productId = row.dataset.productId;
            const coupon = couponMap.get(productId); // 쿠폰 Map 에서 데이터 가져옴

            if (coupon) {
                totalDiscount += coupon.discount; // 쿠폰 할인 금액 추가
                console.log("할인금액 쿠폰계산: ",totalDiscount)
                console.log("상품 ID: ",productId)
            }
        });

        const usedPoint = parseInt(document.getElementById("usedPoint").value) || 0;
        totalDiscount += usedPoint; // 포인트 사용 금액

        return totalDiscount;
    }

    // 주문 총액 계산 함수
    function calculateTotal() {
        let orderAmount = calculateOrderAmount(); // 주문금액(상품, 포장지) 계산
        let totalDiscount = calculateTotalDiscount(); // 할인 금액(포인트, 쿠폰) 계산

        const feeDeliveryThreshold = parseInt(document.getElementById("freeDeliveryThreshold").value) || 0;
        const defaultDeliveryFee = parseInt(document.getElementById("defaultDeliveryFee").value) || 0;

        // 배송비 계산 (기준 금액을 넘으면 무료 배송)
        const shippingFee = orderAmount < feeDeliveryThreshold ? defaultDeliveryFee : 0;

        // 최종 결제 금액 계산
        const totalAmount = orderAmount + shippingFee - totalDiscount

        // 화면에 표시할 값 업데이트
        updateDisplay(orderAmount, totalDiscount, shippingFee, totalAmount);
    }

    // 화면 업데이트
    function updateDisplay(orderAmount, totalDiscount, shippingFee, totalAmount) {
        document.getElementById("orderAmount").innerText = orderAmount;
        document.getElementById("discount").innerText = totalDiscount === 0 ? "0" : `-${totalDiscount}`;
        document.getElementById("shippingFee").innerText = shippingFee;
        document.getElementById("totalAmount").innerText = totalAmount;
    }

    // 포인트 관련
    usedPointInput.addEventListener("input", function () {
        const availablePoints = parseInt(this.getAttribute("data-available-points"), 10) || 0; // 현재 포인트
        const orderAmount = parseInt(document.getElementById("orderAmount").innerText, 10) || 0; // 주문 금액
        const shippingFee = parseInt(document.getElementById("shippingFee").innerText, 10) || 0; // 배송비
        const totalAmount = Math.max(0, orderAmount + shippingFee); // 총 결제 금액

        let usedPoint = parseInt(this.value, 10) || 0;

        // 사용 가능한 최대 포인트 (결제 금액과 보유 포인트 중 작은 값)
        const maxUsablePoints = Math.min(totalAmount, availablePoints);

        // 최대 사용 가능 포인트 초과 시 경고 및 제한
        if (usedPoint > maxUsablePoints) {
            alert(`사용 가능한 최대 포인트는 ${maxUsablePoints}원입니다.`);
            usedPoint = maxUsablePoints; // 입력값 제한
            this.value = maxUsablePoints; // 입력값 업데이트
        }

        // 할인 금액 업데이트
        discountEl.innerText = usedPoint; // 할인 금액 표시
        calculateTotal(); // 총 결제 금액 재계산
    });

    // 쿠폰 선택 시 쿠폰 데이터를 Map에 저장
    document.querySelectorAll('.coupon-select').forEach(button => {
        button.addEventListener('click', function () {
            const productId = this.getAttribute('data-product-id');
            const productPrice = this.getAttribute('data-product-price');
            const quantity = this.getAttribute('data-product-quantity');
            const email = this.getAttribute('data-email');
            const page = this.getAttribute('data-page') || 0;
            const pageSize = this.getAttribute('data-page-size') || 10;

            const couponId = this.getAttribute('data-coupon-id');
            const discount = this.getAttribute('data-discount') || 0;

            // 쿠폰 팝업 창 열기
            const url = `/order/receipt/coupon-popup?productId=${productId}&price=${productPrice}&quantity=${quantity}&page=${page}&pageSize=${pageSize}`;
            console.log('팝업 URL:', url);

            const popup = window.open(url, '주문상품 쿠폰적용', 'width=1000,height=600,scrollbars=yes');
            // 팝업창이 열릴 때 헤더 설정
            if (popup) {
                fetch(url, {
                    method: 'GET',
                    headers: {
                        "X-USER-ID": email
                    }
                }).then(response => {
                    if (!response.ok) throw new Error("팝업창 요청 실패");
                    return response.text();
                }).then(html => {
                    popup.document.write(html); // 팝업창 내용 업데이트
                    popup.document.close();
                }).catch(error => {
                    alert(`에러 발생: ${error.message}`);
                    console.error(error);
                });
            }

            // 총 결제 금액 다시 계산
            calculateTotal();
        });
    });

    // 쿠폰팝업창에서 받은 데이터 업데이트
    window.updateProductPrice = function (productId, discount, couponId) {
        const productRow = document.querySelector(`#orderTable tr[data-product-id="${productId}"]`);

        console.log(`updateProductPrice 호출: productId=${productId}, discount=${discount}, couponId=${couponId}`);

        couponMap.set(productId.toString(), {
            couponId: couponId,
            discount: discount,
        });

        console.log("couponMap 상태 업데이트:", Array.from(couponMap.entries()));

        // 총 결제 금액 다시 계산
        calculateTotal();
    };


    // 포장지 변경 또는 수량 변경 시 이벤트 핸들링
    orderTable.addEventListener("change", (event) => {
        if (event.target.classList.contains("wrapping-select") || event.target.classList.contains("wrapping-quantity")) {
            calculateTotal();
        }
    });

    // 페이지 로드 직후 초기 계산 실행
    calculateTotal();

    // 주문 버튼 클릭 이벤트 핸들러
    document.getElementById("orderBtn").addEventListener("click", async function () {
        const orderRequest = getOrderRequest();
        console.log(orderRequest); // 결과 확인용

        const response = await fetch("/api/orders", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(orderRequest),
        });

        if (!response.ok) {
            const errorMessage = await response.text();
            alert(`${errorMessage}`);
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

});

function getOrderRequest() {
    const receipt = document.getElementById("receipt").value;
    const receiptPhone = document.getElementById("receiptPhone").value;
    const zipCode = document.getElementById("zipCode").value;
    const localAddress = document.getElementById("localAddress").value;
    const detailAddress = document.getElementById("detailAddress").value;

    let deliveryWishDate =
        document.querySelector('input[name="delivery-wish-date"]:checked').value === ""
            ? null
            : document.getElementById("delivery-wish-date").value;

    const orderAmount = document.getElementById("orderAmount").innerText;
    const deliveryFee = document.getElementById("shippingFee").innerText;

    const usedPointValue = document.getElementById("usedPoint").value;
    const usedPoint = usedPointValue === null || usedPointValue === '' ? 0 : parseInt(usedPointValue);

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
        usedPoint: usedPoint,
        deliveryFee: deliveryFee,
        orderPrice: orderAmount
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

        // 쿠폰 데이터 가져오기
        const coupon = couponMap.get(productId.toString());

        const appliedCoupons = coupon
            ? [
                {
                    couponId: coupon.couponId,
                    discount: coupon.discount,
                },
            ]
            : [];


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


        const product = {
            productId: productId,
            price: price,
            quantity: quantity,
            appliedCoupons: appliedCoupons, // 쿠폰정보 삽입
            wrapping: wrapping,
        };

        orderProducts.push(product);
    });

    return orderProducts;
}