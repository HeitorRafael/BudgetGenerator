package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import models.User;

@WebServlet("/cadastro")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();

    // Banco de dados em memória
    public static final HashMap<String, User> users = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
        BufferedReader reader = req.getReader();
        User newUser = gson.fromJson(reader, User.class);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        if (users.containsKey(newUser.getUsername())) {
            out.print("{\"success\": false, \"message\": \"Usuário já existe.\"}");
        } else {
            users.put(newUser.getUsername(), newUser);
            out.print("{\"success\": true, \"message\": \"Cadastro realizado com sucesso.\"}");
        }

        out.flush();
    } catch (IOException e) { // Isso imprimirá a stack trace no console do servidor
        // Isso imprimirá a stack trace no console do servidor
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Define o status para 500
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("{\"success\": false, \"message\": \"Erro interno no servidor.\", \"error\": \"" + e.getMessage() + "\"}");
        out.flush();
    }
}
}
