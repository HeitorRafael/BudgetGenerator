package api;

import dao.UserDAO;
import models.Usuario; // CORRIGIDO: Importa a classe de modelo Usuario

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/auth") // Mapeia o Servlet para a URL "/auth"
public class AuthServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO; // Instância do DAO

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            // Inicializa o UserDAO quando o Servlet é carregado pelo servidor
            userDAO = new UserDAO();
        } catch (RuntimeException e) {
            System.err.println("Erro crítico na inicialização do AuthServlet: " + e.getMessage());
            throw new ServletException("Falha ao inicializar o UserDAO: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action"); // Obtém o parâmetro 'action' da URL

        if (action == null || action.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Ação não especificada. Por favor, tente novamente.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        switch (action.toLowerCase()) {
            case "login":
                // Login via GET é geralmente um erro (formulários usam POST)
                request.setAttribute("errorMessage", "Login via GET não permitido. Por favor, use o formulário de login (POST).");
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);
                break;
            case "register":
                // Registro via GET também é um erro
                request.setAttribute("errorMessage", "Registro via GET não permitido. Por favor, use o formulário de cadastro (POST).");
                RequestDispatcher regDispatcher = request.getRequestDispatcher("index.jsp");
                regDispatcher.forward(request, response);
                break;
            case "logout":
                handleLogout(request, response);
                break;
            default:
                request.setAttribute("errorMessage", "Ação desconhecida. Por favor, tente novamente.");
                RequestDispatcher defaultDispatcher = request.getRequestDispatcher("index.jsp");
                defaultDispatcher.forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action"); // Obtém o parâmetro 'action' do formulário POST

        if (action == null || action.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Ação não especificada. Por favor, tente novamente.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        switch (action.toLowerCase()) {
            case "login":
                handleLogin(request, response);
                break;
            case "register":
                handleRegister(request, response);
                break;
            default:
                request.setAttribute("errorMessage", "Ação desconhecida. Por favor, tente novamente.");
                RequestDispatcher defaultDispatcher = request.getRequestDispatcher("index.jsp");
                defaultDispatcher.forward(request, response);
                break;
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("login-email"); // Nome do campo do formulário de login
        String password = request.getParameter("login-password"); // Nome do campo da senha de login

        // Validação de entrada
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Email e senha são obrigatórios para o login.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        try {
            // Usa o método getUserByEmail do UserDAO que busca pelo email e retorna um objeto Usuario
            Usuario user = userDAO.getUserByEmail(email); // CORRIGIDO: Declaração e uso de 'Usuario'

            // Verifica se o usuário existe e se a senha confere usando BCrypt
            if (user != null && userDAO.checkPassword(password, user.getSenha())) {
                // Login bem-sucedido: salva o usuário na sessão
                HttpSession session = request.getSession();
                session.setAttribute("loggedInUser", user); // CORRIGIDO: Armazena 'Usuario' na sessão
                response.sendRedirect("home.jsp"); // Redireciona para a página principal
            } else {
                // Login inválido (usuário não encontrado ou senha incorreta)
                request.setAttribute("errorMessage", "Email ou senha inválidos. Tente novamente.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime o stack trace completo para depuração no console do servidor
            System.err.println("SQL Exception during login: " + e.getMessage());

            String userMessage = "Ocorreu um erro no banco de dados ao tentar realizar o login.";
            if (e.getMessage() != null && e.getMessage().contains("No suitable driver found")) {
                 userMessage = "Erro crítico: O driver do banco de dados não foi encontrado. Contate o suporte.";
            } else if (e.getMessage() != null && e.getMessage().contains("database is locked")) {
                userMessage = "O banco de dados está temporariamente ocupado. Por favor, tente novamente em instantes.";
            } else if (e.getMessage() != null && e.getMessage().contains("no such column") || e.getMessage().contains("no such table")) {
                 userMessage = "Erro interno: Problema com a estrutura do banco de dados (tabela/coluna). Contate o suporte.";
            }
            request.setAttribute("errorMessage", userMessage);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // Imprime o stack trace para depuração
            System.err.println("Unexpected Exception during login: " + e.getMessage());
            request.setAttribute("errorMessage", "Ocorreu um erro inesperado ao tentar realizar o login.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("register-name"); // Nome do campo do formulário de registro
        String email = request.getParameter("register-email");
        String password = request.getParameter("register-password");
        String confirmPassword = request.getParameter("confirm-password");

        // Validação de entrada
        if (name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Todos os campos de cadastro são obrigatórios.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Verifica se as senhas coincidem
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "As senhas digitadas não coincidem. Por favor, verifique.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Cria um novo objeto Usuario com os dados do formulário
        Usuario newUser = new Usuario(name, email, password); // CORRIGIDO: Cria uma instância de 'Usuario'

        try {
            boolean registered = userDAO.registerUser(newUser); // Tenta registrar o usuário

            if (registered) {
                // Registro bem-sucedido: salva o novo usuário na sessão
                HttpSession session = request.getSession();
                session.setAttribute("loggedInUser", newUser); // CORRIGIDO: Armazena 'Usuario' na sessão
                response.sendRedirect("home.jsp"); // Redireciona para a página principal
            } else {
                // Registro falhou por algum motivo desconhecido (não email duplicado)
                request.setAttribute("errorMessage", "Não foi possível completar o registro. Tente novamente.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime o stack trace para depuração
            System.err.println("SQL Exception during registration: " + e.getMessage());

            String userMessage = "Ocorreu um erro no banco de dados ao tentar realizar o cadastro.";
            // Mensagem específica para email duplicado (baseado na UNIQUE constraint da tabela 'users')
            if (e.getMessage() != null && e.getMessage().contains("UNIQUE constraint failed: users.email")) {
                userMessage = "Este email já está cadastrado. Por favor, utilize outro email."; //
            } else if (e.getMessage() != null && e.getMessage().contains("No suitable driver found")) {
                 userMessage = "Erro crítico: O driver do banco de dados não foi encontrado. Contate o suporte.";
            } else if (e.getMessage() != null && e.getMessage().contains("no such column") || e.getMessage().contains("no such table")) {
                 userMessage = "Erro interno: Problema com a estrutura do banco de dados (tabela/coluna). Contate o suporte.";
            }
            request.setAttribute("errorMessage", userMessage);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // Imprime o stack trace para depuração
            System.err.println("Unexpected Exception during registration: " + e.getMessage());
            request.setAttribute("errorMessage", "Ocorreu um erro inesperado ao tentar realizar o cadastro.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Obtém a sessão sem criá-la se não existir
        if (session != null) {
            session.invalidate(); // Invalida a sessão (remove todos os atributos)
        }
        response.sendRedirect("index.jsp?message=logout_successful"); // Redireciona para a página de login
    }
}