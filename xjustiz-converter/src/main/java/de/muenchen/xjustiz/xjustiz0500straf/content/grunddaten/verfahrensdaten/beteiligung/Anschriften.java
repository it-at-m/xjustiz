package de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class Anschriften {

    protected List<Anschrift> anschriften;

    public Optional<List<Anschrift>> getAnschriften() {
        return Optional.ofNullable(anschriften);
    }

    public void addAnschrift(Anschrift anschrift) {
        if (anschriften == null) {
            anschriften = new ArrayList<>();
        }
        anschriften.add(anschrift);
    }
}
