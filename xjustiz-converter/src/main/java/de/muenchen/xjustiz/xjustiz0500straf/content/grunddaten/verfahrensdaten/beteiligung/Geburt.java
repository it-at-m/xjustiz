package de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class Geburt {

    private String geburtsort;

    private LocalDate geburtsdatum;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void setGeburtsdatum(String datumString) {
        this.geburtsdatum = LocalDate.parse(datumString, FORMATTER);
    }

    public String getGeburtsdatum() {
        return geburtsdatum.format(FORMATTER);
    }

}
