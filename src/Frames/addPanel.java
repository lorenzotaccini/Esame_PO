package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class addPanel extends JPanel {

    private JTextField dateTextField;
    private JTextField amountTextField;
    private JTextField descriptionTextField;

    public addPanel(){
        JLabel dateLabel= new JLabel("Date:");
        JLabel amountLabel= new JLabel("Amount:");
        JLabel descLabel= new JLabel("Description:");
        JTextField dateTextField= new JTextField("DISABILITATO TESTING");
        JTextField amountTextField= new JTextField("aaaaa");
        JTextField descTextField= new JTextField("bbbbbb");
        JButton addButton= new JButton("ADD");
        addButton.setIcon(new ImageIcon("resources/add-button.png"));
        add(dateLabel,BorderLayout.NORTH);
        add(dateTextField,BorderLayout.AFTER_LAST_LINE);
        add(amountLabel, BorderLayout.CENTER);
        add(amountTextField,BorderLayout.CENTER);
        add(descLabel,BorderLayout.SOUTH);
        add(descTextField,BorderLayout.SOUTH);
        add(addButton);
    }

}
