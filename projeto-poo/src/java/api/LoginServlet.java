/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package api;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 *
 * @author raffinoh
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        // Aqui você deveria consultar no banco de dados, mas vamos simular:
        if ("admin".equals(usuario) && "1234".equals(senha)) {
            request.getSession().setAttribute("usuario", usuario);
            response.sendRedirect("home.html");
        } else {
            response.sendRedirect("index.html?erro=login");
        }
    }
}