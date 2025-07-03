package de.muenchen.xjustiz.xjustiz0500straf.content.beteiligung;

import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
public class Beteiligung {

    private Rolle rolle;
    private Beteiligter beteiligter;

    public Optional<Rolle> getRolle() {
        return Optional.ofNullable(rolle);
    }

    public Rolle generateRolle() {
        if (rolle == null) {
            rolle = new Rolle();
        }
        return rolle;
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
