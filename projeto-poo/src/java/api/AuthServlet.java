package api;

import models.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID; // Para gerar IDs únicos

/**
 * Servlet para lidar com as operações de autenticação (login e registro).
 * Mapeado para a URL "/auth".
 */
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Armazenamento em memória para usuários (em um ambiente real, use um banco de dados)
    private static final Map<String, Usuario> Usuario = new ConcurrentHashMap<>();

    // Inicializa alguns usuários de exemplo (para testes)
    static {
        try {
            // Senha "password123" para o usuário de teste
            String hashedPassword = hashPassword("password123");
            Usuario.put("test@example.com", new Usuario("Usuário Teste", "test@example.com", "test@example.com", hashedPassword));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

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
            // Nenhuma ação especificada, redireciona de volta para a página de login
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
            if (usuario != null && usuario.getSenha().equals(password)) {
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
     */
    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("register-name");
        String email = request.getParameter("register-email");
        String password = request.getParameter("register-password");
        String confirmPassword = request.getParameter("confirm-password");

        if (name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            request.setAttribute("error", "Todos os campos são obrigatórios para o registro.");
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
            Usuario novoUsuario = new Usuario(email, password);
            usuarioDAO.inserir(novoUsuario);
            HttpSession session = request.getSession();
            session.setAttribute("usuario", novoUsuario);
            response.sendRedirect("home.jsp");
        } catch (SQLException e) {
            request.setAttribute("error", "Erro interno no servidor durante o registro.");
            request.getRequestDispatcher("index.html").forward(request, response);
        }
    }

    /**
     * Gera o hash SHA-256 de uma senha.
     * ATENÇÃO: Para produção, use bibliotecas mais robustas como BCrypt.
     */
    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Verifica se uma senha corresponde a um hash.
     * ATENÇÃO: Para produção, use bibliotecas mais robustas como BCrypt.
     */
    private static boolean verifyPassword(String rawPassword, String hashedPassword) throws NoSuchAlgorithmException {
        String hashedRawPassword = hashPassword(rawPassword);
        return hashedRawPassword.equals(hashedPassword);
    }
}