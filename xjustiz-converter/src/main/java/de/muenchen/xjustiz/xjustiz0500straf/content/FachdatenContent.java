package de.muenchen.xjustiz.xjustiz0500straf.content;

import de.muenchen.xjustiz.xjustiz0500straf.content.fachdaten.Tatort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

import javax.xml.datatype.XMLGregorianCalendar;

@Data
@NoArgsConstructor
public class FachdatenContent {

    private LocalDate erlassdatum;
    private XMLGregorianCalendar rechtskraftdatum;

    private Double geldbusse;
    private Double auslagen;

    private LocalDateTime datumUhrzeit;

    private LocalDateTime anfangsDatumUhrzeit;

    private LocalDateTime endeDatumUhrzeit;

    private final List<Tatort> tatorte = new ArrayList<>();

}
