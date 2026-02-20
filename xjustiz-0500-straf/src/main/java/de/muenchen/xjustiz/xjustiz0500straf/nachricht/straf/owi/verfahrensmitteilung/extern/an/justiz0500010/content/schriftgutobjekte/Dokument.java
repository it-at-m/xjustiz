package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.schriftgutobjekte;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Dokument {

    @NonNull
    private final Identifikation identifikation;
    @NonNull
    private final FachspezifischeDatenDokument fachspezifischeDatenDokument;

}
