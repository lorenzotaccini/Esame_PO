package Listeners;

import javax.swing.*;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;

public class sorterListener implements RowSorterListener {
    private final JLabel partialLabel;
    public sorterListener(JLabel partialLabel) {
        this.partialLabel=partialLabel;
    }

    @Override
    public void sorterChanged(RowSorterEvent e) {
        System.out.println(e.toString());


    }
}
