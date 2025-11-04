package de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.instanzdaten;

import java.util.Map;
import lombok.Data;

@Data
public class Instanzdaten {

    private Map<Instanztype, Aktenzeichen> auswahlInstanzbehoerde;

}
