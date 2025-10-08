package fast.retire.integration.infrastructure.userportfolio;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class LastWorkingDayWeekBeforeAdjuster implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
        LocalDate date = LocalDate.from(temporal).minusWeeks(1);
        while (date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.minusDays(1);
        }
        return date;
    }
}
