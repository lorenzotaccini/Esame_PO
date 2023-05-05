import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    /**
     * @author Lorenzo Taccini
     */
    public static void main(String[] args) {
        JFrame mainframe= new JFrame("Gestione Bilancio");
        mainframe.setSize(400,600);
        InvoicesTableModel mainModel= new InvoicesTableModel();
        JTable maintable = new JTable(mainModel);
        mainframe.add(maintable);
        mainframe.setVisible(true);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //TODO implementare salvataggio alla chiusura con conferma
        //new mainFrame();
        Invoice t= new Invoice("ciaone",new BigDecimal("45.6"),LocalDateTime.now());
        mainModel.addInvoice(t);
        mainModel.addInvoice("spesa",new BigDecimal("2"), LocalDateTime.now());
        mainModel.addInvoice("succo",new BigDecimal("0.59"), LocalDateTime.now());
        mainModel.deleteInvoice(t);
    }
}