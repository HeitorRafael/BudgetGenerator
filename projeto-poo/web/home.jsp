<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session == null || session.getAttribute("usuarioLogado") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <title>Home - BudgetGenerator</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="style/home.css">
    </head>
    <body>
        <%@ include file="WEB-INF/jspf/navbar.jspf" %>

        <div class="container py-5">
            <div class="row justify-content-center align-items-start">
                <!-- Coluna esquerda -->
                <div class="col-md-6">
                    <div class="logo mb-3">
                        <img src="caminho/para/seu-logo.png" alt="Logo do site">
                    </div>
                    <div class="main-title mt-3">HOME</div>
                    <nav class="mt-4">
                        <span class="text-white">•</span>
                        <a class="nav-link d-inline" href="orcamento.html">Gerar Orçamento</a>
                        <span class="text-white">•</span>
                        <a class="nav-link d-inline" href="logout">Sair</a>
                    </nav>
                </div>

                <!-- Coluna direita (foto e nome) -->
                <div class="col-md-4 user-info">
                    <div class="user-photo-placeholder">
                        <img src="https://via.placeholder.com/200x250" alt="Foto do Usuário">
                    </div>
                    <h5 class="mt-3 fw-bold">JOSEFINO DA SILVA</h5>
                </div>
            </div>

            <!-- Histórico de orçamentos (reaproveitável dinamicamente) -->
            <div id="orcamentosToggle" class="mt-5">
                <div class="toggle-content" id="toggleContent">
                    <h3 class="mb-4">Histórico de Orçamentos</h3>

                    <ul class="lista-orcamentos" id="lista-de-orcamentos">
                        <!-- Conteúdo será incluído dinamicamente ou via backend -->
                        <%-- <jsp:include page="orcamentos.jsp" /> --%>
                    </ul>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js"></script>
    </body>
    <script>
    function toggleDetalhes(id) {
        const detalhes = document.getElementById(`detalhes-${id}`);
        if (detalhes) {
            detalhes.style.display = detalhes.style.display === 'none' || detalhes.style.display === '' ? 'block' : 'none';
        }
    }

    function excluirOrcamento(id) {
        const item = document.getElementById(id);
        if (item) item.remove();
    }
</script>

</html>
