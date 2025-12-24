package de.muenchen.xjustiz.xjustiz0500straf.content;

import de.muenchen.xjustiz.xjustiz0500straf.content.fachdaten.Tatort;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.datatype.XMLGregorianCalendar;
import lombok.*;

@Data
@NoArgsConstructor
public class FachdatenContent {

    private Optional <LocalDate> erlassdatum = Optional.empty();
    private XMLGregorianCalendar rechtskraftdatum;

    private Double geldbusse;
    private Double auslagen;

    private Optional <LocalDateTime> datumUhrzeit = Optional.empty();

    private Optional <LocalDateTime> anfangsDatumUhrzeit = Optional.empty();

    private Optional <LocalDateTime> endeDatumUhrzeit = Optional.empty();

    private final List<Tatort> tatorte = new ArrayList<>();

}
