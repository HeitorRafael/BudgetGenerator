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
import java.io.PrintWriter;
import java.sql.SQLException;

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

// ...existing code...
try {
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    Usuario usuario = usuarioDAO.buscarPorLogin(login);

    if (usuario != null) {
        // Usa getSenha() para conferir a senha
        if (usuario.getSenha().equals(senha)) {
            // Login bem-sucedido
            request.getSession().setAttribute("usuario", usuario);

            // Mensagem de login sucedido
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<html><body>");
                out.println("<h2>Login realizado com sucesso!</h2>");
                out.println("<p>Bem-vindo, " + usuario.getNome() + "!</p>");
                out.println("<a href='home.html'>Ir para a Home</a>");
                out.println("</body></html>");
            }
        } else {
            // Senha incorreta
            response.sendRedirect("index.html?erro=senha");
        }
    } else {
        // Usuário não encontrado
        response.sendRedirect("index.html?erro=usuario");
    }
} catch (IOException | SQLException e) {
    response.sendRedirect("index.html?erro=servidor");
}
// ...existing code...
    }
}