package de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Data;

@Data
public class Beteiligung {

    private List<Rolle> rollen;
    private Beteiligter beteiligter;

    public Optional<List<Rolle>> getRollen() {
        return Optional.ofNullable(rollen);
    }

    public void addRolle(final Rolle rolle) {
        if (this.rollen == null) {
            this.rollen = new ArrayList<>();
        }
        this.rollen.add(rolle);
    }

    public Optional<Beteiligter> getBeteiligter() {
        return Optional.ofNullable(beteiligter);
    }

    public Beteiligter generateBeteiligter() {
        if (beteiligter == null) {
            beteiligter = new Beteiligter();
        }
        return beteiligter;
    }

}
