document.addEventListener('DOMContentLoaded', () => {
    const settingsButton = document.getElementById('settings-button');
    const dropdown = settingsButton.closest('.dropdown');

    settingsButton.addEventListener('click', (event) => {
        event.stopPropagation(); // Prevent click from bubbling up
        dropdown.classList.toggle('active');
    });

    document.addEventListener('click', () => {
        dropdown.classList.remove('active'); // Close if clicked outside
    });


    const menuButton = document.getElementById('menu-button');
    const dropdown2 = menuButton.closest('.dropdown');
    menuButton.addEventListener('click', (event) => {
        event.stopPropagation(); // Prevent click from bubbling up
        dropdown2.classList.toggle('active');
    });

    document.addEventListener('click', () => {
        dropdown2.classList.remove('active'); // Close if clicked outside
    });


});