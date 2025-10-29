package de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.instanzdaten;

import lombok.Data;
import java.util.Map;

@Data
public class Instanzdaten {

    private Map<Instanztype, Aktenzeichen> auswahlInstanzbehoerde;

}
