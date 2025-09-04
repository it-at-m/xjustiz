package de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte;

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
    @NonNull
    private final Laufzeit laufzeit;
    @NonNull
    private final AnwendungspezifischeErweiterung anwendungspezifischeErweiterung;
    @NonNull
    private final FachspezifischeDatenAkte fachspezifischeDatenAkte;

}
