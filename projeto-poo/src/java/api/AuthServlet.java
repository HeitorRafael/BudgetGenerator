package api;

import dao.UserDAO;
import models.Usuario; // Importa a classe de modelo Usuario

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException; // Importa SQLException para tratamento de erros de banco de dados
// import java.time.LocalDateTime; // Não é diretamente usado aqui, mas o Usuario o usa

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO; // Instância do DAO para interagir com o banco de dados

    @Override
    public void init() throws ServletException {
        super.init();
        // Inicializa o UserDAO quando o Servlet é carregado pelo servidor.
        // O construtor do UserDAO já cuida de carregar o driver JDBC e criar a tabela.
        try {
            userDAO = new UserDAO();
        } catch (RuntimeException e) {
            // Captura a RuntimeException lançada pelo UserDAO se o driver não for encontrado
            System.err.println("Erro crítico na inicialização do AuthServlet: " + e.getMessage());
            // Uma ServletException aqui impede o Servlet de ser carregado.
            throw new ServletException("Falha ao inicializar o UserDAO: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8"); // Define o encoding para garantir caracteres especiais

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Ação não especificada. Por favor, tente novamente.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        switch (action.toLowerCase()) { // Usar toLowerCase para maior robustez
            case "login":
                handleLogin(request, response);
                break;
            case "register":
                // Desencorajar registro via GET por segurança (senhas em URLs não são seguras)
                request.setAttribute("errorMessage", "Registro via GET não permitido. Por favor, use o formulário de cadastro (POST).");
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);
                break;
            case "logout": // Adicionando funcionalidade de logout
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
        response.setCharacterEncoding("UTF-8"); // Define o encoding para garantir caracteres especiais

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Ação não especificada. Por favor, tente novamente.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        switch (action.toLowerCase()) { // Usar toLowerCase para maior robustez
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

    /**
     * Lida com as requisições de login.
     * Tenta autenticar o usuário e, se bem-sucedido, redireciona para 'home.jsp'.
     * Caso contrário, retorna para 'index.jsp' com uma mensagem de erro.
     */
    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("login-email");
        String password = request.getParameter("login-password");

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Email e senha são obrigatórios para o login.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        try {
            Usuario user = userDAO.getUserByEmail(email); // Busca o usuário pelo email

            // Verifica se o usuário existe e se a senha corresponde (usando BCrypt)
            if (user != null && userDAO.checkPassword(password, user.getSenha())) {
                // Login bem-sucedido
                HttpSession session = request.getSession();
                session.setAttribute("loggedInUser", user); // Armazena o objeto Usuario na sessão
                response.sendRedirect("home.jsp"); // Redireciona para a página principal
            } else {
                // Credenciais inválidas
                request.setAttribute("errorMessage", "Email ou senha inválidos. Tente novamente.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            // Erro de banco de dados (conexão, query, etc.)
            e.printStackTrace(); // Imprime o stack trace completo para depuração no console do servidor
            System.err.println("SQL Exception during login: " + e.getMessage()); // Log mais específico

            String userMessage = "Ocorreu um erro no banco de dados ao tentar realizar o login.";
            if (e.getMessage() != null && e.getMessage().contains("No suitable driver found")) {
                 userMessage = "Erro crítico: O driver do banco de dados não foi encontrado. Contate o suporte.";
            } else if (e.getMessage() != null && e.getMessage().contains("database is locked")) {
                userMessage = "O banco de dados está temporariamente ocupado. Por favor, tente novamente em instantes.";
            }
            request.setAttribute("errorMessage", userMessage);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            // Outras exceções inesperadas
            e.printStackTrace(); // Imprime o stack trace para depuração
            System.err.println("Unexpected Exception during login: " + e.getMessage());
            request.setAttribute("errorMessage", "Ocorreu um erro inesperado ao tentar realizar o login.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    /**
     * Lida com as requisições de registro de um novo usuário.
     * Tenta registrar o usuário e, se bem-sucedido, redireciona para 'home.jsp' (e faz login automático).
     * Caso contrário, retorna para 'index.jsp' com uma mensagem de erro.
     */
    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("register-name");
        String email = request.getParameter("register-email");
        String password = request.getParameter("register-password");
        String confirmPassword = request.getParameter("confirm-password"); // Campo de confirmação de senha

        // Validação básica de entrada
        if (name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Todos os campos de cadastro são obrigatórios.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Validação de correspondência de senhas
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "As senhas digitadas não coincidem. Por favor, verifique.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Cria um novo objeto Usuario com os dados fornecidos pelo formulário
        Usuario newUser = new Usuario(name, email, password); // ID é auto-gerado pelo DB

        try {
            // Tenta registrar o novo usuário no banco de dados através do DAO
            boolean registered = userDAO.registerUser(newUser);

            if (registered) {
                // Registro bem-sucedido: Armazena o usuário na sessão e redireciona
                HttpSession session = request.getSession();
                session.setAttribute("loggedInUser", newUser); // Opcional: fazer login automático
                response.sendRedirect("home.jsp");
            } else {
                // Caso o DAO retorne false sem lançar exceção (menos comum se o DAO lançar SQLException)
                request.setAttribute("errorMessage", "Não foi possível completar o registro. Tente novamente.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            // Erro de banco de dados durante o registro
            e.printStackTrace(); // Imprime stack trace para depuração no console
            System.err.println("SQL Exception during registration: " + e.getMessage());

            String userMessage = "Ocorreu um erro no banco de dados ao tentar realizar o cadastro.";
            if (e.getMessage() != null && e.getMessage().contains("O email já está em uso.")) { // Mensagem personalizada do DAO
                userMessage = "Este email já está cadastrado. Por favor, utilize outro email.";
            } else if (e.getMessage() != null && e.getMessage().contains("No suitable driver found")) {
                 userMessage = "Erro crítico: O driver do banco de dados não foi encontrado. Contate o suporte.";
            }
            request.setAttribute("errorMessage", userMessage);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            // Outras exceções inesperadas durante o registro
            e.printStackTrace();
            System.err.println("Unexpected Exception during registration: " + e.getMessage());
            request.setAttribute("errorMessage", "Ocorreu um erro inesperado ao tentar realizar o cadastro.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    /**
     * Lida com a requisição de logout.
     * Invalida a sessão do usuário e redireciona para 'index.jsp'.
     */
    private void handleLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Não cria uma nova sessão se não existir
        if (session != null) {
            session.invalidate(); // Invalida a sessão, removendo todos os atributos
        }
        // Redireciona para a página inicial (login) após o logout
        response.sendRedirect("index.jsp?message=logout_successful");
    }
}