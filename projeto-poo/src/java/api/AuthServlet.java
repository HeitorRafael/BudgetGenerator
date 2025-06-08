package api;

import models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dao.UsuarioDAO;

import java.util.UUID; // Para gerar IDs únicos
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Servlet para lidar com as operações de autenticação (login e registro).
 * Mapeado para a URL "/auth".
 */
@WebServlet(name = "AuthServlet", urlPatterns = {"/auth"})
public class AuthServlet extends HttpServlet {

    /**
     * Lida com as requisições POST para login e registro.
     * @param request
     * @param response
     * @throws jakarta.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("error", "Ação inválida.");
            request.getRequestDispatcher("index.html").forward(request, response);
            return;
        }

        switch (action) {
            case "login":
                handleLogin(request, response);
                break;
            case "register":
                handleRegister(request, response);
                break;
            default:
                request.setAttribute("error", "Ação desconhecida.");
                request.getRequestDispatcher("index.html").forward(request, response);
                break;
        }
    }

    /**
     * Processa a requisição de login.
     */
    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("login-email");
        String password = request.getParameter("login-password");

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Email e senha são obrigatórios para o login.");
            request.getRequestDispatcher("index.html").forward(request, response);
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            Usuario usuario = usuarioDAO.buscarPorEmail(email);
            if (usuario != null && verifyPassword(password, usuario.getSenha())) {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                response.sendRedirect("home.jsp");
                return;
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Erro interno no servidor durante o login.");
            request.getRequestDispatcher("index.html").forward(request, response);
            return;
        }

        request.setAttribute("error", "Email ou senha inválidos.");
        request.getRequestDispatcher("index.html").forward(request, response);
    }

    /**
     * Processa a requisição de registro.
     * Valida os dados, insere no banco e redireciona corretamente.
     */
    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("register-name");
        String email = request.getParameter("register-email");
        String password = request.getParameter("register-password");
        String confirmPassword = request.getParameter("confirm-password");

        // Validação dos dados do formulário
        if (name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            request.setAttribute("error", "Todos os campos são obrigatórios.");
            request.getRequestDispatcher("index.html").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "As senhas não coincidem.");
            request.getRequestDispatcher("index.html").forward(request, response);
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            if (usuarioDAO.buscarPorEmail(email) != null) {
                request.setAttribute("error", "Este email já está registrado.");
                request.getRequestDispatcher("index.html").forward(request, response);
                return;
            }

            // Gera o hash da senha antes de salvar
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            Usuario novoUsuario = new Usuario(name, email, hashedPassword);
            usuarioDAO.inserir(novoUsuario);

            // Cria sessão e redireciona para home.jsp após registro bem-sucedido
            HttpSession session = request.getSession();
            session.setAttribute("usuario", novoUsuario);
            response.sendRedirect("home.jsp");
        } catch (SQLException e) {
            request.setAttribute("error", "Erro interno no servidor durante o registro.");
            request.getRequestDispatcher("index.html").forward(request, response);
        }
    }

    /*
     Gera o hash da senha usando BCrypt.
     */
    private static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /*
      Verifica se uma senha corresponde a um hash usando BCrypt.
     */
    private static boolean verifyPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
