window.onload = function() {
    const urlParams = new URLSearchParams(window.location.search);
    const activeDiv = urlParams.get('activeDiv');

    // If there's an active div in the URL, show it
    if (activeDiv) {
        showDiv(activeDiv);
    } else {
        // Default div (you can modify this if needed)
        showDiv('users');
    }
};

function showDiv(divId) {
    // Hide all content divs
    document.querySelectorAll('.content-section').forEach(div => {
        div.classList.remove('active');
    });

    // Show the selected div
    const targetDiv = document.getElementById(divId);
    if (targetDiv) {
        targetDiv.classList.add('active');
    }

    // Highlight the clicked link (active state)
    document.querySelectorAll('a').forEach(link => {
        link.classList.remove('active');
    });

    // Set the active class on the correct link
    const activeLink = document.getElementById(divId + '-link');
    if (activeLink) {
        activeLink.classList.add('active');
    }
}
