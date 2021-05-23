package org.openjfx.model.interfaces;

/**
 * The model class that implements this interface will be available for CSV based database
 * @param <T> model class
 */
public interface CSVConvertable<T> {
    String convertToCSVLine();
}
