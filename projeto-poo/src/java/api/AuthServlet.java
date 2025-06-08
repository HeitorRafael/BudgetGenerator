package api; // Ajuste o pacote conforme sua estrutura

import dao.UserDAO;

import jakarta.servlet.RequestDispatcher; // Mudou de javax.servlet para jakarta.servlet
import jakarta.servlet.ServletException;   // Mudou de javax.servlet.ServletException
import jakarta.servlet.http.HttpServlet;   // Mudou de javax.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest; // Mudou de javax.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse; // Mudou de javax.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpSession;     // Mudou de javax.servlet.http.HttpSession
import java.io.IOException;

// A anotação @WebServlet (jakarta.servlet.annotation.WebServlet) também mudaria,
// mas como estamos focando em Java com Ant, vamos manter a configuração via web.xml.
// import jakarta.servlet.annotation.WebServlet;

public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Um ID de serialização

    private UserDAO userDAO;

    // Método init() é chamado uma vez quando o Servlet é inicializado
    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO(); // Instancia o DAO
        // O DAO já chama createTable() no seu construtor, então o DB será inicializado
    }

    // Lida com requisições POST (formulários HTML geralmente usam POST para login/cadastro)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Garante que caracteres especiais sejam tratados corretamente
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("errorMessage", "Ação inválida. Por favor, tente novamente.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
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
                request.setAttribute("errorMessage", "Ação desconhecida. Por favor, tente novamente.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request, response);
                break;
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("login-email");
        String password = request.getParameter("login-password");

        User user = userDAO.getUserByEmail(email);

        if (user != null && userDAO.checkPassword(password, user.getPassword())) {
            // Login bem-sucedido
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", user); // Armazena o objeto User na sessão
            response.sendRedirect("home.jsp"); // Redireciona para home.jsp
        } else {
            // Login falhou
            request.setAttribute("errorMessage", "Email ou senha inválidos. Tente novamente.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("register-name");
        String email = request.getParameter("register-email");
        String password = request.getParameter("register-password");
        String confirmPassword = request.getParameter("confirm-password");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "As senhas não coincidem.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        User newUser = new User(name, email, password);
        boolean registered = userDAO.registerUser(newUser);

        if (registered) {
            // Registro bem-sucedido
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", newUser);
            response.sendRedirect("home.jsp");
        } else {
            // Registro falhou (ex: email já existe)
            request.setAttribute("errorMessage", "Erro ao registrar. O email pode já estar em uso.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }
}