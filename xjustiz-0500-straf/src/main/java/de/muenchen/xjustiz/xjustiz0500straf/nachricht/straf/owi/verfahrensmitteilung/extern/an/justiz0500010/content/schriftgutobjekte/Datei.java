package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.schriftgutobjekte;

import java.math.BigInteger;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Datei {

    @NonNull
    private final String dateiname;
    @NonNull
    private final BigInteger versionsnummer;

}
