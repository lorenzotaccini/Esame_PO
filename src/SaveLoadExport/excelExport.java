package SaveLoadExport;

import TableModel.InvoicesTableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Classe che implementa l'esportazione del contenuto della tabella su file .xlsx
 */
public class excelExport {
    private final InvoicesTableModel model;


    public excelExport(InvoicesTableModel model) {
        this.model=model;
    }

    /**
     * Esportazione del contenuto della tabella su file Excel (.xlsx) tramite l'utilizzo delle librerie Apache POI
     *
     * @throws IOException the io exception
     */
    public void export() throws IOException {

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter xlsx = new FileNameExtensionFilter("Excel Spreadsheet File (*.xlsx)", "xlsx");
        String extension = ".xlsx";
        fileChooser.addChoosableFileFilter(xlsx);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setCurrentDirectory(new File("./Excel_exports"));
        fileChooser.setDialogTitle("Export table in Excel format");
        int userSelection = fileChooser.showSaveDialog(null);

        if(userSelection==JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            if(!filePath.endsWith(extension)) {
                file = new File(filePath + extension);
            }

            Workbook wb = new XSSFWorkbook(); //Excel workbook
            Sheet sheet = wb.createSheet("Invoices"); //WorkSheet
            Row row = sheet.createRow(2); //riga iniziale per i dati a partire dalla terza

            Row headerRow = sheet.createRow(0); //riga a linea 0 per i titoli
            for(int headings = 0; headings < (this.model.getColumnCount()-1); headings++){ //For each column
                headerRow.createCell(headings).setCellValue(this.model.getColumnName(headings));//Write column name
            }

            for(int rows = 0; rows < this.model.getRowCount(); rows++){ //For each table row
                for(int cols = 0; cols < (this.model.getColumnCount()-1); cols++){ //For each table column
                    row.createCell(cols).setCellValue(this.model.getValueAt(rows, cols).toString()); //Write value
                }

                //Set the row to the next one in the sequence
                row = sheet.createRow((rows + 3));
            }


            wb.write(new FileOutputStream(file));//Save the file
            wb.close();
        }

    }
}
