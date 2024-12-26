main();

async function main() {
    const urlParams = new URLSearchParams(window.location.search);
    const queryOrderId = urlParams.get("orderId");
    const queryOrderName = urlParams.get("orderName");
    const queryOrderAmount = Number(urlParams.get("amount"));

    const button = document.getElementById("payment-button");
    const coupon = document.getElementById("coupon-box");
    const amount = {
        currency: "KRW",
        value: queryOrderAmount,
    };
    // ------  결제위젯 초기화 ------
    // TODO: clientKey는 개발자센터의 결제위젯 연동 키 > 클라이언트 키로 바꾸세요.
    // TODO: 구매자의 고유 아이디를 불러와서 customerKey로 설정하세요. 이메일・전화번호와 같이 유추가 가능한 값은 안전하지 않습니다.
    // @docs https://docs.tosspayments.com/sdk/v2/js#토스페이먼츠-초기화
    const clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm";
    const customerKey = generateRandomString();
    const tossPayments = TossPayments(clientKey);
    // 회원 결제
    // @docs https://docs.tosspayments.com/sdk/v2/js#tosspaymentswidgets
    const widgets = tossPayments.widgets({
        customerKey,
    });
    // 비회원 결제
    // const widgets = tossPayments.widgets({customerKey: TossPayments.ANONYMOUS});

    // ------  주문서의 결제 금액 설정 ------
    // TODO: 위젯의 결제금액을 결제하려는 금액으로 초기화하세요.
    // TODO: renderPaymentMethods, renderAgreement, requestPayment 보다 반드시 선행되어야 합니다.
    // @docs https://docs.tosspayments.com/sdk/v2/js#widgetssetamount
    await widgets.setAmount(amount);

    await Promise.all([
        // ------  결제 UI 렌더링 ------
        // @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrenderpaymentmethods
        widgets.renderPaymentMethods({
            selector: "#payment-method",
            // 렌더링하고 싶은 결제 UI의 variantKey
            // 결제 수단 및 스타일이 다른 멀티 UI를 직접 만들고 싶다면 계약이 필요해요.
            // @docs https://docs.tosspayments.com/guides/v2/payment-widget/admin#새로운-결제-ui-추가하기
            variantKey: "DEFAULT",
        }),
        // ------  이용약관 UI 렌더링 ------
        // @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrenderagreement
        widgets.renderAgreement({
            selector: "#agreement",
            variantKey: "AGREEMENT",
        }),
    ]);

    // ------  주문서의 결제 금액이 변경되었을 경우 결제 금액 업데이트 ------
    // @docs https://docs.tosspayments.com/sdk/v2/js#widgetssetamount
    coupon.addEventListener("change", async function () {
        if (coupon.checked) {
            await widgets.setAmount({
                currency: "KRW",
                value: amount.value - 5000,
            });

            return;
        }

        // TODO: 페이지 불러올 때 정보 채워넣을 수 있게 바꿔야함
        await widgets.setAmount({
            currency: "KRW",
            value: amount.value,
        });
    });

    // ------ '결제하기' 버튼 누르면 결제창 띄우기 ------
    // @docs https://docs.tosspayments.com/sdk/v2/js#widgetsrequestpayment
    // button.addEventListener("click", async function () {
    //   // 결제를 요청하기 전에 orderId, amount를 서버에 저장하세요.
    //   // 결제 과정에서 악의적으로 결제 금액이 바뀌는 것을 확인하는 용도입니다.
    //
    //
    //
    //
    //   await widgets.requestPayment({
    //     orderId: generateRandomString(),
    //     orderName: "토스 티셔츠 외 2건",
    //     successUrl: window.location.origin + "/payment/success.html",
    //     failUrl: window.location.origin + "/fail.html",
    //     customerEmail: "customer123@gmail.com",
    //     customerName: "김토스",
    //     // 가상계좌 안내, 퀵계좌이체 휴대폰 번호 자동 완성에 사용되는 값입니다. 필요하다면 주석을 해제해 주세요.
    //     // customerMobilePhone: "01012341234",
    //   });
    // });
    button.addEventListener("click", async function () {
        // const orderId = generateRandomString();
        const paymentInfo = {
            queryOrderId,
            amount: coupon.checked ? amount.value - 5000 : amount.value
        };

        // 결제정보 임시 저장
        // const response = await fetch("http://plzbuybook:3000/api/payments/save-payment", {
        //     method: "POST",
        //     headers: {"Content-Type": "application/json"},
        //     body: JSON.stringify(paymentInfo),
        // });
        //
        // if (!response.ok) {
        //     alert("결제 정보를 저장하는데 실패했습니다. 다시 시도해 주세요.");
        //     return;
        // }

        // 결제 요청
        // TODO: 페이지 불러올 때 정보 채워넣게 바꿔야함
        // await widgets.requestPayment({
        //     queryOrderId,
        //     orderName: queryOrderName,
        //     successUrl: window.location.origin + "/payment/success.html",
        //     failUrl: window.location.origin + "/payment/fail.html",
        //     // customerEmail: "customer123@gmail.com",
        //     // customerName: "김토스",
        // });
        await widgets.requestPayment({
            queryOrderId,
            orderName: queryOrderName,
            successUrl: window.location.origin + "/payments/success",
            failUrl: window.location.origin + "/payments/fail",
            // customerEmail: "customer123@gmail.com",
            // customerName: "김토스",
        });
    });

}

//
// document.getElementById("payment-window-button").addEventListener("click", () => {
//   location.href = "../../payment/checkout.html";
// });
//
// document.getElementById("brandpay-button").addEventListener("click", () => {
//   location.href = "/brandpay/checkout.html";
// });

function generateRandomString() {
    return window.btoa(Math.random()).slice(0, 20);
}
