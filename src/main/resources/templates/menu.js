let horizontalUnderLine=document.getElementById("horizontal-underline");
let horizontalMenus=document.querySelectorAll("nav a")
console.log(horizontalUnderLine);

horizontalMenus.forEach((menu)=>
  menu.addEventListener("click",(e)=>horizontalIndicator(e))
);

function horizontalIndicator(e) {
horizontalUnderLine.style.left = e.currentTarget.offsetLeft + "px";
horizontalUnderLine.style.width = e.currentTarget.offsetWidth + "px";
horizontalUnderLine.style.top = e.currentTarget.offsetTop + e.currentTarget.offsetHeight + "px";
}

