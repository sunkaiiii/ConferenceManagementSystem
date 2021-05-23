package org.openjfx.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public final class TimeHelper {
    /**
     * Convert iso time to the time format used for display, the time format will be based on the local region
     * @param time Standard Timestamp
     * @return localised date time
     */
    public static String convertToDisplayTime(String time){
        return LocalDateTime.parse(time).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
}
