package org.openjfx.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public final class TimeHelper {
    public static String convertToDisplayTime(String time){
        return LocalDateTime.parse(time).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
}
