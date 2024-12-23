const orderBtn = document.getElementById("order-btn");

orderBtn.addEventListener('click', async function () {
    const data = dummyOrderData;
    const response = await fetch("http://localhost:8090/api/orders", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data),
    });

    if (response.ok) {
        alert("주문 정보를 저장하는데 실패했습니다. 다시 시도해 주세요.");
        return;
    }
    const paymentInfo = response.body;
    const queryString = new URLSearchParams(paymentInfo).toString();
    window.location.href = `/payments/toss?${queryString}`;
});


const dummyOrderData = {
    deliveryWishDate: "2024-12-22",
    usedPoint: 0,
    orderProducts: [
        {
            sellingBookId: 1,
            quantity: 1,
            appliedCouponIds: [0],
            couponDiscount: 0, // 이거 지워도 되나?
            wrapping: {
                wrappingPaperId: 1,
                quantity: 1
            }
        },
        {
            sellingBookId: 2,
            quantity: 3,
            appliedCouponIds: [0],
            couponDiscount: 0
        }
    ],
    orderDeliveryAddress: {
        locationAddress: "광주광역시 동구 필문대로 309(서석동, 조선대학교) 조선대5길 65",
        zipCode: "61452",
        detailAddress: "IT융합대학 별관 1층 컴퓨터공학과",
        recipient: "김태현",
        recipientPhone: "062-230-7381"
    }
}