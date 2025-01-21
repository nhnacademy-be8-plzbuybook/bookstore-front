function couponPolicyFindOpenModal(url) {
    const modal = document.getElementById('couponPolicyFindModal');
    const content = document.getElementById('couponPolicyFindModalContent');

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('서버 응답 오류');
            }
            return response.text();
        })
        .then(html => {
            content.innerHTML = html;
            modal.style.display = 'block';
        })
        .catch(error => {
            console.error('쿠폰정책 조회 오류:', error);
            alert('쿠폰정책을 불러오는데 실패했습니다.');
        });
}

function couponPolicyFindCloseModal() {
    const modal = document.getElementById('couponPolicyFindModal');
    modal.style.display = 'none';
}

function couponPolicyFindSelect(policyId) {
    alert(`선택된 쿠폰정책 ID: ${policyId}`);
    couponPolicyFindCloseModal();
}

function couponPolicyFindGoToPage() {
    const pageInput = document.getElementById('couponPolicyFindPageInput');
    const page = pageInput.value;

    if (!page || page < 0) {
        alert('유효한 페이지 번호를 입력해주세요.');
        return;
    }

    couponPolicyFindOpenModal(`/admin/coupon-policies?page=${page}`);
}