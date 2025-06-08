package api;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet responsável por realizar o logout do usuário.
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Obtém a sessão sem criar uma nova
        if (session != null) {
            session.invalidate(); // Invalida a sessão existente
        }

        // Redireciona para a página inicial com uma mensagem de sucesso
        response.sendRedirect("index.html?logout=sucesso");
    }
}