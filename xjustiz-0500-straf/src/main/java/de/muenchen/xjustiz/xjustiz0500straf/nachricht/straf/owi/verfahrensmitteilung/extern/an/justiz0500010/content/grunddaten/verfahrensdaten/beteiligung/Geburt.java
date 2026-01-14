package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.grunddaten.verfahrensdaten.beteiligung;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Data;

@Data
public class Geburt {

    private String geburtsort;

    private LocalDate geburtsdatum;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void setGeburtsdatum(final String datumString) {
        this.geburtsdatum = LocalDate.parse(datumString, FORMATTER);
    }

    public String getGeburtsdatum() {
        return geburtsdatum.format(FORMATTER);
    }

}
