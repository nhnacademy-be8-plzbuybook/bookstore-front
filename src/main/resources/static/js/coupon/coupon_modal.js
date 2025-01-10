// 모달 제어
function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'none';
    }
}

// 책 검색
function openBookSearchModal() {
    const modal = document.getElementById('bookSearchModal');
    const container = document.getElementById('bookSearchContainer');
    const searchKeyword = prompt("검색어를 입력하세요");

    if (searchKeyword) {
        fetch(`/admin/coupon-book-search?searchKeyword=${encodeURIComponent(searchKeyword)}&page=0`)
            .then(response => response.text())
            .then(html => {
                container.innerHTML = html;
                modal.style.display = 'block';
            })
            .catch(error => {
                console.error("책 검색 모달 에러:", error);
            });
    }
}

document.addEventListener('click', (event) => {
    if (event.target.closest('#bookSearchContainer a')) {
        event.preventDefault();
        const link = event.target.closest('a').getAttribute('href');

        fetch(link)
            .then(response => response.text())
            .then(html => {
                document.getElementById('bookSearchContainer').innerHTML = html;
            })
            .catch(error => {
                console.error("페이징 처리 에러:", error);
            });
    }
});

// 카테고리 검색
function openCategorySearchModal() {
    const modal = document.getElementById('categorySearchModal');
    const container = document.getElementById('categorySearchContainer');
    const searchKeyword = prompt("카테고리 검색어를 입력하세요");

    if (searchKeyword) {
        fetch(`/admin/coupon-category-search?searchKeyword=${encodeURIComponent(searchKeyword)}&page=0`)
            .then(response => response.text())
            .then(html => {
                container.innerHTML = html;
                modal.style.display = 'block';
            })
            .catch(error => {
                console.error("카테고리 검색 모달 에러:", error);
            });
    }
}

// 고정(비율) 할인 선택
function updateDiscountRatio() {
    const saleType = document.getElementById('saleType').value;
    const discountRatioField = document.getElementById('discountRatio');
    const discountRatioHidden = document.getElementById('discountRatioHidden');

    if (saleType === 'AMOUNT') {
        discountRatioField.value = 0;
        discountRatioField.disabled = true;
        discountRatioHidden.value = 0;
    } else if (saleType === 'RATIO') {
        discountRatioField.value = 0;
        discountRatioField.disabled = false;
        discountRatioHidden.value = 0;
    }
}

document.addEventListener('click', (event) => {
    if (event.target.closest('#categorySearchContainer a')) {
        event.preventDefault();
        const link = event.target.closest('a').getAttribute('href');

        fetch(link)
            .then(response => response.text())
            .then(html => {
                document.getElementById('categorySearchContainer').innerHTML = html;
            })
            .catch(error => {
                console.error("카테고리 페이징 처리 에러:", error);
            });
    }
});



document.addEventListener('DOMContentLoaded', () => {
    // 카테고리 선택 시 동작
    function selectCategory(categoryId) {
        const categoryIdDisplay = document.getElementById('selectedCategoryDisplay');
        const categoryIdInput = document.getElementById('selectedCategoryId');
        const bookIdDisplay = document.getElementById('selectedBookDisplay');
        const bookIdInput = document.getElementById('selectedBookId');

        // 선택된 카테고리 ID 업데이트
        categoryIdDisplay.textContent = `선택된 카테고리 ID: ${categoryId}`;
        categoryIdInput.value = categoryId;

        // 선택된 책 ID 초기화
        bookIdDisplay.textContent = '선택된 책 ID: 없음';
        bookIdInput.value = '';

        alert(`선택된 Category ID: ${categoryId}`);
        closeModal('categorySearchModal');
    }

    // 책 선택 시 동작
    function selectBook(bookId) {
        const bookIdDisplay = document.getElementById('selectedBookDisplay');
        const bookIdInput = document.getElementById('selectedBookId');
        const categoryIdDisplay = document.getElementById('selectedCategoryDisplay');
        const categoryIdInput = document.getElementById('selectedCategoryId');

        // 선택된 책 ID 업데이트
        bookIdDisplay.textContent = `선택된 책 ID: ${bookId}`;
        bookIdInput.value = bookId;

        // 선택된 카테고리 ID 초기화
        categoryIdDisplay.textContent = '선택된 카테고리 ID: 없음';
        categoryIdInput.value = '';

        alert(`선택된 Book ID: ${bookId}`);
        closeModal('bookSearchModal');
    }

    // 쿠폰 범위 설정
    function setCouponScope(scope) {
        const scopeInput = document.getElementById('couponScope');
        scopeInput.value = scope;
        alert(`쿠폰 적용 범위가 '${scope}'로 설정되었습니다.`);

        if (scope === 'ALL') {
            document.getElementById('selectedBookId').value = '';
            document.getElementById('selectedCategoryId').value = '';
            document.getElementById('selectedBookDisplay').textContent = '선택된 책 ID: 없음';
            document.getElementById('selectedCategoryDisplay').textContent = '선택된 카테고리 ID: 없음';
        }
    }

    // 함수들을 전역으로 노출
    window.selectCategory = selectCategory;
    window.selectBook = selectBook;
    window.setCouponScope = setCouponScope;
});