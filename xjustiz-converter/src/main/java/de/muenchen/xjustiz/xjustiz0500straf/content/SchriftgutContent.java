package de.muenchen.xjustiz.xjustiz0500straf.content;

import de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte.Akte;
import de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte.Dokument;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class SchriftgutContent {

   private Optional<String> anschreiben;
   private Optional<List<Dokument>> dokumente;
   private Optional<List<Akte>> akten;

}
