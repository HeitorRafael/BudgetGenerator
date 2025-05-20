/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

/**
 *
 * @author raffinoh
 */

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import models.Usuario;

@WebServlet("/CadastroServlet")
public class CadastroServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Pegando os dados do formulário
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        // Criando o objeto de modelo
        Usuario novoUsuario = new Usuario(nome, email, usuario, senha);

        // Aqui você poderia salvar o usuário no banco de dados

        // Armazenar o usuário na sessão (opcional)
        request.getSession().setAttribute("usuario", novoUsuario);

        // Redireciona para uma página de boas-vindas ou dashboard
        response.sendRedirect("home.html");
    }
}

