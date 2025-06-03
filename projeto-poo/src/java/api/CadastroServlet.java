/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import dao.UsuarioDAO;
import models.Usuario;

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
        String login = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        Usuario novoUsuario = new Usuario(login, senha);
        novoUsuario.setId(UUID.randomUUID().toString());

        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.inserir(novoUsuario);
            request.getSession().setAttribute("usuario", novoUsuario);
            response.sendRedirect("home.html");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.html?erro=cadastro");
        }
    }
}

