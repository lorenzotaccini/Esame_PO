package Listeners;

import DatePickerGUI.MyDatePicker;
import TableModel.Invoice;
import TableModel.InvoicesTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;

/**
 * La classe implementa ActionListener e tramite il metodo di default actionPerformed aggiunge un elemento al modello
 * di tipo {@link InvoicesTableModel}
 */
public class addListener implements ActionListener {
    private final InvoicesTableModel model;
    private final JPanel tablePanel;

    public addListener(InvoicesTableModel model, JPanel tablePanel) {
        super();
        this.model=model;
        this.tablePanel=tablePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        MyDatePicker addDatePicker= new MyDatePicker();
        JTextField amountField = new JTextField();
        JTextArea descriptionField= new JTextArea(5,20);
        descriptionField.setFont(new Font("corsivo",Font.ITALIC,descriptionField.getFont().getSize()));

        final JComponent[] inputsComponent = new JComponent[] {
                new JLabel("Date:"),
                addDatePicker,
                new JLabel("Amount"),
                amountField,
                new JLabel("Brief description:"),
                descriptionField
        };
        int addPanelExitStatus = JOptionPane.showConfirmDialog(tablePanel, inputsComponent, "Add an invoice", JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (addPanelExitStatus == JOptionPane.OK_OPTION) {
            try {
                if(!addDatePicker.isTextFieldValid() || addDatePicker.getDate()==null){
                    throw (new DateTimeException("wrong date"));
                }
                model.addInvoice(new Invoice(descriptionField.getText(), Double.parseDouble(amountField.getText()), addDatePicker.getDate()));
                model.fireTableDataChanged();
            } catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(tablePanel,"wrong number format, \"ADD\" operation canceled");
            }
            catch (DateTimeException dateEx){
                JOptionPane.showMessageDialog(tablePanel,"empty or wrong date field, \"ADD\" operation canceled");
            }
        } else {
            System.out.println("User canceled/closed the dialog, exit status = " + addPanelExitStatus);
        }
    }
}
