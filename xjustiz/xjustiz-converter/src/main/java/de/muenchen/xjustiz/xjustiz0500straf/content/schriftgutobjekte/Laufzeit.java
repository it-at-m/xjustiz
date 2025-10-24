package de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte;

import javax.xml.datatype.XMLGregorianCalendar;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class Laufzeit {


    private final XMLGregorianCalendar beginn;
    private final XMLGregorianCalendar ende;

    public Optional<XMLGregorianCalendar> getBeginn() {
        return Optional.ofNullable(beginn);
    }
    public Optional<XMLGregorianCalendar> getEnde() {return Optional.ofNullable(ende);}

}
