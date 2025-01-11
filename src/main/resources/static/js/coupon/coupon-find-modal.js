// 모달 열기
function openCouponModal(url) {
    const modal = document.getElementById('couponModal');
    const couponModalContent = document.getElementById('couponModalContent');

    // 서버에서 HTML 로드
    fetch(url)
        .then(response => response.text())
        .then(html => {
            couponModalContent.innerHTML = html;
            modal.style.display = 'block';
        })
        .catch(error => console.error('쿠폰 조회 오류:', error));
}

// 모달 닫기
function closeCouponModal() {
    const modal = document.getElementById('couponModal');
    modal.style.display = 'none';
}

// 페이지 이동
function goToCouponPage() {
    const pageInput = document.getElementById('pageInput').value || 0;
    openModal(`/admin/coupons?page=${pageInput}`);
}

function updateModalContent(url) {
    openModal(url);
    return false; // 기본 동작 방지
}

// 쿠폰 선택
function selectCoupon(couponId) {
    const couponInput = document.getElementById('coupon-policy-id');
    couponInput.value = couponId;
    closeCouponModal();
}