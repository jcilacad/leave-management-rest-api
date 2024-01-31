package com.api.management.leave.leavemanagementapi.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.api.management.leave.leavemanagementapi.entity.Leave;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static com.api.management.leave.leavemanagementapi.constants.AppConstants.EXCEL_HEADERS;
import static com.api.management.leave.leavemanagementapi.constants.AppConstants.EXCEL_SHEET;

public class ExcelHelper {

    public static ByteArrayInputStream leavesToExcel(List<Leave> leaves) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(EXCEL_SHEET);
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < EXCEL_HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(EXCEL_HEADERS[col]);
            }
            int rowIdx = 1;
            for (Leave leave : leaves) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(leave.getId());
                row.createCell(1).setCellValue(leave.getLeaveType());
                row.createCell(2).setCellValue(leave.getAppliedFrom());
                row.createCell(3).setCellValue(leave.getAppliedTo());
                row.createCell(4).setCellValue(leave.getDaysRequested() == null ? "" : leave.getDaysRequested().toString());
                row.createCell(5).setCellValue(leave.getVacationLeave() == null ? "" : leave.getVacationLeave().toString());
                row.createCell(6).setCellValue(leave.getSickLeave() == null ? "" : leave.getSickLeave().toString());
                row.createCell(7).setCellValue(leave.getSpecialPrivilegeLeave() == null ? "" : leave.getSpecialPrivilegeLeave().toString());
                row.createCell(8).setCellValue(leave.getForcedLeave() == null ? "" : leave.getForcedLeave().toString());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("failed to import data to Excel file: " + e.getMessage());
        }
    }
}
