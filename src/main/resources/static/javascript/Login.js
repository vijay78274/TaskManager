document.getElementById('loginForm').addEventListener('submit', async function(e) {
    e.preventDefault(); // prevent form from submitting normally

    const response = await fetch('/jwt_login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            username: document.getElementById('username').value,
            password: document.getElementById('password').value
        })
    });

    const data = await response.json();

    if (response.ok) {
        // Store token in localStorage or sessionStorage
        localStorage.setItem('jwtToken', data.token);
        // window.location.href = '/home'; 
    } 
});