HttpSession session = request.getSession(false);
if (session == null || session.getAttribute("usuario") == null) {
    response.sendRedirect("index.html?erro=autenticacao");
    return;
}