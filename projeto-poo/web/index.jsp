<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login e Cadastro</title>
    <link rel="stylesheet" href="style/index.css">
    <script src="js/index.js"></script>
</head>
<body>
    <div id="mensagem-erro" style="display:none;color:#e53935;text-align:center;margin-bottom:15px;">
        <%
            // Recupera a mensagem de erro do request, se houver
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null && !errorMessage.isEmpty()) {
                // Exibe a mensagem de erro
                out.println(errorMessage);
                // Usa JavaScript para tornar a div visível
                out.println("<script>document.getElementById('mensagem-erro').style.display = 'block';</script>");
            }
        %>
    </div>
    <div class="container">
        <div class="tab-buttons">
            <button class="tab-button active" onclick="showForm('login')">Entrar</button>
            <button class="tab-button" onclick="showForm('register')">Registrar-se</button>
        </div>

        <div id="login-form" class="form-section active">
            <div class="social-login">
                <a href="YOUR_GOOGLE_AUTH_URL" class="social-button">
                    <img src="https://agenciapnz.com/wp-content/uploads/Logo-Google-G.png" alt="Google">
                    Entrar com Google
                </a>
                <a href="YOUR_APPLE_AUTH_URL" class="social-button">
                    <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Apple_logo_black.svg/1667px-Apple_logo_black.svg.png" alt="Apple">
                    Entrar com Apple
                </a>
            </div>

            <div class="separator">ou</div>

            <form action="auth" method="post">
                <input type="hidden" name="action" value="login">
                <div class="form-group">
                    <label for="login-email">Email</label>
                    <input type="email" id="login-email" name="login-email" placeholder="Seu email">
                </div>
                <div class="form-group">
                    <label for="login-password">Senha</label>
                    <input type="password" id="login-password" name="login-password" placeholder="Sua senha">
                </div>
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                    <label style="display: flex; align-items: center; gap: 5px;">
                        <input type="checkbox" style="width: auto; margin-right: 5px;"> <span style="font-size: 14px;">Lembrar-me</span>
                    </label>
                    <a href="#" class="forgot-password">Esqueceu a senha?</a>
                </div>
                <div class="button-container">
                    <button type="submit" class="login-button">Entrar</button>
                </div>
                <p style="font-size: 13px; color: #888; margin-top: 15px; text-align: center;">
                    Ao entrar, você concorda com os <a href="#" style="color: #e53935; text-decoration: none;">termos de serviço</a>,
                    <a href="#" style="color: #e53935; text-decoration: none;">política de privacidade</a> e
                    <a href="#" style="color: #e53935; text-decoration: none;">uso de cookies</a>.
                </p>
            </form>
        </div>

        <div id="register-form" class="form-section">
            <form action="auth" method="post">
                <input type="hidden" name="action" value="register">
                <div class="form-group">
                    <label for="register-name">Nome</label>
                    <input type="text" id="register-name" name="register-name" placeholder="Seu nome">
                </div>
                <div class="form-group">
                    <label for="register-email">Email</label>
                    <input type="email" id="register-email" name="register-email" placeholder="Seu email">
                </div>
                <div class="form-group">
                    <label for="register-password">Senha</label>
                    <input type="password" id="register-password" name="register-password" placeholder="Sua senha">
                </div>
                <div class="form-group">
                    <label for="confirm-password">Confirmar Senha</label>
                    <input type="password" id="confirm-password" name="confirm-password" placeholder="Confirme sua senha">
                </div>
                <button type="submit" class="register-button">Criar conta</button>
                <div class="create-account">
                    Já tem uma conta? <a href="#" onclick="showForm('login')">Entrar</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>