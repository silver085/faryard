package com.faryard.api.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class DatesUtils {
    static final int MINUTES_PER_HOUR = 60;
    static final int SECONDS_PER_MINUTE = 60;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    private static LocalDateTime convertToLocalDateTime(Date date){
        return date.toInstant()
                    .atZone(ZoneId.of("Europe/Rome"))
                    .toLocalDateTime();
    }

    private static long differenceInMinutes(LocalDateTime from, LocalDateTime to){
        Duration duration = Duration.between(from,to);
        long seconds = ((duration.getSeconds() % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        return seconds;
    }

    public static long differenceInMinutesBetweenDates(Date from, Date to){
        LocalDateTime dtFrom = convertToLocalDateTime(from);
        LocalDateTime dtTo = convertToLocalDateTime(to);
        return differenceInMinutes(dtFrom, dtTo);
    }
}
