package de.muenchen.xjustiz.config.contents.beteiligte;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
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
