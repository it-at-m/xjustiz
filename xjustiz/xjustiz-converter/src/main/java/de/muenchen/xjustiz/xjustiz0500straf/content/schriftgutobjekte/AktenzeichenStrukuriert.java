package de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AktenzeichenStrukuriert {

    @NonNull
    private final String sachgebietsschluessel;
    @NonNull
    private final String zusatzkennung;
    @NonNull
    private final String abteilung;
    @NonNull
    private final String laufendeNummer;
    @NonNull
    private final String jahr;

}
