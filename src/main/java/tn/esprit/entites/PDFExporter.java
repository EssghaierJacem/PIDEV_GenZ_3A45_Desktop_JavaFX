package tn.esprit.entites;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFExporter {

    public PDFExporter() {
    }

    public void generatePDF(TableView<User> tableView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                // Title
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, BaseColor.RED); // Red color and bigger size
                Paragraph title = new Paragraph("User List", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20); // Add spacing after title
                document.add(title);

                PdfPTable pdfTable = new PdfPTable(tableView.getColumns().size());
                addTableHeaders(pdfTable, tableView);
                addTableData(pdfTable, tableView);

                document.add(pdfTable);
                document.close();

                System.out.println("PDF generated successfully.");
            } catch (DocumentException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addTableHeaders(PdfPTable pdfTable, TableView<User> tableView) {
        tableView.getColumns().forEach(column -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(new BaseColor(0, 121, 107)); // Green color
            header.setBorderWidth(1);
            header.setPhrase(new Phrase(column.getText(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE)));
            pdfTable.addCell(header);
        });
    }

    private void addTableData(PdfPTable pdfTable, TableView<User> tableView) {
        tableView.getItems().forEach(row -> {
            tableView.getColumns().forEach(column -> {
                Object cellData = column.getCellData(row);
                String cellValue = (cellData != null) ? cellData.toString() : ""; // Add null check
                PdfPCell cell = new PdfPCell(new Phrase(cellValue));
                cell.setBorderWidth(1);
                pdfTable.addCell(cell);
            });
        });
    }
}
