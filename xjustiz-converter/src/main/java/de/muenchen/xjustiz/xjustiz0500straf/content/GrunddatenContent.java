package de.muenchen.xjustiz.xjustiz0500straf.content;

import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Beteiligung;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.instanzdaten.Aktenzeichen;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.instanzdaten.Instanztype;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@AllArgsConstructor
public class GrunddatenContent {

    private final List<Beteiligung> beteiligungen;
    private final Map<Instanztype, Aktenzeichen> instanzdaten;

    public Optional<List<Beteiligung>> getBeteiligungen() {
        return Optional.ofNullable(beteiligungen);
    }

    public Optional<Map<Instanztype, Aktenzeichen>> getInstanzdaten() {
        return Optional.ofNullable(instanzdaten);
    }

}
