package Filters;

import TableModel.InvoicesTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.util.regex.PatternSyntaxException;

/**
 * classe che implementa DocumentListener e crea un RowFilter dedicato alla ricerca di elementi basata sul testo tramite regex
 * pattern. Il testo ottenuto dal JtextField passato come argomento è processato dal RowFilter.regexfilter e applicato sul tableRowSorter
 */
public class searchFilter implements DocumentListener {
    private final JTextField pattern;
    private final TableRowSorter<InvoicesTableModel> parentSorter;
    public searchFilter(TableRowSorter<InvoicesTableModel> parentSorter,JTextField pattern) {
        this.parentSorter=parentSorter;
        this.pattern=pattern;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if(pattern.getText().length() == 0) {
            //searchbox vuota, filtro solo per date interval
            parentSorter.setRowFilter(null);
        } else {
            try {
                        /*
                          il RowFilter può essere impostato per controllare le regex solo su specifici campi del
                          table model, è sufficiente passargli gli indici delle colonne su cui deve operare.
                         */
                parentSorter.setRowFilter(RowFilter.regexFilter(pattern.getText()));
            } catch(PatternSyntaxException pse) {
                System.out.println("Bad regex pattern");
            }
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        insertUpdate(e);
    }
    @Override
    public void changedUpdate(DocumentEvent e) {
        insertUpdate(e);
    }
}
