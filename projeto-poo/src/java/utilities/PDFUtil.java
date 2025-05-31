package utilities;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.ByteArrayOutputStream;
import javax.swing.text.Document;
import models.orcamento;

public class PDFUtil {

    /**
     * Gera um PDF do orçamento.
     * @param o Orçamento a ser impresso
     * @param comMarcaDagua Se true, adiciona marca d'água
     * @return Array de bytes do PDF
     */
    public static byte[] gerarPDF(orcamento o, boolean comMarcaDagua) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document() {};
        PdfWriter writer = PdfWriter.getInstance(doc, baos);
        doc.open();

        // Conteúdo principal
        doc.add(new Paragraph("ORÇAMENTO GERADO"));
        doc.add(new Paragraph("Descrição: " + o.toString()));
        doc.add(new Paragraph("Valor: R$" + o.valor));
        doc.add(new Paragraph("ID: " + o.getId()));

        // Marca d'água
        if (comMarcaDagua) {
            PdfContentByte canvas = writer.getDirectContentUnder();
            Font font = new Font(Font.HELVETICA, 60, Font.BOLD, new Color(200, 200, 200, 80));
            Phrase marca = new Phrase("PAGAMENTO PENDENTE", font);
            ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, marca, 297, 420, 45);
        }

        doc.close();
        return baos.toByteArray();
    }
}
