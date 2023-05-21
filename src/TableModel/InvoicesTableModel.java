package TableModel;

import DatePickerGUI.MyDatePicker;

import javax.swing.*;
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
    public void addInvoice(String desc, double amount, LocalDate date) {
        invoiceSet.add(new Invoice(desc,amount,date));
        fireTableDataChanged();
    }
    /** metodo per aggiungere elementi alla tabella, viene notificato il cambiamento a tutti i listener */
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
            case 0 -> invoiceSet.get(rowIndex).setDate((LocalDate) aValue);
            case 1 -> invoiceSet.get(rowIndex).setAmount((Double) aValue);
            case 2 -> invoiceSet.get(rowIndex).setDesc(((String) aValue));
        }
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
        return true;
    }


    /** Metodo che restituisce la classe del dato contenuto in una certa colonna
     * @return Classe del dato in colonna columnIndex */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //si guarda alla prima riga
        return getValueAt(0, columnIndex).getClass();
    }

}
