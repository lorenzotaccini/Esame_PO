package SaveLoadExport;

import TableModel.Invoice;
import TableModel.InvoicesTableModel;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe che permette il salvataggio/caricamento su/da file di tipo binario (.bin)
 */
public class binarySaverLoader extends AbstractSaverLoaderExporter{
    /**
     * Salvataggio su file binario (.bin) tramite OutputStream
     * @param model modello di tipo {@link InvoicesTableModel} di cui salvare i dati su file
     * @param file oggetto di tipo {@link File} su cui riversare lo stream di dati
     */
    @Override
    public void saveData(InvoicesTableModel model, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file.getAbsolutePath());
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
        ObjectOutputStream os;
        try {
            os = new ObjectOutputStream(fos);
            os.writeObject(model.getInvoiceSet());
            os.flush();
            os.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Caricamento da file binario (.bin) tramite InputStream
     * @param file file da cui prelevare i dati
     * @return {@link ArrayList<Invoice>} aggiornata con tutti i dati prelevati dal file
     */
    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Invoice> loadData(File file) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois;
        try (fis) {
            ois = new ObjectInputStream(fis);
            return (ArrayList<Invoice>) (ois.readObject());
        } catch(ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


