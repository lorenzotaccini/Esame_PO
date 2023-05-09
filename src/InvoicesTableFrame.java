import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.PatternSyntaxException;

public class InvoicesTableFrame extends JFrame {
    public JTable mainTable;
    public InvoicesTableModel mainModel;

    public InvoicesTableFrame() {
        setTitle("Gestione Bilancio Taccini");

        mainModel=new InvoicesTableModel();
        mainTable = new JTable(mainModel);
        final TableRowSorter<InvoicesTableModel> sorter = new TableRowSorter<>(mainModel);

        mainTable.setRowSorter(sorter);
        add(new JScrollPane(mainTable), BorderLayout.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Filter");
        panel.add(label, BorderLayout.WEST);
        final JTextField filterText = new JTextField("");
        panel.add(filterText, BorderLayout.CENTER);
        add(panel, BorderLayout.NORTH);
        DocumentListener regexfilter = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = filterText.getText();
                if(text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
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
        };

        filterText.getDocument().addDocumentListener(regexfilter);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
