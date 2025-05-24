document.getElementById('loginForm').addEventListener('submit', async function(e) {
    e.preventDefault(); // prevent form from submitting normally

        const response = await fetch('/jwt_login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                empId: document.getElementById('username').value,
                password: document.getElementById('password').value
            })
        })
        .then(response => {
            if (response.redirected) {
                window.location.href = response.url;  
            }
        });
});