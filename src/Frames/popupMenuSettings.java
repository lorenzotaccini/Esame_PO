package Frames;

import Listeners.editListener;
import TableModel.InvoicesTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * La classe propone metodi per azionare l'apertura del popupmenu su una JTable quando è premuto il tasto destro su un elemento,
 * e la funzione di doppio click per modificare un elemento.
 * Viene selezionata la riga su cui è stato effettuato il click.
 */
public class popupMenuSettings {
    /**
     * Aggiunge il menu di popup in caso di click destro su un elemento della tabella
     * @param table tabella su cui applicare il click destro
     * @param popupMenu il menu che verrà mostrato
     */
    public static void setupPopupMenu(JTable table,final JPopupMenu popupMenu) {
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                //viene premuto il tasto destro su un elemento della tabella
                if (e.getButton() == MouseEvent.BUTTON3) {
                    //ottengo la riga della tabella presente sotto al puntatore del mouse
                    int r = table.rowAtPoint(e.getPoint());
                    if (r >= 0 && r < table.getRowCount()) {
                        table.setRowSelectionInterval(r, r);
                        popupMenu.show(table, e.getX(), e.getY());
                    } else {
                        table.clearSelection();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(e.isPopupTrigger()){
                    mouseReleased(e);
                }
            }
        });
    }

    /**
     * Aggiunge la funzionalità di doppio click su un elemento della tabella per aprire il pannello di modifica.
     * @param table la JTable su cui applicare la funzionalità
     * @param parentSorter il sorter della classe chiamante necessario per implementare un {@link editListener} internamente alla funzione
     */
    public static void setupDoubleClickToEdit(JTable table,TableRowSorter<InvoicesTableModel> parentSorter) {

        editListener editL= new editListener(parentSorter,table);
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                //viene premuto il tasto destro su un elemento della tabella
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    //ottengo la riga della tabella presente sotto al puntatore del mouse
                    e.consume();
                    System.out.println("Double click");
                    int r = table.rowAtPoint(e.getPoint());
                    if (r >= 0 && r < table.getRowCount()) {
                        table.setRowSelectionInterval(r, r);

                    } else {
                        table.clearSelection();
                    }
                    editL.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                }
            }
        });
    }
}
