//package api;
//
//import dao.UserDAO;
//import models.Usuario; // Importe sua classe de modelo Usuario
//// import models.User; // Remova este import se não for usar
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//import java.sql.SQLException; // Importe para tratamento de exceções de SQL
//// import java.util.UUID; // Não é mais necessário para ID AUTOINCREMENT
//
//@WebServlet("/CadastroServlet") // Anotação para mapeamento
//public class CadastroServlet extends HttpServlet {
//
//    private UserDAO userDAO; // Declare a instância do DAO a nível de classe
//
//    @Override
//    public void init() throws ServletException {
//        super.init();
//        userDAO = new UserDAO(); // Instancie o DAO uma única vez na inicialização do Servlet
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setCharacterEncoding("UTF-8"); // Garante que caracteres especiais sejam tratados corretamente
//
//        // Obtenha os parâmetros do formulário de cadastro
//        // É CRUCIAL que o formulário HTML que aponta para /CadastroServlet
//        // tenha campos com os nomes "register-name", "register-email" e "register-password".
//        String nome = request.getParameter("register-name");
//        String email = request.getParameter("register-email");
//        String senha = request.getParameter("register-password");
//        String confirmPassword = request.getParameter("confirm-password"); // Se houver confirmação de senha no formulário
//
//        // Validação básica: verificar se os campos não são nulos/vazios
//        if (nome == null || nome.trim().isEmpty() ||
//            email == null || email.trim().isEmpty() ||
//            senha == null || senha.trim().isEmpty()) {
//            request.setAttribute("errorMessage", "Todos os campos de cadastro são obrigatórios.");
//            request.getRequestDispatcher("index.jsp").forward(request, response);
//            return;
//        }
//
//        // Validação de confirmação de senha
//        if (confirmPassword != null && !senha.equals(confirmPassword)) {
//            request.setAttribute("errorMessage", "As senhas não coincidem.");
//            request.getRequestDispatcher("index.jsp").forward(request, response);
//            return;
//        }
//
//        // Crie um objeto Usuario (seu modelo correto)
//        Usuario novoUsuario = new Usuario(nome, email, senha); // O ID será gerado automaticamente pelo DB
//
//        try {
//            // Chame o método registerUser do UserDAO para salvar no banco de dados
//            boolean registered = userDAO.registerUser(novoUsuario);
//
//            if (registered) {
//                // Cadastro bem-sucedido
//                // Opcional: fazer login automático após o cadastro
//                HttpSession session = request.getSession();
//                session.setAttribute("loggedInUser", novoUsuario); // Armazena o objeto Usuario na sessão
//
//                response.sendRedirect("home.jsp"); // Redireciona para home.jsp
//            } else {
//                // Caso registerUser retorne false por algum motivo inesperado (não é comum com throw SQLException)
//                request.setAttribute("errorMessage", "Erro ao cadastrar usuário. Tente novamente.");
//                request.getRequestDispatcher("index.jsp").forward(request, response);
//            }
//        }catch (Exception e) {
//            e.printStackTrace(); // Captura outras exceções inesperadas
//            request.setAttribute("errorMessage", "Ocorreu um erro inesperado durante o cadastro.");
//            request.getRequestDispatcher("index.jsp").forward(request, response);
//        }
//        // Imprime o stack trace no console do servidor para depuração
//        // Verifique mensagens específicas de erro do banco de dados (ex: email já existe)
//        
//    }
//}