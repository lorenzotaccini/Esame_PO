package Panels;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * barra di stato che mostra il bilancio totale delle transazioni a schermo, e la data odierna
 */
public class statusPanel extends JPanel {
    public statusPanel(JFrame parentFrame) {
        super();
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setPreferredSize(new Dimension(parentFrame.getWidth(),20));
        setLayout(new BorderLayout(20,20));
    }
}
