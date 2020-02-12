package com.faryard.api.utils;

import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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


    public static Date asDate(LocalDate localDate , ZoneId zoneId) {
        return Date.from(localDate.atStartOfDay().atZone(zoneId).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime, ZoneId zoneId) {
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    public static LocalDate asLocalDate(Date date,  ZoneId zoneId) {
        return Instant.ofEpochMilli(date.getTime()).atZone(zoneId).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date, ZoneId zoneId) {
        return Instant.ofEpochMilli(date.getTime()).atZone(zoneId).toLocalDateTime();
    }

    public static int getHourFromDate(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);   // assigns calendar to given date
        return calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
    }
}
