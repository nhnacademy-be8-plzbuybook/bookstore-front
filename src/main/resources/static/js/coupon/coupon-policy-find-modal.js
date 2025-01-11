// 모달창 제어
function openModal(url) {
    const modal = document.getElementById('couponPolicyModal');
    const modalContent = document.getElementById('modalContent');

    // 서버에서 HTML 로드
    fetch(url)
        .then(response => response.text())
        .then(html => {
            modalContent.innerHTML = html;
            modal.style.display = 'block';
        })
        .catch(error => console.error('쿠폰정책 조회 오류:', error));
}

function closeModal() {
    const modal = document.getElementById('couponPolicyModal');
    modal.style.display = 'none';
}

function updateModalContent(url) {
    openModal(url);
    return false; // 기본 동작 방지
}

// 선택된 정책 처리
function selectPolicy(policyId) {
    alert('선택된 정책 ID: ' + policyId);
    closeModal();
}

// 페이지 이동 처리
function goToPage() {
    const pageInput = document.getElementById('pageInput').value || 0;
    openModal(`/admin/coupon-policies?page=${pageInput}`);
}