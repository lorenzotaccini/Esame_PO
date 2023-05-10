import javax.swing.table.AbstractTableModel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe TableModel che estende la classe AbstractTableModel per adattarla alle esigenze del progetto.
 * Si utilizza una ArrayList come struttura dati per il salvataggio e la gestione delle voci di bilancio.
 *
 */
public class InvoicesTableModel extends AbstractTableModel {
    /**
     * ArrayList di generics di tipo invoice
     */
    private final ArrayList<Invoice> invoiceSet;
    String[] columnNames = {"Date", "Amount", "Description"};

    public InvoicesTableModel() {
        super();
        invoiceSet=new ArrayList<>();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /** metodo per aggiungere elementi alla tabella, viene notificato il cambiamento a tutti i listener */
    public void addInvoice(String desc, double amount, LocalDateTime date) {
        invoiceSet.add(new Invoice(desc,amount,date));
        fireTableDataChanged();
    }
    public void addInvoice(Invoice t) {
        invoiceSet.add(t);
        fireTableDataChanged();
    }
    /** Si utilizza un oggetto di classe Iterator per scorrere il vettore arrayList e rimuovere l'oggetto*/
    public void deleteInvoice(Invoice delItem){
        Iterator<Invoice> itr = invoiceSet.iterator();
        while(itr.hasNext()){
            Invoice actual= itr.next();
            if(delItem.equals(actual)){
                itr.remove(); //rimozione dell'elemento tramite iteratore
                fireTableDataChanged();

                return;
            }
        }

    }

    @Override
    public int getRowCount() {
        return this.invoiceSet.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> invoiceSet.get(rowIndex).getDate();
            case 1 -> invoiceSet.get(rowIndex).getAmount();
            case 2 -> invoiceSet.get(rowIndex).getDesc();
            default -> null;
        };
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        switch (columnIndex){
            case 1 -> invoiceSet.get(rowIndex).setAmount((Double) aValue);
            case 2 -> invoiceSet.get(rowIndex).setDesc(((String) aValue));
        }
    }

    public Invoice getInvoiceAtRow(int index){
        return invoiceSet.get(index);
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return columnIndex > 0;
    }


    /** Metodo che restituisce la classe del dato contenuto in una certa colonna */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //si guarda alla prima riga
        return getValueAt(0, columnIndex).getClass();
    }

}
