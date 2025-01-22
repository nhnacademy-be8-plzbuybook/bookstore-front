
function openCouponMemberModal(url) {
    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('회원 쿠폰 조회 오류 발생');
            }
            return response.text();
        })
        .then(html => {
            document.getElementById('couponMemberModalContent').innerHTML = html;
            document.getElementById('couponMemberModal').style.display = 'block';
        })
        .catch(error => {
            console.error('회원 쿠폰 모달 로드 실패:', error);
            alert('회원 쿠폰 목록을 불러오는데 실패했습니다');
        });
}

function closeCouponMemberModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
}

function goToMemberFindPage(page) {
    const pageSize = 10; // 페이지 크기
    const url = `/admin/member-coupons?page=${page}&pageSize=${pageSize}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error(`페이지 요청 실패. 상태 코드: ${response.status}`);
            }
            return response.text();
        })
        .then(html => {
            console.log(`페이지 ${page} 데이터:`, html);
            document.getElementById('couponMemberModalContent').innerHTML = html;
        })
        .catch(error => {
            console.error('페이지네이션 오류:', error);
            alert('데이터를 불러오는데 실패했습니다. 자세한 내용은 콘솔을 확인하세요.');
        });
}

function goToMemberFindPageByInput() {
    const pageInput = document.getElementById('pageInput').value;
    if (pageInput !== '' && pageInput >= 0) {
        goToMemberFindPage(pageInput);
    } else {
        alert('유효한 페이지 번호를 입력해주세요.');
    }
}