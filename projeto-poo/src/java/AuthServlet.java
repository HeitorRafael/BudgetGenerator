import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "login":
                    handleLogin(request, response);
                    break;
                case "register":
                    handleRegistration(request, response);
                    break;
                default:
                    response.getWriter().println("Ação inválida.");
                    break;
            }
        } else {
            response.getWriter().println("Nenhuma ação especificada.");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("login-email");
        String password = request.getParameter("login-password");

        // *** AQUI ENTRA SUA LÓGICA DE AUTENTICAÇÃO ***
        // Consultar o banco de dados para verificar o email e a senha
        // Se as credenciais forem válidas, você pode definir uma sessão, cookies, etc.

        if ("teste@email.com".equals(email) && "senha123".equals(password)) {
            response.getWriter().println("Login bem-sucedido!");
            // Redirecionar para a página principal, por exemplo
            // response.sendRedirect("pagina_principal.jsp");
        } else {
            response.getWriter().println("Falha no login: email ou senha incorretos.");
            // Você pode redirecionar de volta para a página de login com uma mensagem de erro
            // request.setAttribute("erro", "Email ou senha incorretos.");
            // request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("register-name");
        String email = request.getParameter("register-email");
        String password = request.getParameter("register-password");
        String confirmPassword = request.getParameter("confirm-password");

        // *** AQUI ENTRA SUA LÓGICA DE REGISTRO ***
        // Validar os dados (por exemplo, se as senhas coincidem)
        // Verificar se o email já existe no banco de dados
        // Se tudo estiver ok, inserir os dados do novo usuário no banco de dados

        if (!password.equals(confirmPassword)) {
            response.getWriter().println("Falha no registro: as senhas não coincidem.");
            return;
        }

        // Exemplo de registro bem-sucedido (você precisará implementar a interação com o banco de dados)
        response.getWriter().println("Registro bem-sucedido para: " + name + " (" + email + ")");
        // Você pode redirecionar para a página de login ou para uma página de sucesso
        // response.sendRedirect("login.jsp?registro=sucesso");
    }
}