const orderBtn = document.getElementById("order-btn");

orderBtn.addEventListener('click', async function () {
    const data = dummyOrderData;
    const response = await fetch("/api/orders/non-member", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
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


const dummyOrderData =
    {
        deliveryWishDate: "2024-12-31",
        usedPoint: 0,
        orderProducts: [
            {
                productId: 291,
                price: 15000,
                quantity: 1,
                appliedCoupons: [
                    {
                        couponId: 0,
                        discount: 0
                    }
                ],
                wrapping: {
                    wrappingPaperId: 1,
                    quantity: 1,
                    price: 3000
                }
            },
            {
                productId: 292,
                price: 16000,
                quantity: 3,
                wrapping: {
                    wrappingPaperId: 1,
                    quantity: 1,
                    price: 3000
                }
            },
            {
                productId: 297,
                price: 22000,
                quantity: 2,
                appliedCoupons: [
                    {
                        couponId: 0,
                        discount: 0
                    }
                ],
                wrapping: {
                    wrappingPaperId: 1,
                    quantity: 2,
                    price: 3000
                }
            },
            {
                productId: 298,
                price: 15000,
                quantity: 1
            }
        ],
        orderDeliveryAddress: {
            locationAddress: "광주광역시 동구 필문대로 309(서석동, 조선대학교) 조선대5길 65",
            zipCode: "61452",
            detailAddress: "IT융합대학 별관 1층 컴퓨터공학과",
            recipient: "김태현",
            recipientPhone: "062-230-7381"
        },
        deliveryFee: 0,
        orderPrice: 56000,
        nonMemberPassword: "1234"
    }