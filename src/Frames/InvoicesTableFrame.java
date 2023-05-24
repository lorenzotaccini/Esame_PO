package Frames;

import Listeners.*;
import Panels.datePanel;
import Panels.statusPanel;
import TableModel.InvoicesTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.regex.PatternSyntaxException;


public class InvoicesTableFrame extends JFrame {
    public InvoicesTableModel mainModel; //TODO metodo private quando togli dati di esempio dal main


    public InvoicesTableFrame() {
        setTitle("Gestione Bilancio Taccini");


        JPanel mainPanel=new JPanel(new BorderLayout(10,10));
        JPanel topPanel=new JPanel(new BorderLayout(10,10));
        JPanel tablePanel=new JPanel(new BorderLayout(10,10));
        JPanel bottomPanel=new JPanel();
        datePanel datePanel=new datePanel();
        JPanel filterPanel=new JPanel(new BorderLayout(10,10));
        JPanel filterTypeSelectionPanel= new JPanel();
        filterTypeSelectionPanel.setLayout(new BoxLayout(filterTypeSelectionPanel,BoxLayout.X_AXIS));
        JTabbedPane tabbedPane = new JTabbedPane();
        topPanel.setBorder(BorderFactory.createEmptyBorder(15,10, 5, 10));

        tabbedPane.addTab("Regex Filter",filterPanel);
        tabbedPane.addTab("Date Filter",datePanel);


        mainModel=new InvoicesTableModel();
        JTable mainTable = new JTable(mainModel);


        final TableRowSorter<InvoicesTableModel> sorter = new TableRowSorter<>(mainModel);
        mainTable.setRowSorter(sorter);

        JPopupMenu mainPopupMenu = new JPopupMenu();

        final JTextField filterText = new JTextField("");
        final JButton addButton = new JButton("ADD");
        final JButton deleteButton = new JButton("DELETE");
        final JMenuItem popupDelete = new JMenuItem("Delete");
        final JMenuItem popupEdit = new JMenuItem("Edit Row");

        final JLabel totalLabel = new JLabel();
        final JLabel filterTypeSelectionLabel= new JLabel("Filter by:");
        final JButton restoreFiltersBtn = new JButton("RESTORE");
        final ButtonGroup filterTypeSelectionGroup= new ButtonGroup();
        final JRadioButton filterByRegexBtn = new JRadioButton("Text Search");
        final JRadioButton filterByDateBtn = new JRadioButton("Date");
        filterByDateBtn.setBorder(BorderFactory.createEmptyBorder(0,10, 0, 10));
        filterByRegexBtn.setBorder(BorderFactory.createEmptyBorder(0,20, 0, 10));

        filterTypeSelectionGroup.add(filterByRegexBtn);
        filterTypeSelectionGroup.add(filterByDateBtn);
        filterTypeSelectionPanel.add(filterTypeSelectionLabel);
        filterTypeSelectionPanel.add(filterByRegexBtn);
        filterTypeSelectionPanel.add(filterByDateBtn);


//        //filtro sole date
//        List<RowFilter<Object,Object>> dateFilterIntervalArray = new ArrayList<RowFilter<Object,Object>>(2);
//        dateFilterIntervalArray.add( RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, startDate) );
//        dateFilterIntervalArray.add( RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, endDate) );
//        RowFilter<Object, Object> dateIntervalRowFilter = RowFilter.andFilter(dateFilterIntervalArray); //andfilter tra data inizio e data fine
//


        DocumentListener regexFilter = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String searchedText = filterText.getText();
                if(searchedText.length() == 0) {
                    //searchbox vuota, filtro solo per date interval
                    sorter.setRowFilter(null);
                } else {
                    try {
                        /*
                          il RowFilter può essere impostato per controllare le regex solo su specifici campi del
                          table model, è sufficiente passargli gli indici delle colonne su cui deve operare.
                         */
                        sorter.setRowFilter(RowFilter.regexFilter(searchedText));
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

        restoreFiltersBtn.addActionListener(e -> {
            sorter.setRowFilter(null);
            filterText.setText(null);
        });

        filterByRegexBtn.addActionListener(e -> {
            datePanel.setVisible(false);
            filterPanel.setVisible(true);
        });

        filterByDateBtn.addActionListener(e -> {
            filterPanel.setVisible(false);
            datePanel.setVisible(true);
        });


        mainPopupMenu.add(popupDelete);
        mainPopupMenu.add(popupEdit);
        //aggiungo tramite la classe Frames.popupMenu la selezione automatica dell'elemento della tabella quando è premuto il tasto destro
        popupMenuSettings.setupPopupMenu(mainTable, mainPopupMenu);

        tablePanel.add(new JScrollPane(mainTable));
        tablePanel.add(restoreFiltersBtn,BorderLayout.PAGE_END);

        JLabel filterLabel = new JLabel(" Filter:");
        filterPanel.add(filterLabel, BorderLayout.WEST);
        filterPanel.add(filterText, BorderLayout.CENTER);

        topPanel.add(filterTypeSelectionPanel,BorderLayout.NORTH);
        topPanel.add(filterPanel,BorderLayout.SOUTH);
        topPanel.add(datePanel);
//        topPanel.add(tabbedPane);

        bottomPanel.add(addButton,BorderLayout.NORTH);
        bottomPanel.add(deleteButton,BorderLayout.CENTER);
        bottomPanel.add(new statusPanel(this).add(totalLabel),BorderLayout.SOUTH);

        filterPanel.setVisible(false);
        datePanel.setVisible(false);

        mainPanel.add(topPanel,BorderLayout.NORTH);
        mainPanel.add(tablePanel,BorderLayout.CENTER);
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);
        add(mainPanel);


        sorter.addRowSorterListener(new sorterListener(totalLabel));
        mainModel.addTableModelListener(new totalListener(totalLabel,mainModel));
        addButton.addActionListener(new addListener(mainModel,tablePanel));
        deleteButton.addActionListener(new deleteListener(sorter,mainModel, mainTable));
        popupDelete.addActionListener(new deleteListener(sorter,mainModel, mainTable));
        popupEdit.addActionListener(new editListener(sorter,mainModel, mainTable));
        filterText.getDocument().addDocumentListener(regexFilter);

        pack();
        setMinimumSize(this.getSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
