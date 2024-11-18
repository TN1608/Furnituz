// Ghi lại vị trí cuộn trước khi chuyển trang
document.addEventListener("DOMContentLoaded", function () {
    const pageLinks = document.querySelectorAll('.pagination .page-link');
    pageLinks.forEach(link => {
        link.addEventListener('click', function () {
            // Lưu vị trí cuộn hiện tại vào sessionStorage
            sessionStorage.setItem('scrollPosition', window.scrollY);
        });
    });

    // Cuộn lại đúng vị trí khi tải trang
    const scrollPosition = sessionStorage.getItem('scrollPosition');
    if (scrollPosition) {
        window.scrollTo(0, parseInt(scrollPosition, 10));
        sessionStorage.removeItem('scrollPosition'); // Xóa để tránh cuộn lại sau các thao tác khác
    }
});