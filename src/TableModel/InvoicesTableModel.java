package TableModel;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe TableModel che estende la classe AbstractTableModel per adattarla alle esigenze del progetto.
 * Si utilizza una ArrayList come struttura dati per il salvataggio e la gestione delle voci di bilancio.
 * (Oggetto Vector ormai deprecato)
 */
public class InvoicesTableModel extends AbstractTableModel {
    /**
     * ArrayList di generics di tipo invoice
     */
    private final ArrayList<Invoice> invoiceSet;
    private double total=0;
    String[] columnNames = {"Date", "Amount", "Description"};

    public InvoicesTableModel() {
        super();
        invoiceSet=new ArrayList<>();
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /** metodo per aggiungere elementi alla tabella, viene notificato il cambiamento a tutti i listener */
    public void addInvoice(Invoice t) {
        invoiceSet.add(t);
        total=total+t.getAmount();
        fireTableDataChanged();
    }

    /** Si utilizza un oggetto di classe Iterator per scorrere il vettore arrayList e rimuovere l'oggetto*/
    public void deleteInvoice(Invoice delItem){
        Iterator<Invoice> itr = invoiceSet.iterator();
        while(itr.hasNext()){
            Invoice actual= itr.next();
            if(delItem.equals(actual)){
                total=total-actual.getAmount();
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
            case 0 -> invoiceSet.get(rowIndex).setDate((LocalDate) aValue);
            case 1 -> {
                total -= invoiceSet.get(rowIndex).getAmount();
                invoiceSet.get(rowIndex).setAmount((Double) aValue);
                total+= invoiceSet.get(rowIndex).getAmount();
            }
            case 2 -> invoiceSet.get(rowIndex).setDesc(((String) aValue));
        }
        fireTableDataChanged();
    }

    public Invoice getInvoiceAtRow(int index){
        return invoiceSet.get(index);
    }

    /**
     * @return 1 se la cella è editabile, 0 altrimenti
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }


    /** Metodo che restituisce la classe del dato contenuto in una certa colonna
     * @return Classe del dato in colonna columnIndex */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //si guarda alla prima riga
        return getValueAt(0, columnIndex).getClass();
    }

}
