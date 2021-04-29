package org.openjfx.helper;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CSVConvertHelper {
    public static String convertClassToCSVStringLine(Object object) {
        return new Gson().toJson(object);
    }

}
