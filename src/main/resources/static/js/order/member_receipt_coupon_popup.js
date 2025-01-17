document.addEventListener("DOMContentLoaded", function () {
    const applyCouponButtons = document.querySelectorAll(".apply-coupon-btn");

    if (applyCouponButtons.length > 0) {
        applyCouponButtons.forEach(button => {
            button.addEventListener("click", function () {
                const productId = this.getAttribute("data-product-id");
                const couponId = this.getAttribute("data-coupon-id");
                const price = parseFloat(this.getAttribute("data-price"));
                const quantity = parseInt(this.getAttribute("data-quantity"));
                const email = this.getAttribute("data-email");

                console.log("팝업창에서 쿠폰 적용:");
                console.log("productId:", productId);
                console.log("couponId:", couponId);
                console.log("price:", price);
                console.log("quantity:", quantity);
                console.log("email:", email);

                const productPrice = price * quantity;
                const requestBody = {
                    price: productPrice,
                };

                // API 요청
                fetch(`/order/receipt/coupon-popup/apply?couponId=${couponId}`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "X-USER-ID": email
                    },
                    body: JSON.stringify(requestBody),
                })
                    .then(response => {
                        if (!response.ok) throw new Error("쿠폰 적용 요청 실패");
                        return response.json();
                    })
                    .then(data => {
                        const discountAmount = data.discountAmount
                        alert("쿠폰이 성공적으로 적용되었습니다!");
                        const discount = discountAmount;
                        console.log("업데이트 직전 productId, discount, couponId:", productId, discount, couponId)
                        // 부모창 업데이트
                        window.opener.updateProductPrice(productId, discount, couponId);

                        window.close();
                    })
                    .catch(error => {
                        alert(`에러 발생: ${error.message}`);
                        console.error(error);
                    });
            });
        });
    } else {
        console.error("적용 가능한 쿠폰 버튼을 찾을 수 없습니다.");
    }
});