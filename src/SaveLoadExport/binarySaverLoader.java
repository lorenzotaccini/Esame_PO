package SaveLoadExport;

import TableModel.Invoice;
import TableModel.InvoicesTableModel;

import java.io.*;
import java.util.ArrayList;

public class binarySaverLoader extends AbstractSaverLoaderExporter{
    @Override
    public void saveData(InvoicesTableModel model, File file) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file.getAbsolutePath());
        } catch(FileNotFoundException e){
            //something
        }
        ObjectOutputStream os;
        try {
            os = new ObjectOutputStream(fos);
            os.writeObject(model.getInvoiceSet());
            os.flush();
            os.close();
        }
        catch (IOException e){ System.exit(2);
        }
    }

    @Override
    public ArrayList<Invoice> loadData(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois=null;
        try(fis){
            ois= new ObjectInputStream(fis);
        }
        ArrayList<Invoice> outputInvoiceSet;
        try{
            outputInvoiceSet= (ArrayList<Invoice>) (ois.readObject());

        } catch (ClassNotFoundException e){
            return null;
        }
        return outputInvoiceSet;
    }
}
