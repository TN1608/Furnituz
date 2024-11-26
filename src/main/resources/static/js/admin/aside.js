var toggleClick = document.querySelector(".toggleBox");
var container = document.querySelector(".container");
toggleClick.addEventListener('click',()=>{
    toggleClick.classList.toggle('active');
    container.classList.toggle('active');
})