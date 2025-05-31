document.querySelectorAll(".items").forEach(item=>{
    item.addEventListener("click",(event)=>{
        const id = event.currentTarget.getAttribute("id");
        window.location.href=`/manager/project-detail?id=${id}`;
    })
})