package Frames;
import TableModel.InvoicesTableModel;
import javax.swing.*;
import java.awt.*;

public class addFrame extends JFrame {
    public addFrame(InvoicesTableModel model) {
        addPanel panel= new addPanel();
        add(panel);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
