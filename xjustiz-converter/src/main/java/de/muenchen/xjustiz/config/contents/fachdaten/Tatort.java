package de.muenchen.xjustiz.config.contents.fachdaten;

import de.muenchen.xjustiz.config.contents.beteiligte.Anschrift;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public class Tatort extends Anschrift {

    @Getter
    protected final List<StrasseHausnummer> strasseHausnummer = new ArrayList<>();

}
