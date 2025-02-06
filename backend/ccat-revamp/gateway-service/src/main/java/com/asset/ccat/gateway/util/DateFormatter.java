package com.asset.ccat.gateway.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Mayar.EzzElDin
 * A utility class for converting date strings between different formats and time zones.
 */
public class DateFormatter {
    private static final String[] predefinedFormats = {
            // ISO 8601 formats
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",

            // Standard date-time formats
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-d HH:mm:ss",
            "yyyy-MM-dd h:mm:ss a",
            "yyyy-MM-d h:mm:ss a",
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-d HH:mm",
            "yyyy-MM-dd h:mm a",
            "yyyy-MM-d h:mm a",

            // Slash-separated formats
            "MM/dd/yyyy HH:mm:ss",
            "MM/dd/yyyy HH:mm:ss a",
            "MM/dd/yyyy hh:mm:ss a",
            "M/d/yyyy HH:mm:ss",
            "MM/dd/yyyy h:mm:ss a",
            "M/d/yyyy h:mm:ss a",
            "MM/dd/yyyy HH:mm",
            "M/d/yyyy HH:mm",
            "MM/dd/yyyy h:mm a",
            "M/d/yyyy h:mm a",

            // Dash-separated formats
            "dd-MM-yyyy HH:mm:ss",
            "d-M-yyyy HH:mm:ss",
            "dd-MM-yyyy h:mm:ss a",
            "d-M-yyyy h:mm:ss a",
            "dd-MM-yyyy HH:mm",
            "d-M-yyyy HH:mm",
            "dd-MM-yyyy h:mm a",
            "d-M-yyyy h:mm a",

            "MM/dd/yyyy HH:mm:ss",
            // European formats
            "dd/MM/yyyy HH:mm:ss",
            "d/M/yyyy HH:mm:ss",
            "dd/MM/yyyy h:mm:ss a",
            "d/M/yyyy h:mm:ss a",
            "dd/MM/yyyy HH:mm",
            "d/M/yyyy HH:mm",
            "dd/MM/yyyy h:mm a",
            "d/M/yyyy h:mm a",
            "dd/MM/yyyy hh:mm:ss a",

            // Year-first formats with slashes
            "yyyy/MM/dd HH:mm:ss",
            "yyyy/M/d HH:mm:ss",
            "yyyy/MM/dd h:mm:ss a",
            "yyyy/M/d h:mm:ss a",
            "yyyy/MM/dd HH:mm",
            "yyyy/M/d HH:mm",
            "yyyy/MM/dd h:mm a",
            "yyyy/M/d h:mm a",

            // Year-first formats with dashes
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-d HH:mm:ss",
            "yyyy-MM-dd h:mm:ss a",
            "yyyy-MM-d h:mm:ss a",
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-d HH:mm",
            "yyyy-MM-dd h:mm a",
            "yyyy-MM-d h:mm a",

            // Shortened year formats
            "yy-MM-dd HH:mm:ss",
            "yy-M-d HH:mm:ss",
            "yy/MM/dd HH:mm:ss",
            "yy/M/d HH:mm:ss",
            "yy-MM-dd h:mm:ss a",
            "yy-M-d h:mm:ss a",
            "yy/MM/dd h:mm:ss a",
            "yy/M/d h:mm:ss a",

            // Time-only formats
            "HH:mm:ss",
            "h:mm:ss a",
            "HH:mm",
            "h:mm a",

            // Date-only formats
            "yyyy-MM-dd",
            "yyyy-M-d",
            "MM/dd/yyyy",
            "M/d/yyyy",
            "dd-MM-yyyy",
            "d-M-yyyy",
            "dd/MM/yyyy",
            "d/M/yyyy",
            "yyyy/MM/dd",
            "yyyy/M/d",
            "yyyyMMdd", //ccat
            "ddMMyyyy",

            "yyyyMMdd'T'HH:mm:ss+0000",
            "yyyyMMdd'T'HH:mm:ss",
            "E, dd MMM yyyy",
            "E, dd MMM yyyy HH:mm:ss",
            "yyyyMMdd'T'HH:mm:ss+0200",
            "MMM dd HH:mm:ss",
            "dd/MM/yyyy HH:mm",
            "dd-MM-yyyy HH:mm:ss a",
            "hh:mm:ss a",
            "hh:mm:ss",
            "dd-MM-yyyy hh:mm:ss a",
            "EEE MMM dd HH:mm:ss zzz yyyy"

    };

    /**
     * Converts a date string from various possible formats into a standardized format, while handling time zones.
     *
     * @param inputDate      The input date string to be converted the format should be predefined.
     * @param targetFormat   The desired output date format.
     * @param inputTimeZone  The time zone of the input date string.
     * @param targetTimeZone The desired time zone of the output date.
     * @return A string representing the date in the target format and time zone.
     * @throws IllegalArgumentException If the input date is null, empty, or cannot be parsed.
     */
    public static String convertToStandardFormat(String inputDate, String targetFormat, TimeZone inputTimeZone, TimeZone targetTimeZone) {
        if (inputDate == null || inputDate.isEmpty() || inputTimeZone == null || targetTimeZone == null || targetFormat == null) {
            throw new IllegalArgumentException("Cannot parse date: " + inputDate);
        }

        // Formatter for the desired output format
        SimpleDateFormat outputFormat = new SimpleDateFormat(targetFormat, Locale.ENGLISH);
        outputFormat.setTimeZone(targetTimeZone);

        for (String format : predefinedFormats) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat(format, Locale.ENGLISH);
                inputFormat.setLenient(false); // Strict parsing to prevent incorrect date interpretation
                inputFormat.setTimeZone(inputTimeZone);

                Date reformattedInputDate = inputFormat.parse(inputDate);

                // Validate if the parsed date matches the input
                if (isDateMatchingFormat(inputDate, inputFormat, reformattedInputDate)) {
                    return outputFormat.format(reformattedInputDate);
                }
            } catch (ParseException ignored) {
                // Continue trying with the next format
            }
        }

        throw new IllegalArgumentException("Cannot parse date: " + inputDate);
    }

    public static String convertToStandardFormatGivenFormat(String inputDate, String inputFormat, String targetFormat, TimeZone inputTimeZone, TimeZone targetTimeZone) {
        if (inputDate == null || inputDate.isEmpty() || inputFormat == null || inputTimeZone == null || targetTimeZone == null || targetFormat == null) {
            throw new IllegalArgumentException("Cannot parse date: " + inputDate + " Due to missing required arguments.");
        }

        try {
            SimpleDateFormat inputFormatter = new SimpleDateFormat(inputFormat, Locale.ENGLISH);
            inputFormatter.setTimeZone(inputTimeZone);

            Date parsedDate = inputFormatter.parse(inputDate);

            SimpleDateFormat outputFormatter = new SimpleDateFormat(targetFormat, Locale.ENGLISH);
            outputFormatter.setTimeZone(targetTimeZone);

            return outputFormatter.format(parsedDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Cannot parse date: " + inputDate, e);
        }
    }

    /**
     * Validates that the input date matches the specified format.
     * @return True if the reformatted date matches the original input; false otherwise.
     */
    private static boolean isDateMatchingFormat(String inputDate, SimpleDateFormat inputFormat, Date reformattedInputDate) {
        String reformattedInput = inputFormat.format(reformattedInputDate);
        return reformattedInput.equals(inputDate);
    }

    public static String convertToStandardFormatGivenFormat(Date inputDate, String targetFormat, TimeZone targetTimeZone) {
        if (inputDate == null || targetFormat == null || targetTimeZone == null) {
            throw new IllegalArgumentException("Cannot format date: " + inputDate + " Due to missing required arguments.");
        }

        SimpleDateFormat outputFormatter = new SimpleDateFormat(targetFormat, Locale.ENGLISH);
        outputFormatter.setTimeZone(targetTimeZone);

        return outputFormatter.format(inputDate);
    }


}
