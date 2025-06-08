<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String erro = request.getParameter("erro");
    if (session.getAttribute("usuarioLogado") != null) {
        response.sendRedirect("home.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Login - BudgetGenerator</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="style/home.css">
</head>
<body class="bg-dark text-white">
    <div class="container vh-100 d-flex justify-content-center align-items-center">
        <div class="row w-50 bg-form p-4 shadow rounded">
            <h2 class="text-center mb-4">Login</h2>
            <% if (erro != null) { %>
                <div class="alert alert-danger"><%= erro %></div>
            <% } %>
            <form method="post" action="login.jsp">
                <div class="mb-3">
                    <label for="usuario" class="form-label">UsuÃ¡rio</label>
                    <input type="text" class="form-control" id="usuario" name="usuario" required value="teste">
                </div>
                <div class="mb-3">
                    <label for="senha" class="form-label">Senha</label>
                    <input type="password" class="form-control" id="senha" name="senha" required value="1234">
                </div>
                <button type="submit" class="btn btn-custom w-100">Entrar</button>
            </form>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"></script>
</body>
<%
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");
        if ("teste".equals(usuario) && "1234".equals(senha)) {
            session.setAttribute("usuarioLogado", usuario);
            response.sendRedirect("home.jsp");
        } else {
            response.sendRedirect("login.jsp?erro=UsuÃ¡rio ou senha invÃ¡lidos");
        }
    }
%>
</html>