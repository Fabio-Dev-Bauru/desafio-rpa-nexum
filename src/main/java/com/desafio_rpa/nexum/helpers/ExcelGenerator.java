package com.desafio_rpa.nexum.helpers;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {
    public static void exportToExcel(String excelFilePath, List<List<String>> dataSheet1, List<List<String>> dataSheet2) {
        try (Workbook workbook = WorkbookFactory.create(true)) {
            Sheet sheet1 = workbook.createSheet("MercadoLivre");

            int rowNum = 0;
            for (List<String> rowData : dataSheet1) {
                Row row = sheet1.createRow(rowNum++);
                int colNum = 0;
                for (String value : rowData) {
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(value);
                }
            }

            Sheet sheet2 = workbook.createSheet("CasasBahia");

            rowNum = 0;
            for (List<String> rowData : dataSheet2) {
                Row row = sheet2.createRow(rowNum++);
                int colNum = 0;
                for (String value : rowData) {
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue(value);
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(new File(excelFilePath))) {
                workbook.write(outputStream);
                System.out.println("Planilha gerada em: " + excelFilePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Arquivo não encontrado!");
            }

        } catch (IOException | EncryptedDocumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
