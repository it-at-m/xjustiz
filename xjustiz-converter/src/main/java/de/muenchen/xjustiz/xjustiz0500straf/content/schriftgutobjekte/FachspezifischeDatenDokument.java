package de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte;

import de.muenchen.xjustiz.xoev.codelisten.XoevCodeGDSDokumentklasse;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class FachspezifischeDatenDokument {

    @NonNull
    private final XoevCodeGDSDokumentklasse dokumentklasse;
    @NonNull
    private final String anzeigename;
    @NonNull
    private final List<Datei> dateien;

}
