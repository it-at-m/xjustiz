package de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.xml.datatype.XMLGregorianCalendar;

@Getter
@RequiredArgsConstructor
public class Laufzeit {

    @NonNull
    private final XMLGregorianCalendar beginn;
    @NonNull
    private final XMLGregorianCalendar ende;
}
