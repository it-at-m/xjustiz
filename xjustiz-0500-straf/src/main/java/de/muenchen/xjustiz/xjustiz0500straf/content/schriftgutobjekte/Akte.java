package de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte;

import java.util.Optional;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * FachspezifischeDaten are supplemented in SchriftgutObjektBuilder.
 */
@Getter
@RequiredArgsConstructor
public class Akte {

    @NonNull
    private final Identifikation identifikation;
    private final Laufzeit laufzeit;
    private final AnwendungspezifischeErweiterung anwendungspezifischeErweiterung;
    private final FachspezifischeDatenAkte fachspezifischeDatenAkte;

    public Optional<Identifikation> getIdentifikation() {
        return Optional.ofNullable(identifikation);
    }

    public Optional<Laufzeit> getLaufzeit() {
        return Optional.ofNullable(laufzeit);
    }

    public Optional<AnwendungspezifischeErweiterung> getAnwendungspezifischeErweiterung() {
        return Optional.ofNullable(anwendungspezifischeErweiterung);
    }

    public Optional<FachspezifischeDatenAkte> getFachspezifischeDatenAkte() {
        return Optional.ofNullable(fachspezifischeDatenAkte);
    }

}
