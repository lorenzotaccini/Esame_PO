package SaveLoadExport;

import TableModel.Invoice;
import TableModel.InvoicesTableModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractSaverLoaderExporter {
    public abstract void saveData(InvoicesTableModel model, File file) throws IOException;

    public abstract ArrayList<Invoice> loadData(File file) throws IOException;
    
}
