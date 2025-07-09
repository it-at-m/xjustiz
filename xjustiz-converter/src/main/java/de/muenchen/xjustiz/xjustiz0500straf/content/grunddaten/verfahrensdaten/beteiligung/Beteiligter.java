package de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class Beteiligter {

    @Getter
    @Setter
    private String beteiligtenNummer;

    private Organisation organisation;

    private NatuerlichePerson natuerlichePerson;

    public Optional<Organisation> getOrganisation() {
        return Optional.ofNullable(organisation);
    }

    public Organisation generateOrganisation() {

        if (organisation == null) {
            organisation = new Organisation();
        }
        return organisation;
    }

    public Optional<NatuerlichePerson> getNatuerlichePerson() {
        return Optional.ofNullable(natuerlichePerson);
    }

    public NatuerlichePerson generateNatuerlichePerson() {

        if (natuerlichePerson == null) {
            natuerlichePerson = new NatuerlichePerson();
        }
        return natuerlichePerson;
    }
}
