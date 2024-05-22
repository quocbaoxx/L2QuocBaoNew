package com.exampledeliverynew.deliverynew.export;


import com.exampledeliverynew.deliverynew.dto.LocationResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelExporter {

    public ByteArrayInputStream locationResultsToExcel(List<LocationResult> locationResults) throws IOException {
        String[] columns = {"Location ID", "Province Name", "District Name", "Subdistrict Name", "Warehouse ID",
                "Warehouse Name", "LM ID", "LM Name", "LM Type", "FFM ID", "FFM Name", "FFM Type"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Location Results");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowNum = 1;
            for (LocationResult locationResult : locationResults) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(locationResult.getLocationId());
                row.createCell(1).setCellValue(locationResult.getProvinceName());
                row.createCell(2).setCellValue(locationResult.getDistrictName());
                row.createCell(3).setCellValue(locationResult.getSubdistrictName());
                row.createCell(4).setCellValue(locationResult.getWarehouseId());
                row.createCell(5).setCellValue(locationResult.getWarehouseName());
                row.createCell(6).setCellValue(locationResult.getLmId());
                row.createCell(7).setCellValue(locationResult.getLmName());
                row.createCell(8).setCellValue(locationResult.getLmType());
                row.createCell(9).setCellValue(locationResult.getFfmId());
                row.createCell(10).setCellValue(locationResult.getFfmName());
                row.createCell(11).setCellValue(locationResult.getFfmType());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
