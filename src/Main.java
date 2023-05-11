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

        InvoicesTableFrame frame=new InvoicesTableFrame();
        frame.mainModel.addInvoice(new Invoice("miao",10,LocalDateTime.now()));
        frame.mainModel.addInvoice(new Invoice("bau",13,LocalDateTime.now()));
        frame.mainModel.addInvoice(new Invoice("prova",10.67,LocalDateTime.now()));
        frame.mainModel.addInvoice(new Invoice("miao",10,LocalDateTime.now()));
        frame.mainModel.addInvoice(new Invoice("bau",13,LocalDateTime.now()));
        frame.mainModel.addInvoice(new Invoice("prova",10.67,LocalDateTime.now()));
        frame.mainModel.addInvoice(new Invoice("miao",10,LocalDateTime.now()));
        frame.mainModel.addInvoice(new Invoice("bau",13,LocalDateTime.now()));
        frame.mainModel.addInvoice(new Invoice("prova",10.67,LocalDateTime.now()));
        frame.mainModel.addInvoice(new Invoice("miao",10,LocalDateTime.now()));
        frame.mainModel.addInvoice(new Invoice("bau",13,LocalDateTime.now()));
        frame.mainModel.addInvoice(new Invoice("prova",10.67,LocalDateTime.now()));

    }
}