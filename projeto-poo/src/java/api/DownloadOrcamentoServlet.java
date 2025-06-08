package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.orcamento;
import utilities.PDFUtil;
import dao.OrcamentoDAO;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/DownloadOrcamento")
public class DownloadOrcamentoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orcamentoId = req.getParameter("id");
        if (orcamentoId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do orçamento não informado.");
            return;
        }

        // Busque o orçamento do banco (ajuste conforme seu DAO)
        orcamento orc = OrcamentoDAO.buscarPorId(orcamentoId);
        if (orc == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Orçamento não encontrado.");
            return;
        }

        boolean comMarcaDagua = orc.isComMarcaDagua();

        try {
            byte[] pdf = PDFUtil.gerarPDF(orc, comMarcaDagua);
            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=orcamento_" + orcamentoId + ".pdf");
            resp.setContentLength(pdf.length);
            OutputStream os = resp.getOutputStream();
            os.write(pdf);
            os.flush();
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar PDF.");
        }
    }
}
