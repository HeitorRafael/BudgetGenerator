//package api;
//
//import dao.UserDAO;
//import models.Usuario; // Importa a classe de modelo Usuario para consistência
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//import java.sql.SQLException; // Importa SQLException para tratamento de erros de banco de dados
//
///**
// * Servlet para lidar com o processo de login de usuários.
// * NOTA: É ALTAMENTE RECOMENDADO CONSOLIDAR ESTA FUNCIONALIDADE DENTRO DO AuthServlet
// * para evitar redundância e simplificar o gerenciamento de autenticação.
// */
//@WebServlet("/LoginServlet") // Anotação para mapeamento URL
//public class LoginServlet extends HttpServlet {
//
//    private static final long serialVersionUID = 1L;
//    private UserDAO userDAO; // Instância do DAO para interagir com o banco de dados
//
//    @Override
//    public void init() throws ServletException {
//        super.init();
//        // Inicializa o UserDAO quando o Servlet é carregado pelo servidor.
//        try {
//            userDAO = new UserDAO();
//        } catch (RuntimeException e) {
//            System.err.println("Erro crítico na inicialização do LoginServlet: " + e.getMessage());
//            throw new ServletException("Falha ao inicializar o UserDAO no LoginServlet", e);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setCharacterEncoding("UTF-8");
//
//        String email = request.getParameter("email"); // Supondo que o campo de login seja 'email'
//        String senha = request.getParameter("senha"); // Supondo que o campo de senha seja 'senha'
//
//        // Validação básica: verificar se os campos não são nulos/vazios
//        if (email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
//            request.setAttribute("errorMessage", "Email e senha são obrigatórios para o login.");
//            request.getRequestDispatcher("index.jsp").forward(request, response);
//            return;
//        }
//
//        try {
//            // Usa o método getUserByEmail do UserDAO que busca pelo email e retorna um objeto Usuario
//            Usuario usuario = userDAO.getUserByEmail(email);
//
//            // Verifica se o usuário existe e se a senha confere usando BCrypt
//            if (usuario != null && userDAO.checkPassword(senha, usuario.getSenha())) {
//                // Login bem-sucedido
//                HttpSession session = request.getSession();
//                session.setAttribute("loggedInUser", usuario); // Armazena o objeto Usuario na sessão
//                response.sendRedirect("home.jsp"); // Redireciona para a página principal
//            } else {
//                // Login inválido (usuário não encontrado ou senha incorreta)
//                request.setAttribute("errorMessage", "Email ou senha inválidos. Tente novamente.");
//                request.getRequestDispatcher("index.jsp").forward(request, response);
//            }
//        } catch (SQLException e) {
//            // Erro de banco de dados (conexão, query, etc.)
//            e.printStackTrace(); // Imprime o stack trace completo para depuração no console do servidor
//            System.err.println("SQL Exception during login in LoginServlet: " + e.getMessage());
//
//            String userMessage = "Ocorreu um erro no banco de dados ao tentar realizar o login.";
//            if (e.getMessage() != null && e.getMessage().contains("No suitable driver found")) {
//                 userMessage = "Erro crítico: O driver do banco de dados não foi encontrado. Contate o suporte.";
//            } else if (e.getMessage() != null && e.getMessage().contains("database is locked")) {
//                userMessage = "O banco de dados está temporariamente ocupado. Por favor, tente novamente em instantes.";
//            }
//            request.setAttribute("errorMessage", userMessage);
//            request.getRequestDispatcher("index.jsp").forward(request, response);
//        } catch (Exception e) {
//            // Outras exceções inesperadas
//            e.printStackTrace(); // Imprime o stack trace para depuração
//            System.err.println("Unexpected Exception during login in LoginServlet: " + e.getMessage());
//            request.setAttribute("errorMessage", "Ocorreu um erro inesperado ao tentar realizar o login.");
//            request.getRequestDispatcher("index.jsp").forward(request, response);
//        }
//    }
//}