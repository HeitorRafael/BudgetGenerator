/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package api;

import dao.UsuarioDAO;
import models.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;

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

        // Validação de entrada
        if (login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            request.setAttribute("erro", "Usuário e senha são obrigatórios.");
            request.getRequestDispatcher("index.html").forward(request, response);
            return;
        }

        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = usuarioDAO.buscarPorLogin(login);
            if (usuario == null) {
                request.setAttribute("erro", "Usuário ou senha inválidos.");
                request.getRequestDispatcher("index.html").forward(request, response);
                return;
            }

            // Verifica se o usuário existe e a senha confere
            if (usuario != null && BCrypt.checkpw(senha, usuario.getSenha())) {
                // Salva o usuário na sessão
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                response.sendRedirect("home.html");
            } else {
                // Login inválido
                request.setAttribute("erro", "Usuário ou senha inválidos.");
                request.getRequestDispatcher("index.html").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro interno no servidor.");
            request.getRequestDispatcher("index.html").forward(request, response);
        }
    }
}