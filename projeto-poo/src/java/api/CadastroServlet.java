/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/CadastroServlet")
public class CadastroServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        User novoUsuario = new User(usuario, senha);
        novoUsuario.setId(UUID.randomUUID().toString());

        try {
            UserDAO UserDAO = new UserDAO();
            UserDAO.inserir(novoUsuario);
            request.getSession().setAttribute("usuario", novoUsuario);
            response.sendRedirect("home.html");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.html?erro=cadastro");
        }
    }
}

