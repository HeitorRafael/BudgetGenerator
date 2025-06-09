<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="models.Usuario" %>
<%
    // Verifica se o usuário está logado
    Object usuarioObj = session.getAttribute("loggedInUser");
    if (usuarioObj == null) {
        response.sendRedirect("index.html?erro=autenticacao");
        return;
    }
    // Supondo que seu objeto seja User ou Usuario
    // Ajuste o cast conforme sua classe de usuário
    Usuario usuario = (Usuario) usuarioObj;
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Home - BudgetGenerator</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="style/home.css">
    <link rel="stylesheet" href="style/styleHistorico.css">
</head>
<body>
     <%@ include file="WEB-INF/jspf/navbar.jspf" %>

    <div class="container py-5">
        <div class="row justify-content-center align-items-start">
            <div class="col-md-6">
                <div class="logo mb-3">
                </div>
                <div class="main-title mt-3">HOME</div>
                <h5 class="mt-3 fw-bold"><%= usuario.getNome() %></h5>
                <p class="text-white"><%= usuario.getEmail() %></p>
                <nav class="mt-4">
                    <span class="text-white">•</span>
                    <a class="nav-link d-inline" href="orcamento.html">Gerar Orçamento</a>
                    <span class="text-white">•</span>
                    <a class="nav-link d-inline" href="logout">Sair</a>
                </nav>
            </div>
        </div>
    </div>
</body>
</html>