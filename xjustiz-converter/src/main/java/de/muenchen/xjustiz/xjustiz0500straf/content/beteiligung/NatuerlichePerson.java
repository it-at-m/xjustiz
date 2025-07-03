package de.muenchen.xjustiz.xjustiz0500straf.content.beteiligung;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class NatuerlichePerson {

    private VollerName vollerName;

    private Geburt geburt;

    @Getter
    @Setter
    private String geschlecht;

    private Anschrift anschrift;

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

    public Optional<Anschrift> getAnschrift() {
        return Optional.ofNullable(anschrift);
    }

    public Anschrift generateAnschrift() {
        if (anschrift == null) {
            anschrift = new Anschrift();
        }
        return anschrift;
    }
}
