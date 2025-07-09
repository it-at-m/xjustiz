package de.muenchen.xjustiz.xjustiz0500straf.content.fachdaten;

import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Anschrift;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public class Tatort extends Anschrift {

    @Getter
    protected final List<StrasseHausnummer> strasseHausnummer = new ArrayList<>();

}
