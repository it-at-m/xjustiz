package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content;

import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.schriftgutobjekte.Akte;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.schriftgutobjekte.Dokument;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SchriftgutContent {

    private String anschreiben;
    private List<Dokument> dokumente;
    private List<Akte> akten;

    public Optional<String> getAnschreiben() {
        return Optional.ofNullable(anschreiben);
    }

    public Optional<List<Dokument>> getDokumente() {
        return Optional.ofNullable(dokumente);
    }

    public Optional<List<Akte>> getAkten() {
        return Optional.ofNullable(akten);
    }
}
