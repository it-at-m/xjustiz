package de.muenchen.xjustiz.xjustiz0500straf.content;

import de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte.Akte;
import de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte.Dokument;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SchriftgutContent {

    private Optional<String> anschreiben;
    private Optional<List<Dokument>> dokumente;
    private Optional<List<Akte>> akten;

}
