document.addEventListener('DOMContentLoaded', function () {
    const errorLink = document.getElementById('errorLink');
    errorLink.addEventListener('click', function (event) {
        event.preventDefault();
        window.history.back();
    });
});