package de.muenchen.xjustiz.xjustiz0500straf.content;

import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Beteiligung;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GrunddatenContent {

    private final List<Beteiligung> beteiligungen;

    public Optional<List<Beteiligung>> getBeteiligungen() {
        return Optional.ofNullable(beteiligungen);
    }

}
