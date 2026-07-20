package com.api_food.Algaworks_Food.infra.service.report;

import com.api_food.Algaworks_Food.api.dto.output.DailySalesOutput;
import com.api_food.Algaworks_Food.domain.filter.DailySalesFilter;
import com.api_food.Algaworks_Food.domain.service.SaleReportService;
import com.api_food.Algaworks_Food.infra.service.query.SalesQueryService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfSalesReportService implements SaleReportService {

    private final SalesQueryService salesQueryService;

    private static final float MARGIN = 50f;
    private static final float START_Y = 700f;
    private static final float ROW_HEIGHT = 24f;
    private static final float[] COL_X = { MARGIN, 220f, 340f, 470f };
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public byte[] generateDailySalesReport(DailySalesFilter filter, String timeZone) {
        List<DailySalesOutput> dailySales = salesQueryService.searchDailySales(filter, timeZone);
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);
            try (PDPageContentStream c = new PDPageContentStream(doc, page)) {
                write(c, "Daily Sales Report", MARGIN, 760, Standard14Fonts.FontName.HELVETICA_BOLD, 18);
                write(c, "Date", COL_X[0], START_Y, Standard14Fonts.FontName.HELVETICA_BOLD, 12);
                write(c, "Sales", COL_X[2], START_Y, Standard14Fonts.FontName.HELVETICA_BOLD, 12);
                write(c, "Total Value", COL_X[3], START_Y, Standard14Fonts.FontName.HELVETICA_BOLD, 12);

                c.setLineWidth(1f);
                c.moveTo(MARGIN, START_Y - 6);
                c.lineTo(PDRectangle.A4.getWidth() - MARGIN, START_Y - 6);
                c.stroke();

                float y = START_Y - ROW_HEIGHT;
                for (DailySalesOutput s : dailySales) {
                    write(c, s.getDate() != null ? s.getDate().format(DATE_FORMATTER) : "-", COL_X[0], y, Standard14Fonts.FontName.HELVETICA, 11);
                    write(c, String.valueOf(s.getTotalSales()), COL_X[2], y, Standard14Fonts.FontName.HELVETICA, 11);
                    write(c, String.valueOf(s.getTotalValue()), COL_X[3], y, Standard14Fonts.FontName.HELVETICA, 11);
                    y -= ROW_HEIGHT;
                }
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            doc.save(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate daily sales PDF report", e);
        }
    }

    private void write(PDPageContentStream c, String text, float x, float y,
                       Standard14Fonts.FontName font, float size) throws IOException {
        c.setFont(new PDType1Font(font), size);
        c.beginText();
        c.newLineAtOffset(x, y);
        c.showText(text);
        c.endText();
    }
}
