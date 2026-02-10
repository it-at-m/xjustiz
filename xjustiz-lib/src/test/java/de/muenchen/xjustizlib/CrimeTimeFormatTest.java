package de.muenchen.xjustizlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Locale;
import org.junit.jupiter.api.Test;

public class CrimeTimeFormatTest {

    @Test
    void test_formatHour() {

        assertNull(DateTimeHelper.xJustizTypeGDSZeitangabeFormat(null, null, null, Locale.GERMANY));

        assertEquals("14", DateTimeHelper.xJustizTypeGDSZeitangabeFormat("14", null, "", Locale.GERMANY));
        assertEquals("14", DateTimeHelper.xJustizTypeGDSZeitangabeFormat("14", "  ", "", Locale.GERMANY));
        assertEquals("14", DateTimeHelper.xJustizTypeGDSZeitangabeFormat("14", "  ", "01", Locale.GERMANY));
        assertEquals("04", DateTimeHelper.xJustizTypeGDSZeitangabeFormat("04", null, null, Locale.GERMANY));
        assertEquals("04", DateTimeHelper.xJustizTypeGDSZeitangabeFormat("4", null, null, Locale.GERMANY));
        assertEquals("20", DateTimeHelper.xJustizTypeGDSZeitangabeFormat("20", "  ", "", Locale.GERMANY));

        assertThrows(IllegalArgumentException.class, () -> DateTimeHelper.xJustizTypeGDSZeitangabeFormat("25", "0", "30", Locale.GERMANY));
        assertThrows(IllegalArgumentException.class, () -> DateTimeHelper.xJustizTypeGDSZeitangabeFormat("14", null, null, Locale.US));
        assertThrows(IllegalArgumentException.class, () -> DateTimeHelper.xJustizTypeGDSZeitangabeFormat("-1", null, null, Locale.US));
    }

    @Test
    void test_formatHourMinute() {
        assertEquals("01:01", DateTimeHelper.xJustizTypeGDSZeitangabeFormat("1", "01", "", Locale.GERMANY));
        assertEquals("12:01", DateTimeHelper.xJustizTypeGDSZeitangabeFormat("12", "1", null, Locale.GERMANY));
        assertEquals("14:14", DateTimeHelper.xJustizTypeGDSZeitangabeFormat("14", "14  ", "  ", Locale.GERMANY));

        assertThrows(IllegalArgumentException.class, () -> DateTimeHelper.xJustizTypeGDSZeitangabeFormat("07", "60", "30", Locale.GERMANY));
        assertThrows(IllegalArgumentException.class, () -> DateTimeHelper.xJustizTypeGDSZeitangabeFormat("15", "00", null, Locale.US));
        assertThrows(IllegalArgumentException.class, () -> DateTimeHelper.xJustizTypeGDSZeitangabeFormat("8", "-1", null, Locale.US));
    }

    @Test
    void test_formatHourMinuteSecond() {
        assertEquals("12:13:14", DateTimeHelper.xJustizTypeGDSZeitangabeFormat("12", "13", "14", Locale.GERMANY));
        assertEquals("20:20:20", DateTimeHelper.xJustizTypeGDSZeitangabeFormat("20", " 20  ", "20  ", Locale.GERMANY));
        assertEquals("10:30:59", DateTimeHelper.xJustizTypeGDSZeitangabeFormat("10", "30", "59", Locale.US));

        assertThrows(IllegalArgumentException.class, () -> DateTimeHelper.xJustizTypeGDSZeitangabeFormat("20", "30", "60", Locale.GERMANY));
        assertThrows(IllegalArgumentException.class, () -> DateTimeHelper.xJustizTypeGDSZeitangabeFormat("16", "60", "30", Locale.GERMANY));
        assertThrows(IllegalArgumentException.class, () -> DateTimeHelper.xJustizTypeGDSZeitangabeFormat("15", "15", "15", Locale.US));
        assertThrows(IllegalArgumentException.class, () -> DateTimeHelper.xJustizTypeGDSZeitangabeFormat("11", "15", "-15", Locale.US));
    }

}
