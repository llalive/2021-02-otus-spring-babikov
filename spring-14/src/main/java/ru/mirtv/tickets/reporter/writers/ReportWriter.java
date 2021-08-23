package ru.mirtv.tickets.reporter.writers;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import ru.mirtv.tickets.reporter.model.batch.ReportItem;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReportWriter implements ItemStreamWriter<ReportItem> {

    private XSSFWorkbook workbook;
    private WritableResource resource;
    private XSSFSheet sheet;
    private int row;
    @Value("${job.reports.outputFileName}")
    private String outputFileName;

    @SneakyThrows
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        resource = new FileSystemResource(outputFileName);
        if (resource.exists() && resource.contentLength() > 0) {
            workbook = new XSSFWorkbook(new FileInputStream(resource.getFile()));
        } else {
            workbook = new XSSFWorkbook();
        }

        sheet = workbook.createSheet();
        workbook.setActiveSheet(workbook.getSheetIndex(sheet));

        row = 0;
        createHeaderRow(sheet);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        //nothing here
    }

    @Override
    public void close() throws ItemStreamException {
        if (workbook == null) {
            return;
        }
        try (var bos = new BufferedOutputStream(resource.getOutputStream())) {
            workbook.write(bos);
            bos.flush();
            workbook.close();
        } catch (IOException ex) {
            throw new ItemStreamException("Error writing to output file", ex);
        }
        row = 0;
    }

    @Override
    public void write(List<? extends ReportItem> items) throws Exception {
        for (ReportItem o : items) {
            Row r = sheet.createRow(row++);
            Cell c = r.createCell(0);
            c.setCellValue(o.getDate());

            c = r.createCell(1);
            c.setCellValue(o.getProgram());

            c = r.createCell(2);
            c.setCellValue(o.getProjectCode());

            c = r.createCell(3);
            c.setCellValue(o.getUsageTime());

            c = r.createCell(4);
            c.setCellValue(o.getAssemblyName());
        }
    }

    private void createHeaderRow(XSSFSheet sheet) {
        XSSFCellStyle cs = getHeaderCellStyle();

        var r = sheet.createRow(row);
        r.setRowStyle(cs);

        var c = r.createCell(0);
        c.setCellValue("Дата");
        sheet.setColumnWidth(0, poiWidth(12.0));
        c = r.createCell(1);
        c.setCellValue("Наименование проекта/Наименование подразделения");
        sheet.setColumnWidth(1, poiWidth(30.0));
        c = r.createCell(2);
        c.setCellValue("Код проекта");
        sheet.setColumnWidth(2, poiWidth(12.0));
        c = r.createCell(3);
        c.setCellValue("Время использования ресурса");
        sheet.setColumnWidth(3, poiWidth(20.0));
        c = r.createCell(4);
        c.setCellValue("Идентификатор ресурса (при наличии)");
        sheet.setColumnWidth(4, poiWidth(26.0));
        row++;
    }

    private XSSFCellStyle getHeaderCellStyle() {
        var cs = workbook.createCellStyle();
        cs.setWrapText(true);
        cs.setAlignment(HorizontalAlignment.LEFT);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        cs.setFont(createHeadersFont());
        return cs;
    }

    private int poiWidth(double width) {
        return (int) Math.round(width * 256 + 200);
    }

    private Font createHeadersFont() {
        var font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setBold(Boolean.TRUE);
        return font;
    }

}
