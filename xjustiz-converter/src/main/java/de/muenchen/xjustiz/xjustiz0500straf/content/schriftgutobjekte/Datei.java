package de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Getter
@RequiredArgsConstructor
public class Datei {

    @NonNull
    private final String dateiname;
    @NonNull
    private final BigInteger versionsnummer;

}
