package utilities;

import java.io.ByteArrayOutputStream;
// REMOVA: import javax.swing.text.Document;
import models.Orcamento; // MUDEI de orcamento para Orcamento (Maiúsculo!)
import com.lowagie.text.Document; // Adicionado: O import correto para o Document do OpenPDF
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.Element;
import java.awt.Color;
import java.io.IOException; // Adicionado caso você use a exceção IOException

public class PDFUtil {

    /**
     * Gera um PDF do orçamento.
     * @param o Orçamento a ser impresso
     * @param comMarcaDagua Se true, adiciona marca d'água
     * @return Array de bytes do PDF
     * @throws java.lang.Exception
     */
    public static byte[] gerarPDF(models.Orcamento o, boolean comMarcaDagua) throws Exception { // Mudei aqui
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(); // Removido o '{}' final, como sugerido anteriormente.
        PdfWriter writer = PdfWriter.getInstance(doc, baos);
        doc.open();

        // Conteúdo principal
        doc.add(new Paragraph("ORÇAMENTO GERADO"));
        doc.add(new Paragraph("Descrição: " + o.toString()));
        doc.add(new Paragraph("Valor: R$" + o.getValorFinal())); // Предполагаю, что 'valor' - это геттер
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