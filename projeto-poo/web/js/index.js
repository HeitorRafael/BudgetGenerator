
    function showForm(formId) {
        const loginForm = document.getElementById('login-form');
        const registerForm = document.getElementById('register-form');
        const loginTab = document.querySelector('.tab-buttons button:first-child');
        const registerTab = document.querySelector('.tab-buttons button:last-child');

        if (formId === 'login') {
            loginForm.classList.add('active');
            registerForm.classList.remove('active');
            loginTab.classList.add('active');
            registerTab.classList.remove('active');
        } else if (formId === 'register') {
            registerForm.classList.add('active');
            loginForm.classList.remove('active');
            registerTab.classList.add('active');
            loginTab.classList.remove('active');
        }
    }

    // Validação dos formulários
    document.addEventListener('DOMContentLoaded', function() {
        // Login
        document.querySelector('#login-form form').addEventListener('submit', function(e) {
            const email = document.getElementById('login-email').value.trim();
            const senha = document.getElementById('login-password').value.trim();
            if (!email || !senha) {
                alert('Preencha todos os campos de login.');
                e.preventDefault();
            }
        });

        // Cadastro
        document.querySelector('#register-form form').addEventListener('submit', function(e) {
            const nome = document.getElementById('register-name').value.trim();
            const email = document.getElementById('register-email').value.trim();
            const senha = document.getElementById('register-password').value.trim();
            const confirmar = document.getElementById('confirm-password').value.trim();
            if (!nome || !email || !senha || !confirmar) {
                alert('Preencha todos os campos de cadastro.');
                e.preventDefault();
            } else if (senha !== confirmar) {
                alert('As senhas não coincidem.');
                e.preventDefault();
            }
        });
    });

