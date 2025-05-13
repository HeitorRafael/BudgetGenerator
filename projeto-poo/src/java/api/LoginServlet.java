package api;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.*;
import models.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        BufferedReader reader = req.getReader();
        User user = gson.fromJson(reader, User.class);

        // Lógica fictícia
        boolean loginValido = "admin".equals(user.getUsername()) && "123".equals(user.getPassword());

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print("{\"success\": " + loginValido + "}");
        out.flush();
    }
}
