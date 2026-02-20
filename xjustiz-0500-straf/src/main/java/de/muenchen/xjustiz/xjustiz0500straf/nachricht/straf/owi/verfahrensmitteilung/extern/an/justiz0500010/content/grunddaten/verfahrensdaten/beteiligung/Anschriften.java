package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.grunddaten.verfahrensdaten.beteiligung;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Data;

@Data
public class Anschriften {

    protected List<Anschrift> anschriftenList;

    public Optional<List<Anschrift>> getAnschriftenList() {
        return Optional.ofNullable(anschriftenList);
    }

    public void addAnschrift(final Anschrift anschrift) {
        if (anschriftenList == null) {
            anschriftenList = new ArrayList<>();
        }
        anschriftenList.add(anschrift);
    }
}
