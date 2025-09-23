package de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class FachspezifischeDatenAkte {

    private final AktenzeichenStrukuriert aktenzeichenAuswahlAktenzeichenAktenzeichenStrukturiert;
    private final String freitext;
    private final boolean aktenzeichenArt;

    public Optional<AktenzeichenStrukuriert> getAktenzeichenAuswahlAktenzeichenAktenzeichenStrukturiert() {
        return Optional.ofNullable(aktenzeichenAuswahlAktenzeichenAktenzeichenStrukturiert);
    }

    public Optional<String> getFreitext() {
        return Optional.ofNullable(freitext);
    }
}
