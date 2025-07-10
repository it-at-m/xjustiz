package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xjustiz0500straf.config.NachrichtenProperty;
import de.muenchen.xjustiz.xjustiz0500straf.content.GrunddatenContent;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GrunddatenBuilder {

    private final NachrichtenProperty nachrichtenProperty;
    private final Geschlecht geschlecht;

    private final IncrementCounters incrementCounters = new IncrementCounters();

    public TypeGDSGrunddaten build(GrunddatenContent grunddatenContent) {

        incrementCounters.reset();

        /*
           The organization can be defined statically once in the properties or transferred dynamically with the natural persons.
        */
        if (nachrichtenProperty.isOrganisationConfiguredInApplicationProperties())
              grunddatenContent.getBeteiligungen().ifPresent( b -> b.add(generateBeteiligungOrganisation()) );

        /**
         * Grunddaten
         * - Verfahrensdaten
         *      - Instanzdaten
         *          - Sachgebiet
         *          - AuswahlInstanzbehoerde
         * - Beteiligung
         *  - Rolle
         *      - Rollennummer
         *      - Rollenbezeichnung
         *  - Beteiligter
         *      - Beteiligtennummer
         *      - AuswahlBeteiligter
         */

        TypeGDSGrunddaten grunddaten = new TypeGDSGrunddaten();

        TypeGDSInstanzdaten instanzGericht = new TypeGDSInstanzdaten();
        TypeGDSBehoerde behoerde = new TypeGDSBehoerde();

        TypeGDSGrunddaten.Verfahrensdaten verfahrensdaten = new TypeGDSGrunddaten.Verfahrensdaten();
        TypeGDSInstanzdaten instanzSachgebiet = new TypeGDSInstanzdaten();
        instanzSachgebiet.setAuswahlInstanzbehoerde(behoerde);

        CodeGDSSachgebietTyp3 sachgebiet = new CodeGDSSachgebietTyp3();
        sachgebiet.setCode(nachrichtenProperty.getCodelisten().get("gds-sachgebiet").currentCodelistValueWithKey("owi-sachen"));
        sachgebiet.setListVersionID(nachrichtenProperty.getCodelisten().get("gds-sachgebiet").getCurrentVersion());

        instanzSachgebiet.setSachgebiet(sachgebiet);
        verfahrensdaten.getInstanzdatens().add(instanzSachgebiet);
        grunddaten.setVerfahrensdaten(verfahrensdaten);

        instanzGericht.setAuswahlInstanzbehoerde(behoerde);
        CodeGDSGerichteTyp3 gerichtInstanzBehoerde = new CodeGDSGerichteTyp3();
        gerichtInstanzBehoerde.setCode(nachrichtenProperty.getCodelisten().get("gds-gerichte").currentCodelistValueWithKey("amtsgericht-muenchen"));
        gerichtInstanzBehoerde.setListVersionID(nachrichtenProperty.getCodelisten().get("gds-gerichte").getCurrentVersion());
        instanzGericht.getAuswahlInstanzbehoerde().setGericht(gerichtInstanzBehoerde);
        verfahrensdaten.getInstanzdatens().add(instanzGericht);

        grunddatenContent.getBeteiligungen().ifPresent(beteiligungen -> beteiligungen.forEach(beteiligung -> verfahrensdaten.getBeteiligungs().add(beteiligungBuilder(beteiligung))));

        return grunddaten;
    }

    private TypeGDSBeteiligung beteiligungBuilder(Beteiligung beteiligung) {

        TypeGDSBeteiligung xjustizBeteiligung = new TypeGDSBeteiligung();

        beteiligung.getRollen().ifPresent(rollen -> {
            rollen.forEach(r -> {

                TypeGDSBeteiligung.Rolle rolle = new TypeGDSBeteiligung.Rolle();
                rolle.setRollennummer(String.valueOf(incrementCounters.incrementEntireXmlRollennummer()));
                rolle.setNr(incrementCounters.incrementRollenbezeichnungCounter(r.getRollenbezeichnung()));

                CodeGDSRollenbezeichnungTyp3 rollenbez = new CodeGDSRollenbezeichnungTyp3();
                rollenbez.setCode(r.getRollenbezeichnung());
                rollenbez.setListVersionID(nachrichtenProperty.getCodelisten().get("gds-rollenbezeichnung").getCurrentVersion());
                rolle.setRollenbezeichnung(rollenbez);
                xjustizBeteiligung.getRolles().add(rolle);
            });
        });

        beteiligung.getBeteiligter().ifPresent(b -> {

            if (b.getOrganisation().isPresent())
                xjustizBeteiligung.setBeteiligter(beteiligteOrganisation(b));
            else
                xjustizBeteiligung.setBeteiligter(beteiligtePerson(b));

        });
        return xjustizBeteiligung;
    }

    private TypeGDSBeteiligter beteiligteOrganisation(Beteiligter b) {

        TypeGDSBeteiligter beteiligter = new TypeGDSBeteiligter();
        beteiligter.setBeteiligtennummer(String.valueOf(incrementCounters.incrementBeteiligtenNummer()));

        b.getOrganisation().ifPresent(o -> {
            TypeGDSOrganisation organisation = new TypeGDSOrganisation();

            beteiligter.setAuswahlBeteiligter(new TypeGDSBeteiligter.AuswahlBeteiligter());

            TypeGDSOrganisation.Bezeichnung bezeichnung = new TypeGDSOrganisation.Bezeichnung();
            bezeichnung.setBezeichnungAktuell(o.getBezeichnungAktuell());
            organisation.setBezeichnung(bezeichnung);

            o.getAnschriften().ifPresent(anschriften -> {
                anschriften.forEach(a -> {
                    TypeGDSAnschrift anschrift = new TypeGDSAnschrift();
                    CodeGDSAnschriftstyp anschriftstyp = new CodeGDSAnschriftstyp();
                    anschriftstyp.setCode(nachrichtenProperty.getCodelisten().get("gds-anschriftstyp").currentCodelistValueWithKey("dienst-geschaeftsanschrift"));
                    anschriftstyp.setListVersionID(nachrichtenProperty.getCodelisten().get("gds-anschriftstyp").getCurrentVersion());
                    anschrift.setAnschriftstyp(anschriftstyp);
                    anschrift.setStrasse(a.getStrasse());
                    anschrift.setHausnummer(a.getHausnummer());
                    anschrift.setPostleitzahl(a.getPlz());
                    anschrift.setOrt(a.getOrt());
                    organisation.getAnschrifts().add(anschrift);
                });
            });

            TypeGDSBankverbindung bankverbindung = new TypeGDSBankverbindung();
            bankverbindung.setIban(o.getIban());
            organisation.getBankverbindungs().add(bankverbindung);

            beteiligter.getAuswahlBeteiligter().setOrganisation(organisation);
        });

        return beteiligter.getAuswahlBeteiligter() != null ? beteiligter : null;
    }

    private TypeGDSBeteiligter beteiligtePerson(Beteiligter b) {

        TypeGDSBeteiligter beteiligter = new TypeGDSBeteiligter();
        beteiligter.setBeteiligtennummer(String.valueOf(incrementCounters.incrementBeteiligtenNummer()));

        b.getNatuerlichePerson().ifPresent(p -> {

            beteiligter.setAuswahlBeteiligter(new TypeGDSBeteiligter.AuswahlBeteiligter());

            TypeGDSNatuerlichePerson person = new TypeGDSNatuerlichePerson();

            p.getVollerName().ifPresent(vn -> {
                TypeGDSNameNatuerlichePerson name = new TypeGDSNameNatuerlichePerson();
                name.setVorname(vn.getVorname());
                name.setNachname(vn.getNachname());
                name.setTitel(vn.getTitel());
                name.setNamensvorsatz(vn.getNamensvorsatz());
                name.setGeburtsname(vn.getGeburtsname());
                person.setVollerName(name);
            });

            p.getGeburt().ifPresent(g -> {
                TypeGDSGeburt geburt = new TypeGDSGeburt();
                geburt.setGeburtsdatum(g.getGeburtsdatum());
                TypeGDSOrtsangabe ortsangabe = new TypeGDSOrtsangabe();
                ortsangabe.setOrt(g.getGeburtsort());
                geburt.setGeburtsort(ortsangabe);
                person.setGeburt(geburt);
            });

            p.getGeschlecht().ifPresent( g-> {
              CodeGDSGeschlecht geschlecht = new CodeGDSGeschlecht();
              geschlecht.setCode(this.geschlecht.getGeschlechtCode(g));
              person.setGeschlecht(geschlecht);
            });

            p.getAnschriften().ifPresent(anschriften -> {
                anschriften.forEach(a -> {
                    TypeGDSAnschrift anschrift = new TypeGDSAnschrift();
                    anschrift.setPostfachnummer(a.getPostfachnummer());
                    anschrift.setStrasse(a.getStrasse());
                    anschrift.setHausnummer(a.getHausnummer());
                    anschrift.setPostleitzahl(a.getPlz());
                    anschrift.setOrt(a.getOrt());
                    anschrift.setWohnungsgeber(a.getWohnungsgeber());
                    CodeGDSStaatenTyp3 staatenTyp = new CodeGDSStaatenTyp3();
                    staatenTyp.setCode(nachrichtenProperty.getCodelisten().get("bjf-staat").currentCodelistValueWithKey("deutschland"));
                    staatenTyp.setListVersionID(nachrichtenProperty.getCodelisten().get("bjf-staat").getCurrentVersion());
                    anschrift.setStaat(staatenTyp);
                    anschrift.getAnschriftenzusatzs().add(a.getAnschriftenzusatz());
                    person.getAnschrifts().add(anschrift);

                });
            });
            beteiligter.getAuswahlBeteiligter().setNatuerlichePerson(person);
        });
        return beteiligter.getAuswahlBeteiligter() != null ? beteiligter : null;
    }

    private Beteiligung generateBeteiligungOrganisation() {

        Beteiligung beteiligungOrganisation = new Beteiligung();

        var rolle = new Rolle();
        rolle.setRollenbezeichnung(nachrichtenProperty.getCodelisten().get("gds-rollenbezeichnung").currentCodelistValueWithKey("antragsteller"));
        beteiligungOrganisation.addRolle(rolle);

        beteiligungOrganisation.generateBeteiligter().generateOrganisation().setBezeichnungAktuell(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungAktuell());

        var anschrift = new Anschrift();
        anschrift.setStrasse(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungAnschriftStrasse());
        anschrift.setHausnummer(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungAnschriftHausnummer());
        anschrift.setPlz(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungAnschriftPlz());
        anschrift.setOrt(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungAnschriftOrt());
        beteiligungOrganisation.generateBeteiligter().generateOrganisation().addAnschrift(anschrift);

        beteiligungOrganisation.generateBeteiligter().generateOrganisation().setIban(nachrichtenProperty.getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation().getBezeichnungBankverbindung());

        return beteiligungOrganisation;
    }

}
