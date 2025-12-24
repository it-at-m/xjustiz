package de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte;

import de.muenchen.xjustiz.xoev.codelisten.XoevCodeGDSDokumentklasse;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
