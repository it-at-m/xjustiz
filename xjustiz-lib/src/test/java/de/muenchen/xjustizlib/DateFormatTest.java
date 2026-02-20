package de.muenchen.xjustizlib;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import org.junit.jupiter.api.Test;

public class DateFormatTest {

    @Test
    public void test_localDate() {

        assertEquals(LocalDate.of(2020, 10, 25), DateTimeHelper.convertLocalDate("25.10.2020", "dd.MM.yyyy"));
        assertEquals(LocalDate.of(2020, 10, 25), DateTimeHelper.convertLocalDate("10252020", "MMddyyyy"));
        assertEquals(LocalDate.of(2020, 10, 25), DateTimeHelper.convertLocalDate("20201025", "yyyyMMdd"));

    }

    @Test
    public void test_targetFormat() {

        assertEquals("2020-10-25", DateTimeHelper.convertTargetStringFormat("25.10.2020", "dd.MM.yyyy", "yyyy-MM-dd"));
        assertEquals("2020.10.25", DateTimeHelper.convertTargetStringFormat("10252020", "MMddyyyy", "yyyy.MM.dd"));

    }

    @Test
    public void test_xmlGregorianCalendar() throws DatatypeConfigurationException, ParseException {

        GregorianCalendar gregorianCalendar = new GregorianCalendar(2020, Calendar.OCTOBER, 20, 0, 0, 0);
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));

        assertEquals(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar),
                DateTimeHelper.convertXMLGregorianCalendar("20.10.2020", "dd.MM.yyyyy", "Europe/Berlin"));

    }

}
