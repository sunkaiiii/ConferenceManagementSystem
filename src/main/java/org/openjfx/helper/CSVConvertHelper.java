package org.openjfx.helper;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * For CSV based database mods, this method must be implemented to write the data to the CSV file
 */
public class CSVConvertHelper {
    public static String convertClassToCSVStringLine(Object object) {
        return new Gson().toJson(object);
    }

}
