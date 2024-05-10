package org.defalt.core.util;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtils {
    public static Date plusMinutes(Date date, int minutes) {
        return plus(date, minutes, ChronoUnit.MINUTES);
    }

    public static Date plusHours(Date date, int hours) {
        return plus(date, hours, ChronoUnit.HOURS);
    }

    public static Date plusDays(Date date, int days) {
        return plus(date, days, ChronoUnit.DAYS);
    }

    public static Date plus(Date date, int amountToAdd, ChronoUnit unit) {
        return Date.from(date.toInstant().plus(amountToAdd, unit).atZone(ZoneId.systemDefault()).toInstant());
    }
}
