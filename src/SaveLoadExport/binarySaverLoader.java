package SaveLoadExport;

import TableModel.Invoice;
import TableModel.InvoicesTableModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class binarySaverLoader extends AbstractSaverLoaderExporter{
    @Override
    public void saveData(InvoicesTableModel model, File file) throws IOException {
        model.getInvoiceSet();
    }

    @Override
    public ArrayList<Invoice> loadData(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        try(fis){
            ObjectInputStream ois= new ObjectInputStream(fis);
        }

        return null;
    }
}
