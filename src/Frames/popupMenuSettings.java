package Frames;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * La classe aggiunge il popupmenu alla tabella quando è premuto il tasto destro su un elemento.
 * Viene selezionata la riga su cui è stato effettuato il click
 */
public class popupMenuSettings {
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
        });
    }
}
