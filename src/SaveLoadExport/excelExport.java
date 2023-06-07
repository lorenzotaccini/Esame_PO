package SaveLoadExport;

import TableModel.InvoicesTableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class excelExport {
    private final InvoicesTableModel model;
    private final JTable table;

    public excelExport(InvoicesTableModel model, JTable table) {
        this.model=model;
        this.table=table;
    }

    public void export() throws IOException {

        Workbook wb = new XSSFWorkbook(); //Excel workbook
        Sheet sheet = wb.createSheet(); //WorkSheet
        Row row = sheet.createRow(2); //Row created at line 3



        Row headerRow = sheet.createRow(0); //Create row at line 0
        for(int headings = 0; headings < this.model.getColumnCount(); headings++){ //For each column
            headerRow.createCell(headings).setCellValue(this.model.getColumnName(headings));//Write column name
        }

        for(int rows = 0; rows < this.model.getRowCount(); rows++){ //For each table row
            for(int cols = 0; cols < table.getColumnCount(); cols++){ //For each table column
                row.createCell(cols).setCellValue(this.model.getValueAt(rows, cols).toString()); //Write value
            }

            //Set the row to the next one in the sequence
            row = sheet.createRow((rows + 3));
        }


        wb.write(new FileOutputStream("resources\\prova.xlsx"));//Save the file


    }
}
