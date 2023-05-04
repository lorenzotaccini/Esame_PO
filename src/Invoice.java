import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Invoice {
    private String desc;
    /** il tipo di dato BigDecimal è adatto all'utilizzo in ambito monetario*/
    private BigDecimal amount;
    private LocalDateTime date;
    private DateTimeFormatter formattedDate;
}
