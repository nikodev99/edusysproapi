package com.edusyspro.api.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Datetime {

    public static ZoneId BRAZZA_TIME = ZoneId.of("Africa/Brazzaville");

    public static ZonedDateTime brazzavilleDatetime() {
        return ZonedDateTime.now(BRAZZA_TIME);
    }

    public static ZonedDateTime systemDatetime() {
        return ZonedDateTime.now(ZoneId.systemDefault());
    }

    public static ZonedDateTime zonedDateTime(String datetime) {
        Object[] content = isZonedDatetime(datetime);
        ZonedDateTime zdt = ZonedDateTime.now();
        if ((boolean) content[0]) {
            zdt = (ZonedDateTime) content[1];
        }
        return zdt.withZoneSameInstant(ZoneId.systemDefault());
    }

    public static ZonedDateTime toZone(LocalDateTime datetime, ZoneId zoneId) {
        return datetime.atZone(zoneId);
    }

    public static ZonedDateTime toZone(LocalDateTime datetime) {
        return datetime.atZone(BRAZZA_TIME);
    }

    public static ZonedDateTime toZone(LocalDate date) {
        return date.atStartOfDay(BRAZZA_TIME);
    }

    public static ZonedDateTime toZone(LocalDate datetime, ZoneId zoneId) {
        return datetime.atStartOfDay(zoneId);
    }

    public static Object[] isZonedDatetime (String datetime) {
        boolean isZonedDatetime;
        ZonedDateTime actualZonedDateTime = null;
        try {
            actualZonedDateTime = ZonedDateTime.parse(datetime);
            isZonedDatetime = true;
        }catch (DateTimeParseException e) {
            isZonedDatetime = false;
        }
        return new Object[]{isZonedDatetime, actualZonedDateTime};
    }

    public static String instantToFormat(Instant instant) {
        return instantToFormat(instant, "dd/MM/yyyy HH:mm:ss");
    }

    public static String instantToFormat(Instant instant, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format).withZone(ZoneId.of("Africa/Brazzaville"));
        return formatter.format(instant);
    }

}
