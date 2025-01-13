// 모달 제어
function openCouponIssueModal(url) {
    const modal = document.getElementById('couponIssueModal');
    const modalContent = document.getElementById('couponIssueModalContent');

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error('쿠폰발급 모달 에러');
            }
            return response.text();
        })
        .then(html => {
            modalContent.innerHTML = html;
            modal.style.display = 'block';
        })
        .catch(error => {
            console.error('모달 내용을 불러오는 중 오류가 발생했습니다:', error);
            alert('모달 내용을 불러오는 중 오류가 발생했습니다');
        });
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.style.display = 'none';

    const modalContent = document.getElementById(`${modalId}Content`);
    if (modalContent) {
        modalContent.innerHTML = '';
    }
}