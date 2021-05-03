package org.openjfx.helper;

import java.util.List;

public class PreDefineKeywordHelper {
    private static List<String> preDefineList;

    public static List<String> getPreDefineList() {
        if (preDefineList == null) {
            preDefineList = List.of("Artificial Intelligence", "Agile", "Software engineering", "Cloud computing", "Android", "iOS", "Software security", "Big data", "Database", "5G");
        }
        return preDefineList;
    }
}
