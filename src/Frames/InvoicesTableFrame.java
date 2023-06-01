package Frames;

import Filters.searchFilter;
import Listeners.*;
import Panels.datePanel;
import Panels.statusPanel;
import TableModel.InvoicesTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;


public class InvoicesTableFrame extends JFrame {
    public InvoicesTableModel mainModel; //TODO metodo private quando togli dati di esempio dal main


    public InvoicesTableFrame() {
        setTitle("Gestione Bilancio Taccini");

        mainModel=new InvoicesTableModel();
        JTable mainTable = new JTable(mainModel);
        final TableRowSorter<InvoicesTableModel> sorter = new TableRowSorter<>(mainModel);
        mainTable.setRowSorter(sorter);

        JPanel mainPanel=new JPanel(new BorderLayout(10,10));
        JPanel topPanel=new JPanel(new BorderLayout(10,10));
        JPanel tablePanel=new JPanel(new BorderLayout(10,10));
        JPanel bottomPanel=new JPanel();

        datePanel mainDatePanel=new datePanel(sorter);
        JPanel filterPanel=new JPanel(new BorderLayout(10,10));
        JPanel filterTypeSelectionPanel= new JPanel();
        filterTypeSelectionPanel.setLayout(new BoxLayout(filterTypeSelectionPanel,BoxLayout.X_AXIS));
//        JTabbedPane tabbedPane = new JTabbedPane(); TODO TABBED PANE INSTEAD OF JRADIOBUTTON
        topPanel.setBorder(BorderFactory.createEmptyBorder(15,10, 5, 10));

//        tabbedPane.addTab("Regex Filter",filterPanel);
//        tabbedPane.addTab("Date Filter",datePanel);

        JPopupMenu mainPopupMenu = new JPopupMenu();

        final JTextField filterText = new JTextField("");
        final JButton addButton = new JButton("ADD");
        final JButton deleteButton = new JButton("DELETE");
        final JMenuItem popupDelete = new JMenuItem("Delete");
        final JMenuItem popupEdit = new JMenuItem("Edit Row");

        final JLabel totalLabel = new JLabel();
        final JLabel filterTypeSelectionLabel= new JLabel("Filter by:");
        final JButton resetFiltersBtn = new JButton("RESET FILTERS \uD83D\uDD04");
//        JButton printButton =new JButton("Print");

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
        filterTypeSelectionPanel.add(resetFiltersBtn,BorderLayout.EAST);


        resetFiltersBtn.addActionListener(e -> {
            mainDatePanel.resetPanel();
            sorter.setRowFilter(null);
            filterText.setText(null);
            mainDatePanel.setVisible(false);
            filterPanel.setVisible(false);
            filterTypeSelectionGroup.clearSelection();
        });

        filterByRegexBtn.addActionListener(e -> {
            mainDatePanel.setVisible(false);
            filterPanel.setVisible(true);
            filterText.requestFocus();
        });

        filterByDateBtn.addActionListener(e -> {
            filterPanel.setVisible(false);
            mainDatePanel.setVisible(true);
        });

//        printButton.addActionListener((ActionListener) e -> {
//            try {
//                if (! mainTable.print()) {
//                    System.err.println("User cancelled printing");
//                }
//            } catch (PrinterException e2) {
//                System.err.format("Cannot print %s%n", e2.getMessage());
//            }
//        });


        mainPopupMenu.add(popupDelete);
        mainPopupMenu.add(popupEdit);

        //aggiungo tramite la classe Frames.popupMenu la selezione automatica dell'elemento della tabella quando è premuto il tasto destro
        popupMenuSettings.setupPopupMenu(mainTable, mainPopupMenu);
        popupMenuSettings.setupDoubleClickToEdit(mainTable);

        tablePanel.add(new JScrollPane(mainTable));



        filterPanel.add(new JLabel("Search:"), BorderLayout.WEST);
        filterPanel.add(filterText, BorderLayout.CENTER);

        topPanel.add(filterTypeSelectionPanel,BorderLayout.NORTH);
        topPanel.add(filterPanel,BorderLayout.SOUTH);
        topPanel.add(mainDatePanel);
//        topPanel.add(tabbedPane);

        bottomPanel.add(addButton,BorderLayout.NORTH);
        bottomPanel.add(deleteButton,BorderLayout.CENTER);
        bottomPanel.add(new statusPanel(this).add(totalLabel),BorderLayout.SOUTH);

        filterPanel.setVisible(false);
        mainDatePanel.setVisible(false);

        mainPanel.add(topPanel,BorderLayout.NORTH);
        mainPanel.add(tablePanel,BorderLayout.CENTER);
        mainPanel.add(bottomPanel,BorderLayout.SOUTH);
        add(mainPanel);


        sorter.addRowSorterListener(new sorterListener(totalLabel));
        mainModel.addTableModelListener(new totalListener(totalLabel,mainModel));
        addButton.addActionListener(new addListener(mainModel,tablePanel));
        deleteButton.addActionListener(new deleteListener(sorter,mainModel, mainTable));
        popupDelete.addActionListener(new deleteListener(sorter,mainModel, mainTable));
        popupEdit.addActionListener(new editListener(sorter, mainTable));
        filterText.getDocument().addDocumentListener(new searchFilter(sorter,filterText));

        pack();
        setMinimumSize(this.getSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
