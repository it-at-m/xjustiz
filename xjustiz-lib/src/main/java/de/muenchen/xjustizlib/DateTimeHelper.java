package de.muenchen.xjustizlib;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class DateTimeHelper {

    public static final String DD_MM_YYYY = "dd.MM.yyyy";

    /**
     * Format one date string format to another string format.
     *
     * @param date as string.
     * @param dateFormat string pattern.
     * @param targetFormat expected target format.
     * @return Formatted string in target format.
     */
    public static String convertTargetStringFormat(String date, String dateFormat, String targetFormat) {

        log.debug("dateFormatConverter : date '{}' dateFormat '{}' targetFormat '{}'", date, dateFormat, targetFormat);

        if (date == null || date.isEmpty()) {
            return null;
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern(targetFormat);

        LocalDate dateInstance = LocalDate.parse(date, dateFormatter);
        return dateInstance.format(targetFormatter);

    }

    /**
     * Format string to local date.
     *
     * @param date as string.
     * @param dateFormat string pattern.
     * @return LocalDate
     */
    public static LocalDate convertLocalDate(String date, String dateFormat) {

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(dateFormat);
        return LocalDate.parse(date, inputFormatter);

    }

    /**
     * Convert standard string format "yyyy-MM-dd" to XMLGregorianCalendar.
     *
     * @param dateString
     * @param dateFormat string pattern.
     * @param timeZoneId https://en.wikipedia.org/wiki/List_of_tz_database_time_zones.
     * @return XMLGregorianCalendar
     * @throws DatatypeConfigurationException
     */
    public static XMLGregorianCalendar convertXMLGregorianCalendar(String dateString, String dateFormat, String timeZoneId)
            throws DatatypeConfigurationException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZoneId));

        Date date = sdf.parse(dateString);

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone(timeZoneId));
        gregorianCalendar.setTime(date);

        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }

    /**
     * xJustiz XML validation expects '\d{1,2}(:\d{2}){0,2}' for Typ 'Type.GDS.Zeitangabe'
     *
     * Formats the given hour, minute, and second into a GDS-compliant time string.
     * Validates hour range based on the provided locale (12-hour or 24-hour format).
     * Empty or null values for minute/second are allowed and result in a shorter format.
     * Throws IllegalArgumentException only for invalid numeric values.
     *
     * Note:
     * Since the XML elements can be set to minOccurs="0",
     * missing time values ​​are not treated as errors, but rather terminated with 'null'.
     *
     * @param hour The hour as a String, can be null or empty
     * @param minute The minute as a String (0-59), can be null or empty
     * @param second The second as a String (0-59), can be null or empty
     * @param locale The locale for validation (determines 12-hour or 24-hour format)
     * @return Formatted time string in "HH", "HH:mm", or "HH:mm:ss" format
     * @throws IllegalArgumentException if hour, minute, or second are out of range
     */
    public static String xJustizTypeGDSZeitangabeFormat(String hour, String minute, String second, Locale locale) throws IllegalArgumentException {
        log.debug("TimeFormatUtils.formatTime : hour '{}' minute '{}' second '{}', locale '{}'", hour, minute, second, locale);

        // Trim whitespace from inputs
        hour = StringUtils.trim(hour);
        minute = StringUtils.trimToNull(minute);
        second = StringUtils.trimToNull(second);

        if (StringUtils.isEmpty(hour)) {
            return null;
        }

        int hourInt;
        try {
            hourInt = Integer.parseInt(hour);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Hour must be a valid integer.", e);
        }

        // Determine if the locale uses 12-hour or 24-hour format
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, locale);
        boolean is24HourFormat = !(timeFormat instanceof SimpleDateFormat &&
                ((SimpleDateFormat) timeFormat).toPattern().matches(".*[ha].*"));
        int maxHour = is24HourFormat ? 23 : 11; // 0-23 for 24h, 0-11 for 12h

        if (hourInt < 0 || hourInt > maxHour) {
            throw new IllegalArgumentException(
                    String.format("Hour must be between 0 and %d for the given locale.", maxHour));
        }

        // Parse and validate minute (if provided)
        Integer minuteInt = null;
        if (minute != null) {
            try {
                minuteInt = Integer.parseInt(minute);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Minute must be a valid integer.", e);
            }
            if (minuteInt < 0 || minuteInt > 59) {
                throw new IllegalArgumentException("Minute must be between 0 and 59.");
            }
        }

        // Parse and validate second (if provided)
        Integer secondInt = null;
        if (second != null) {
            try {
                secondInt = Integer.parseInt(second);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Second must be a valid integer.", e);
            }
            if (secondInt < 0 || secondInt > 59) {
                throw new IllegalArgumentException("Second must be between 0 and 59.");
            }
        }

        // Build the formatted time string
        String formatted;
        if (minuteInt == null) {
            formatted = String.format("%02d", hourInt);
        } else if (secondInt == null) {
            formatted = String.format("%02d:%02d", hourInt, minuteInt);
        } else {
            formatted = String.format("%02d:%02d:%02d", hourInt, minuteInt, secondInt);
        }

        log.debug("TimeFormatUtils.formatTime : formatted '{}'", formatted);

        // Validate the formatted string against the pattern
        if (!formatted.matches("\\d{1,2}(:\\d{2}){0,2}")) {
            throw new IllegalArgumentException("Formatted time does not match the required pattern.");
        }

        return formatted;
    }

}
