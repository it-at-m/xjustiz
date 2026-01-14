package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IncrementCounters {

    private int beteiligtenNummer = 0;
    private int entireXmlRollennummer = 0;

    private final Map<String, BigInteger> rollenbezeichnungCounter = new HashMap<>();

    public int incrementBeteiligtenNummer() {
        return ++beteiligtenNummer;
    }

    public int incrementEntireXmlRollennummer() {
        return ++entireXmlRollennummer;
    }

    public void reset() {
        beteiligtenNummer = 0;
        entireXmlRollennummer = 0;
        rollenbezeichnungCounter.clear();
    }

    public BigInteger incrementRollenbezeichnungCounter(final String rollenbezeichnung) {

        if (rollenbezeichnungCounter.containsKey(rollenbezeichnung)) {
            BigInteger counter = rollenbezeichnungCounter.get(rollenbezeichnung);
            counter = counter.add(BigInteger.ONE);
            rollenbezeichnungCounter.put(rollenbezeichnung, counter);
            return counter;
        } else {
            rollenbezeichnungCounter.put(rollenbezeichnung, BigInteger.ONE);
            return BigInteger.ONE;
        }
    }

}
