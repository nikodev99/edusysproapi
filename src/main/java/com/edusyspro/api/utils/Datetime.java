package com.edusyspro.api.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Datetime {

    public static ZonedDateTime brazzavilleDatetime() {
        return ZonedDateTime.now(ZoneId.of("Africa/Brazzaville"));
    }

    public static ZonedDateTime systemDatetime() {
        return ZonedDateTime.now(ZoneId.systemDefault());
    }

}
