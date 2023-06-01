package Listeners;

import DatePickerGUI.MyDatePicker;
import TableModel.InvoicesTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;

public class editListener implements ActionListener {
    private final InvoicesTableModel model;
    private final JTable table;
    private final TableRowSorter<InvoicesTableModel> parentSorter;
    public editListener(TableRowSorter<InvoicesTableModel> parentSorter, JTable table) {
        this.table=table;
        this.model= (InvoicesTableModel) table.getModel();
        this.parentSorter= parentSorter;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int index, mappedindex;
        if(parentSorter.getRowFilter() == null) {
            index = table.getSelectedRow();
        }
        else{
            System.out.println();
            index=table.getSelectedRow();
            mappedindex= parentSorter.convertRowIndexToModel(index);
            index=mappedindex;
        }

        MyDatePicker addDatePicker= new MyDatePicker();
        addDatePicker.setDate((LocalDate) model.getValueAt(index,0));
        JTextField amountField = new JTextField();
        amountField.setText(String.valueOf(model.getValueAt(index,1)));
        JTextArea descriptionField= new JTextArea((String) model.getValueAt(index,2),5,20);
        descriptionField.setFont(new Font("corsivo",Font.ITALIC,descriptionField.getFont().getSize()));

        final JComponent[] inputsComponent = new JComponent[] {
                new JLabel("Date:"),
                addDatePicker,
                new JLabel("Amount:"),
                amountField,
                new JLabel("Brief description:"),
                descriptionField
        };
        int editPanelExitStatus = JOptionPane.showConfirmDialog(table, inputsComponent, "Edit selected invoice", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (editPanelExitStatus == JOptionPane.OK_OPTION) {
            try {
                if(!addDatePicker.isTextFieldValid() || addDatePicker.getDate()==null){
                    throw (new DateTimeException("wrong date"));
                }
                model.setValueAt(addDatePicker.getDate(), index, 0);
                model.setValueAt(Double.parseDouble((amountField.getText())), index, 1);
                model.setValueAt(descriptionField.getText(), index, 2);
                model.fireTableDataChanged();
            } catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(table,"wrong number format, \"ADD\" operation canceled");
            }
            catch (DateTimeException dateEx){
                JOptionPane.showMessageDialog(table,"empty or wrong date field, \"ADD\" operation canceled");
            }

        } else {
            System.out.println("User canceled/closed the dialog, exit status = " + editPanelExitStatus);
        }

    }
}
