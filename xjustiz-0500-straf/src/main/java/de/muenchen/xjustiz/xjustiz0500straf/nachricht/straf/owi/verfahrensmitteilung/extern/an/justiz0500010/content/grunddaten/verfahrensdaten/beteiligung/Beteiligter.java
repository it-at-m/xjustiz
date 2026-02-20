package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.grunddaten.verfahrensdaten.beteiligung;

import java.util.Optional;
import lombok.Data;

@Data
public class Beteiligter {

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
