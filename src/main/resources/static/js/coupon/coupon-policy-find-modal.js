// 쿠폰 정책 모달 열기
function couponPolicyFindOpenModal(url) {
    const modal = document.getElementById('couponPolicyFindModal');
    const content = document.getElementById('couponPolicyFindModalContent');

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('서버 응답 오류');
            }
            return response.text(); // HTML 형식으로 데이터 파싱
        })
        .then(html => {
            content.innerHTML = html; // 모달 내용 업데이트
            modal.style.display = 'block'; // 모달 보이기
        })
        .catch(error => {
            console.error('쿠폰 정책 조회 오류:', error);
            alert('쿠폰 정책을 불러오는 데 실패했습니다.');
        });
}

// 모달 닫기
function couponPolicyFindCloseModal() {
    const modal = document.getElementById('couponPolicyFindModal');
    modal.style.display = 'none';
}

// 페이지 변경
function couponPolicyFindChangePage(offset) {
    const currentPage = parseInt(document.getElementById('currentPageDisplay').textContent, 10) - 1;
    const newPage = currentPage + offset;

    if (newPage < 0) {
        alert('첫 번째 페이지입니다.');
        return;
    }

    couponPolicyFindOpenModal(`/admin/coupon-policies?page=${newPage}`);
}

// 특정 페이지 이동
function couponPolicyFindGoToPage() {
    const pageInput = document.getElementById('couponPolicyFindPageInput');
    const page = parseInt(pageInput.value, 10) - 1;

    if (isNaN(page) || page < 0) {
        alert('유효한 페이지 번호를 입력해주세요.');
        return;
    }

    couponPolicyFindOpenModal(`/admin/coupon-policies?page=${page}`);
}

// 쿠폰 정책 선택
function couponPolicyFindSelect(policyId) {
    alert(`선택된 쿠폰 정책 ID: ${policyId}`);
    couponPolicyFindCloseModal();
}