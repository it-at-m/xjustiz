package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content;

import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.fachdaten.Tatort;
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

    private LocalDateTime anfangsDatumUhrzeit;

    private LocalDateTime endeDatumUhrzeit;

    private final List<Tatort> tatorte = new ArrayList<>();

    public Optional<LocalDate> getErlassdatum() {
        return Optional.ofNullable(erlassdatum);
    }

    public Optional<LocalDateTime> getDatumUhrzeit() {
        return Optional.ofNullable(datumUhrzeit);
    }

    public Optional<LocalDateTime> getAnfangsDatumUhrzeit() {
        return Optional.ofNullable(anfangsDatumUhrzeit);
    }

    public Optional<LocalDateTime> getEndeDatumUhrzeit() {
        return Optional.ofNullable(endeDatumUhrzeit);
    }
}
