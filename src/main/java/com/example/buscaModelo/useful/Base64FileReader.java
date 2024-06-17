package com.example.buscaModelo.useful;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Base64FileReader {
    private final String base64EncodedFile;
    private final String docType;

    public Base64FileReader(String base64EncodedFile, String docType) {
        this.base64EncodedFile = base64EncodedFile;
        this.docType = docType;
    }

    public Object[] getFileContents() throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(this.base64EncodedFile);

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes)) {
            if (this.docType.equalsIgnoreCase("xlsx")) {
                return readExcel(byteArrayInputStream);
            } else {
                throw new IllegalArgumentException("Tipo de arquivo não suportado: " + this.docType);
            }
        }
    }

    private Object[] readPdf(ByteArrayInputStream byteArrayInputStream) throws IOException {
        try (PDDocument document = PDDocument.load(byteArrayInputStream)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            int pageCount = document.getNumberOfPages();
            List<String[]> pages = new ArrayList<>();

            for (int i = 1; i <= pageCount; i++) {
                pdfStripper.setStartPage(i);
                pdfStripper.setEndPage(i);
                String pageText = pdfStripper.getText(document);
                pages.add(pageText.split("\\r?\\n"));
            }

            return pages.toArray(new String[0][]);
        }
    }

    private Object[] readExcel(ByteArrayInputStream byteArrayInputStream) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(byteArrayInputStream)) {
            List<String[][]> sheets = new ArrayList<>();

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                List<String[]> rows = new ArrayList<>();

                for (Row row : sheet) {
                    List<String> cells = new ArrayList<>();
                    for (Cell cell : row) {
                        switch (cell.getCellType()) {
                            case STRING:
                                cells.add(cell.getStringCellValue());
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    cells.add(cell.getDateCellValue().toString());
                                } else {
                                    cells.add(Double.toString(cell.getNumericCellValue()));
                                }
                                break;
                            case BOOLEAN:
                                cells.add(Boolean.toString(cell.getBooleanCellValue()));
                                break;
                            case FORMULA:
                                cells.add(cell.getCellFormula());
                                break;
                            default:
                                cells.add("");
                                break;
                        }
                    }
                    rows.add(cells.toArray(new String[0]));
                }
                sheets.add(rows.toArray(new String[0][]));
            }

            return sheets.toArray(new String[0][][]);
        }
    }
}
