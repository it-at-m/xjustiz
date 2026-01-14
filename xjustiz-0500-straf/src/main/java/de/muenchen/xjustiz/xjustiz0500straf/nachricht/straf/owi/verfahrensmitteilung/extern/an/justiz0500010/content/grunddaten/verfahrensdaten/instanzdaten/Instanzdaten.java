package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.grunddaten.verfahrensdaten.instanzdaten;

import java.util.Map;
import lombok.Data;

@Data
public class Instanzdaten {

    private Map<Instanztype, Aktenzeichen> auswahlInstanzbehoerde;

}
