const apiUrl = "/api/selling-books";
const bookList = document.getElementById("bookList");
const pagination = document.getElementById("pagination");
const itemsPerPage = 12;

// 책 목록 렌더링
function renderBooks(books) {
    bookList.innerHTML = ""; // 기존 목록 초기화
    books.forEach(book => {
        const bookElement = document.createElement("div");
        bookElement.className = "book";
        bookElement.innerHTML = `
            <img src="${book.imageUrl}" alt="${book.bookTitle}" style="max-width: 100px; height: auto;">
            <div>${book.bookTitle}</div>
        `;
        bookList.appendChild(bookElement);
    });
}

// 페이징 버튼 렌더링
function renderPagination(currentPage, totalPages) {
    pagination.innerHTML = ""; // 기존 버튼 초기화

    // 이전 버튼
    const prevButton = document.createElement("li");
    prevButton.className = `page-item ${currentPage === 0 ? "disabled" : ""}`;
    prevButton.innerHTML = `<a href="#">이전</a>`;
    prevButton.onclick = () => {
        if (currentPage > 0) loadPage(currentPage - 1);
    };
    pagination.appendChild(prevButton);

    // 페이지 번호 버튼
    for (let i = 0; i < totalPages; i++) {
        const pageButton = document.createElement("li");
        pageButton.className = `page-item ${i === currentPage ? "active" : ""}`;
        pageButton.innerHTML = `<a href="#">${i + 1}</a>`;
        pageButton.onclick = () => loadPage(i);
        pagination.appendChild(pageButton);
    }

    // 다음 버튼
    const nextButton = document.createElement("li");
    nextButton.className = `page-item ${currentPage === totalPages - 1 ? "disabled" : ""}`;
    nextButton.innerHTML = `<a href="#">다음</a>`;
    nextButton.onclick = () => {
        if (currentPage < totalPages - 1) loadPage(currentPage + 1);
    };
    pagination.appendChild(nextButton);
}

// 데이터 로드
function loadPage(page) {
    fetch(`${apiUrl}?page=${page}&size=${itemsPerPage}&sortBy=sellingBookId&sortDir=desc`)
        .then(response => response.json())
        .then(data => {
            renderBooks(data.content);
            renderPagination(data.number, data.totalPages);
        })
        .catch(error => console.error("Error loading page:", error));
}

// 초기 로드
document.addEventListener("DOMContentLoaded", () => {
    loadPage(0);
});
