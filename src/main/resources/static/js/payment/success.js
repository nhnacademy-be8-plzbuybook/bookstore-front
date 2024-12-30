// // 쿼리 파라미터 값을 서버로 전달해 결제 요청할 때 보낸 데이터와 동일한지 반드시 확인하세요.
// // 클라이언트에서 결제 금액을 조작하는 행위를 방지할 수 있습니다.
const urlParams = new URLSearchParams(window.location.search);
//
// // 서버로 결제 승인에 필요한 결제 정보를 보내세요.
// async function confirm() {
//     var requestData = {
//         paymentKey: urlParams.get("paymentKey"),
//         orderId: urlParams.get("orderId"),
//         amount: urlParams.get("amount"),
//     };
//
//     //TODO: 게이트웨이로 바꿔야함
//     const response = await fetch("http://localhost:8090/api/payments/confirm/widget", {
//         method: "POST",
//         headers: {
//             "Content-Type": "application/json",
//         },
//         body: JSON.stringify(requestData),
//     });
//
//     const json = await response.json();
//
//     if (!response.ok) {
//         throw {message: json.message, code: json.code};
//     }
//
//     return json;
// }
//
// confirm()
//     .then(function (data) {
//         // TODO: 결제 승인에 성공했을 경우 UI 처리 로직을 구현하세요.
//
//         document.getElementById("response").innerHTML = `<pre>${JSON.stringify(data, null, 4)}</pre>`;
//     })
//     .catch((err) => {
//         // TODO: 결제 승인에 실패했을 경우 UI 처리 로직을 구현하세요.
//         window.location.href = `/payment/fail.html?message=${err.message}&code=${err.code}`;
//     });
//
// const paymentKeyElement = document.getElementById("paymentKey");
// const orderIdElement = document.getElementById("orderId");
const amountElement = document.getElementById("amount");
//
// orderIdElement.textContent = urlParams.get("orderId");
amountElement.textContent = urlParams.get("amount") + "원";
// paymentKeyElement.textContent = urlParams.get("paymentKey");


document.addEventListener("DOMContentLoaded", function () {
    // const orderId = document.getElementById("orderId").value;
    const orderId =  urlParams.get("orderId");
    // 주문 완료 요청
    fetch(`/api/orders/${orderId}/complete`, {
        method: "POST",
    })
        .then(async response => {
            if (response.ok) {
                alert("주문이 성공적으로 완료되었습니다.");
                const orderId = await response.text();
                console.log("주문성공! orderId: " + orderId);
                window.location.replace(`/orders/${orderId}`) // 주문상세 페이지로 이동
            } else {
                alert("주문이 실패했습니다.");
            }
        })
        .catch(error => console.error("Error:", error));
});