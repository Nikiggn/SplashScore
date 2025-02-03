function loadUsersTable(users) {
    const tableBody = document.getElementById("user-table").getElementsByTagName('tbody')[0];

    // Clear existing rows
    tableBody.innerHTML = '';

    // Loop through users and create rows
    users.forEach(user => {
        const row = tableBody.insertRow();

        // Insert cells into each row
        row.innerHTML = `
            <td><img src="${user.avatar}" alt="Avatar"></td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
            <td>${user.createdOn}</td>
            <td><button>Edit</button></td>
        `;
    });
}

// Call the function to load data
loadUsersTable(usersData);