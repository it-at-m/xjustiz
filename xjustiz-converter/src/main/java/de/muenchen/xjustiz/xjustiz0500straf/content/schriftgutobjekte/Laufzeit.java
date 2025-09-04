package de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte;

import javax.xml.datatype.XMLGregorianCalendar;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Laufzeit {

    @NonNull
    private final XMLGregorianCalendar beginn;
    @NonNull
    private final XMLGregorianCalendar ende;
}
