package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.fachdaten;

import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.grunddaten.verfahrensdaten.beteiligung.Anschrift;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class Tatort extends Anschrift {

    @Getter
    protected final List<StrasseHausnummer> strasseHausnummer = new ArrayList<>();

}
