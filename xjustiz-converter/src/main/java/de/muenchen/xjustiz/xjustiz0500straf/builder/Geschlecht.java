package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.xjustiz0500straf.config.NachrichtenProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Geschlecht {

    private final NachrichtenProperty nachrichtenProperty;

    public enum GESCHLECHT {UNBEKANNT, MAENNLICH, WEIBLICH, DIVERS, SAECHLICH};

    public String getGeschlechtCode(GESCHLECHT geschlecht) {
        return nachrichtenProperty.getCodelisten().get("gds-geschlecht").currentCodelistValueWithKey(geschlecht.name().toLowerCase());
    }
}
