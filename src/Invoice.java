import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Invoice {
    private String desc;
    /** il tipo di dato BigDecimal è adatto all'utilizzo in ambito monetario*/
    private BigDecimal amount;
    private LocalDateTime date;
    private DateTimeFormatter formattedDate;

    public Invoice(String desc, BigDecimal amount, LocalDateTime date) {
        this.desc = desc;
        this.amount = amount;
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public DateTimeFormatter getFormattedDate() {
        return formattedDate;
    }

    @Override
    public String toString(){
        return "data: "+formattedDate+"\namount: "+amount+"\ndescription: "+desc;
    }
}
