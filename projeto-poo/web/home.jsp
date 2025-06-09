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
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark m-4">
          <div class="container-fluid">
            <a class="navbar-brand" href="home.jsp">
              <img src="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/icons/house-fill.svg" alt="Home" width="30" height="30" class="d-inline-block align-top">
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
              <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="nav-item">
                  <a class="nav-link" href="orcamento.html">Gerar Orçamento</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="index.html">Sair</a>
                </li>
              </ul>
            </div>
          </div>
        </nav>
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