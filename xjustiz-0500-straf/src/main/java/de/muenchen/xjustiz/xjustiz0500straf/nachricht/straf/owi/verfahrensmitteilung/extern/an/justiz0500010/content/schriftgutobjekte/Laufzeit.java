package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.schriftgutobjekte;

import java.util.Optional;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Laufzeit {

    private final XMLGregorianCalendar beginn;
    private final XMLGregorianCalendar ende;

    public Optional<XMLGregorianCalendar> getBeginn() {
        return Optional.ofNullable(beginn);
    }

    public Optional<XMLGregorianCalendar> getEnde() {
        return Optional.ofNullable(ende);
    }

}
