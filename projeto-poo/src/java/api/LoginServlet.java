/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package api;

import dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 *
 * @author raffinoh
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        try {
            UserDAO UserDAO = new UserDAO();
            User usuario = (User) UserDAO.buscarPorLogin(login);

            // Verifica se usuário existe e senha confere
            if (usuario != null && usuario.getSenha().equals(senha)) {
                // Salva usuário na sessão
                request.getSession().setAttribute("usuario", usuario);
                response.sendRedirect("home.html");
            } else {
                // Login inválido
                response.sendRedirect("index.html?erro=login");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.html?erro=servidor");
        }
    }
}