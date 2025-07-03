package de.muenchen.xjustiz.config.contents.beteiligte;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class Organisation {

    @Getter
    @Setter
    private String bezeichnungAktuell;
    @Getter
    @Setter
    private String iban;

    private Anschrift anschrift  ;

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
