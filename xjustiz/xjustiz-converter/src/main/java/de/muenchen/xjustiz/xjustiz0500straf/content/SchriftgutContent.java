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

    private Optional<String> anschreiben = Optional.empty();
    private Optional<List<Dokument>> dokumente = Optional.empty();
    private Optional<List<Akte>> akten = Optional.empty();

}
