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
});