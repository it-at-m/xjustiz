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

    private LocalDate erlassdatum;
    private XMLGregorianCalendar rechtskraftdatum;

    private Double geldbusse;
    private Double auslagen;

    private LocalDateTime datumUhrzeit;

    private String anfangDatum;

    private String anfangUhrzeit;

    private String endeDatum;

    private String endeUhrzeit;

    private final List<Tatort> tatorte = new ArrayList<>();

    public Optional<String> getAnfangDatum() {
        return Optional.ofNullable(anfangDatum);
    }

    public Optional<String> getAnfangUhrzeit() {
        return Optional.ofNullable(anfangUhrzeit);
    }

    public Optional<String> getEndeDatum() {
        return Optional.ofNullable(endeDatum);
    }

    public Optional<String> getEndeUhrzeit() {
        return Optional.ofNullable(endeUhrzeit);
    }

}
