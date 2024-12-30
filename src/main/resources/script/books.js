// // // 책 리스트 로드 함수
// // function loadBooks(page = 0, sortBy = 'sellingBookId', sortDir = 'desc') {
// //     const size = 10; // 페이지 당 아이템 수
// //
// //     fetch(`/api/selling-books?page=${page}&size=${size}&sortBy=${sortBy}&sortDir=${sortDir}`)
// //         .then(response => response.json())
// //         .then(data => {
// //             // 책 리스트 렌더링
// //             renderBooks(data.content);
// //             // 페이징 버튼 렌더링
// //             renderPagination(data.number, data.totalPages);
// //         })
// //         .catch(error => {
// //             console.error('Error fetching books:', error);
// //         });
// // }
// //
// // // 책 리스트 렌더링 함수
// // function renderBooks(books) {
// //     const bookList = document.querySelector('.book-list');
// //     bookList.innerHTML = ''; // 기존 리스트 초기화
// //
// //     books.forEach(book => {
// //         const bookElement = document.createElement('div');
// //         bookElement.classList.add('book');
// //         bookElement.innerHTML = `
// //             <a href="/book/detail/${book.sellingBookId}">
// //                 <img src="${book.imageUrl}" alt="${book.bookTitle}">
// //                 <div>${book.bookTitle}</div>
// //             </a>
// //         `;
// //         bookList.appendChild(bookElement);
// //     });
// // }
//
// // 페이징 버튼 렌더링 함수
// function renderPagination(currentPage, totalPages) {
//     const pagination = document.getElementById('pagination');
//     pagination.innerHTML = ''; // 기존 버튼 초기화
//
//     // 이전 버튼
//     if (currentPage > 0) {
//         const prevButton = document.createElement('li');
//         prevButton.classList.add('page-item');
//         prevButton.innerHTML = `
//             <a class="page-link" href="#" onclick="loadBooks(${currentPage - 1})">&laquo;</a>
//         `;
//         pagination.appendChild(prevButton);
//     }
//
//     // 페이지 번호 버튼 (최대 5개로 제한)
//     const maxVisiblePages = 5;
//     let startPage = Math.max(0, currentPage - Math.floor(maxVisiblePages / 2));
//     let endPage = Math.min(startPage + maxVisiblePages, totalPages);
//
//     if (endPage - startPage < maxVisiblePages) {
//         startPage = Math.max(0, endPage - maxVisiblePages);
//     }
//
//     for (let i = startPage; i < endPage; i++) {
//         const pageButton = document.createElement('li');
//         pageButton.classList.add('page-item', i === currentPage ? 'active' : '');
//         pageButton.innerHTML = `
//             <a class="page-link" href="#" onclick="loadBooks(${i})">${i + 1}</a>
//         `;
//         pagination.appendChild(pageButton);
//     }
//
//     // 다음 버튼
//     if (currentPage < totalPages - 1) {
//         const nextButton = document.createElement('li');
//         nextButton.classList.add('page-item');
//         nextButton.innerHTML = `
//             <a class="page-link" href="#" onclick="loadBooks(${currentPage + 1})">&raquo;</a>
//         `;
//         pagination.appendChild(nextButton);
//     }
// }
//
// // 페이지 로드 시 기본 데이터 로드
// document.addEventListener('DOMContentLoaded', () => {
//     loadBooks();
// });
