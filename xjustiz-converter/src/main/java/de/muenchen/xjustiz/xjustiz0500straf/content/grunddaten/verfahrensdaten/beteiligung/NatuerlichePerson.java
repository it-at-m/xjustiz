package de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NatuerlichePerson extends Anschriften {

    private VollerName vollerName;

    private Geburt geburt;

    @Getter
    @Setter
    private String geschlecht;

    public Optional<VollerName> getVollerName() {
        return Optional.ofNullable(vollerName);
    }

    public VollerName generateVollerName() {

        if (vollerName == null) {
            vollerName = new VollerName();
        }
        return vollerName;
    }

    public Optional<Geburt> getGeburt() {
        return Optional.ofNullable(geburt);
    }

    public Geburt generateGeburt() {
        if (geburt == null) {
            geburt = new Geburt();
        }
        return geburt;
    }

}
