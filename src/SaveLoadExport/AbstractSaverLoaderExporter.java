package SaveLoadExport;

import TableModel.Invoice;
import TableModel.InvoicesTableModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe astratta che espone metodi per il salvataggio/esportazione su file dei dati della tabella e per il caricamento da file
 *
 */
public abstract class AbstractSaverLoaderExporter {
    /**
     * salvataggio di dati su file.
     * @param model modello di tipo {@link InvoicesTableModel} di cui salvare i dati su file
     * @param file oggetto di tipo {@link File} su cui riversare lo stream di dati
     */
    public abstract void saveData(InvoicesTableModel model, File file) throws IOException;

    /**
     * caricamento di dati da file
     * @param file file da cui prelevare i dati
     * @return {@link ArrayList<Invoice>} aggiornata con tutti i dati prelevati dal file
     */
    public abstract ArrayList<Invoice> loadData(File file) throws IOException;
    
}
