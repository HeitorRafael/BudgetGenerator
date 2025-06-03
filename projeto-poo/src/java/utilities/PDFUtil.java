package utilities;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import models.orcamento;

public class PDFUtil {

    /**
     * Gera um PDF do orçamento.
     * @param orc Orçamento a ser impresso
     * @param comMarcaDagua Se true, adiciona marca d'água
     * @return Array de bytes do PDF
     */
    public static byte[] gerarPDF(orcamento orc, boolean comMarcaDagua) throws IOException, DocumentException {
        Document doc = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(doc, baos);
        doc.open();

        // Título
        doc.add(new Paragraph("Orçamento Gerado", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
        doc.add(new Paragraph(" "));

        // Dados principais
        doc.add(new Paragraph("ID: " + orc.getId()));
        doc.add(new Paragraph("Tipo: " + orc.getTipo()));
        if ("produto".equals(orc.getTipo())) {
            doc.add(new Paragraph("Produto: " + orc.getNomeProduto()));
            doc.add(new Paragraph("Materiais: " + orc.getMateriais()));
            doc.add(new Paragraph("Custo: R$" + orc.getCusto()));
            doc.add(new Paragraph("Lucro: " + orc.getLucro() + "%"));
        } else {
            doc.add(new Paragraph("Serviço: " + orc.getDescricaoServico()));
            doc.add(new Paragraph("Horas: " + orc.getHoras()));
            doc.add(new Paragraph("Valor/Hora: R$" + orc.getValorHora()));
            doc.add(new Paragraph("Custos Extras: R$" + orc.getCustosExtras()));
        }
        doc.add(new Paragraph("Valor Final: R$" + orc.getValorFinal()));
        doc.add(new Paragraph(" "));

        // Resposta da IA
        doc.add(new Paragraph("Orçamento detalhado:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        doc.add(new Paragraph(orc.getRespostaIA() != null ? orc.getRespostaIA() : "N/A"));

        // Marca d'água
        if (comMarcaDagua) {
            PdfContentByte canvas = writer.getDirectContentUnder();
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 52, Font.BOLD, new Color(200, 200, 200, 80));
            Phrase marca = new Phrase("ORÇAMENTO DEMONSTRATIVO", font);
            ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, marca, 297, 421, 45);
        }

        doc.close();
        return baos.toByteArray();
    }
}
