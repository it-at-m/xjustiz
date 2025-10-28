package de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte;

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
