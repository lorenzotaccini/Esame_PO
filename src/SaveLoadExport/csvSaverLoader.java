package SaveLoadExport;

import TableModel.Invoice;
import TableModel.InvoicesTableModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class csvSaverLoader extends AbstractSaverLoaderExporter{
    @Override
    public void saveData(InvoicesTableModel model, File file) {

        try{
            FileWriter csvWriter= new FileWriter(file);
            for (Invoice actualInvoice : model.getInvoiceSet()) {
                csvWriter.write(actualInvoice.getDate() + ";" + actualInvoice.getAmount() + ";" + actualInvoice.getDesc() + "\n");
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Invoice> loadData(File file) throws IOException {
        ArrayList<Invoice> l= new ArrayList<>();
        Scanner csvReader= new Scanner(file);

        //finchè trovo righe nel file, le leggo, faccio lo split e inserisco in l nuovi oggetti Invoice
        while(csvReader.hasNextLine()){
            l.add(new Invoice(
                    csvReader.nextLine().split(";")[2],
                    Double.parseDouble(csvReader.nextLine().split(";")[1]),
                    LocalDate.parse(csvReader.nextLine().split(";")[0])
                    )
            );
        }
        csvReader.close();
        System.out.println(l);
        return l;
    }
}
