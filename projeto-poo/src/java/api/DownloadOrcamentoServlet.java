package api;

import dao.OrcamentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Orcamento;
import utilities.PDFUtil;

import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/DownloadOrcamento")
public class DownloadOrcamentoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orcamentoId = request.getParameter("id");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("index.html");
            return;
        }
        User usuario = (User) session.getAttribute("usuario");

        // Busque o orçamento do banco (ajuste se necessário)
        OrcamentoDAO dao = new OrcamentoDAO();
        Orcamento o = null;
        try {
            for (Orcamento item : dao.listarPorUsuario(usuario.getId())) {
                if (item.getId().equals(orcamentoId)) {
                    o = item;
                    break;
                }
            }
            if (o == null) {
                response.sendError(404, "Orçamento não encontrado.");
                return;
            }

            // Gera o PDF
            byte[] pdf = PDFUtil.gerarPDF(o, o.isComMarcaDagua());

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=orcamento.pdf");
            response.setContentLength(pdf.length);

            OutputStream out = response.getOutputStream();
            out.write(pdf);
            out.flush();
        } catch (Exception e) {
            response.sendError(500, "Erro ao gerar PDF: " + e.getMessage());
        }
    }
}
