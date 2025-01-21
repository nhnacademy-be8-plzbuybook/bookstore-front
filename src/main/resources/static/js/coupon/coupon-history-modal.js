
function searchCouponHistories() {
    const couponId = document.getElementById('couponIdInput').value;

    if (!couponId) {
        alert('쿠폰 ID를 입력해주세요.');
        return;
    }

    const url = `/admin/coupon-histories/${couponId}?page=0&pageSize=10`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.text();
        })
        .then(html => {
            const modalContent = document.getElementById('couponHistoryModalContent');
            modalContent.innerHTML = html;

            const modal = document.getElementById('couponHistoryModal');
            modal.style.display = 'block';
        })
        .catch(error => {
            console.error('쿠폰 이력 조회 오류:', error);
            alert('쿠폰 이력 조회에 실패했습니다.');
        });
}

function closeCouponHistoriesModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'none';
    }
}

// 입력받은 페이지로 이동
function goToCouponHistoryPageByInput() {
    const pageInput = document.getElementById('pageInput').value;
    if (pageInput !== '' && pageInput >= 0) {
        goToCouponHistoryPage(pageInput);
    } else {
        alert('유효한 페이지 번호를 입력해주세요.');
    }
}

function goToCouponHistoryPage(page) {
    const couponId = document.getElementById('couponIdInput').value; // 쿠폰 ID 유지
    if (!couponId) {
        alert('쿠폰 ID를 입력해주세요.');
        return;
    }

    const url = `/admin/coupon-histories/${couponId}?page=${page}&pageSize=10`;

    fetch(url)
        .then(response => response.text())
        .then(html => {
            document.getElementById('couponHistoryModalContent').innerHTML = html;
        })
        .catch(error => console.error('Error fetching page data:', error));
}