package Filters;

import javax.swing.*;
import java.time.LocalDate;

public class LocalDateRowFilter extends RowFilter<Object,Object> {
    private final LocalDate startLocalDate;
    private final LocalDate endLocalDate;


    public LocalDateRowFilter(LocalDate startLocalDate,LocalDate endLocalDate) {
        super();
        this.startLocalDate=startLocalDate;
        this.endLocalDate=endLocalDate;
    }

    @Override
    public boolean include(Entry entry) {
        LocalDate current=(LocalDate)entry.getValue(0);
        return current.isAfter(startLocalDate.minusDays(1)) && current.isBefore(endLocalDate.plusDays(1));
    }
}