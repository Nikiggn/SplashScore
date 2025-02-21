function showDiv(divId){
    // hide all content divs
    document.querySelectorAll('.content-section').forEach(div => {
        div.classList.remove('active');
    });


    const targetDiv = document.getElementById(divId);
    if (targetDiv){
        targetDiv.classList.add('active')
    }
}