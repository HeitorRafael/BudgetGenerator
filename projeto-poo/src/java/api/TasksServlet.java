package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import models.User;

@WebServlet("/tasks")
public class TasksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();
    private static final HashMap<String, User> users = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        BufferedReader reader = req.getReader();
        User receivedUser = gson.fromJson(reader, User.class);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        if (null == path) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"success\": false, \"message\": \"Rota não encontrada.\"}");
        } else switch (path) {
            case "/register":
                if (users.containsKey(receivedUser.getUsername())) {
                    out.print("{\"success\": false, \"message\": \"Usuário já existe.\"}");
                } else {
                    users.put(receivedUser.getUsername(), receivedUser);
                    out.print("{\"success\": true, \"message\": \"Cadastro realizado.\"}");
                }   break;
            case "/login":
                User storedUser = users.get(receivedUser.getUsername());
                if (storedUser != null && storedUser.getPassword().equals(receivedUser.getPassword())) {
                    out.print("{\"success\": true, \"message\": \"Login bem-sucedido.\"}");
                } else {
                    out.print("{\"success\": false, \"message\": \"Usuário ou senha incorretos.\"}");
                }   break;
            default:
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"success\": false, \"message\": \"Rota não encontrada.\"}");
                break;
        }

        out.flush();
    }
}
