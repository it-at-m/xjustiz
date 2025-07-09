package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xjustiz0500straf.content.GrunddatenContent;
import de.muenchen.xjustiz.xjustiz0500straf.config.VerfahrensdatenProperty;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Anschrift;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Beteiligter;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Beteiligung;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Rolle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GrunddatenBuilder {

    private final VerfahrensdatenProperty verfahrensdatenProperty;
    IncrementCounters incrementCounters = new IncrementCounters();

    public TypeGDSGrunddaten build(List<Beteiligung> beteiligungen) {

        GrunddatenContent grunddatenContent = new GrunddatenContent();
        incrementCounters.reset();

        /*
           The organization can be defined statically once in the properties or transferred dynamically with the natural persons.
        */
        if (verfahrensdatenProperty.isOrganisationConfiguredInApplicationProperties())
            grunddatenContent.getBeteiligungen().add(generateBeteiligungOrganisation());

       beteiligungen.forEach(b -> grunddatenContent.getBeteiligungen().add(b));

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
        instanzSachgebiet.setSachgebietszusatz(NachrichtenkopfBuilder.CHANGEIT);
        instanzSachgebiet.setAuswahlInstanzbehoerde(behoerde);

        CodeGDSSachgebietTyp3 sachgebiet = new CodeGDSSachgebietTyp3();
        sachgebiet.setCode(verfahrensdatenProperty.getInstanzdaten().getCodelisten().get("gds-sachgebiet").currentCodelistValueWithKey("sachgebiet"));
        sachgebiet.setListVersionID(verfahrensdatenProperty.getInstanzdaten().getCodelisten().get("gds-sachgebiet").getCurrentVersion());

        instanzSachgebiet.setSachgebiet(sachgebiet);
        verfahrensdaten.getInstanzdatens().add(instanzSachgebiet);
        grunddaten.setVerfahrensdaten(verfahrensdaten);

        instanzGericht.setAuswahlInstanzbehoerde(behoerde);
        CodeGDSGerichteTyp3 gerichtInstanzBehoerde = new CodeGDSGerichteTyp3();
        gerichtInstanzBehoerde.setCode(verfahrensdatenProperty.getInstanzdaten().getCodelisten().get("gds-gericht").currentCodelistValueWithKey("auswahl-empfaenger-gericht"));
        gerichtInstanzBehoerde.setListVersionID(verfahrensdatenProperty.getInstanzdaten().getCodelisten().get("gds-gericht").getCurrentVersion());
        instanzGericht.getAuswahlInstanzbehoerde().setGericht(gerichtInstanzBehoerde);
        verfahrensdaten.getInstanzdatens().add(instanzGericht);

        grunddatenContent.getBeteiligungen().forEach(b -> verfahrensdaten.getBeteiligungs().add(beteiligungBuilder(b)));

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
                rollenbez.setListVersionID(verfahrensdatenProperty.getCodelisten().get("gds-rollenbezeichnung").getCurrentVersion());
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
                    anschriftstyp.setCode(a.getAnschriftsTyp());
                    anschriftstyp.setListVersionID(a.getListVersionID());
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
                    staatenTyp.setCode(a.getStaat());
                    staatenTyp.setListVersionID(NachrichtenkopfBuilder.CHANGEIT);
                    anschrift.setStaat(staatenTyp);
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
        rolle.setRollenbezeichnung(verfahrensdatenProperty.getCodelisten().get("gds-rollenbezeichnung").currentCodelistValueWithKey("organisation"));
        beteiligungOrganisation.addRolle(rolle);

        beteiligungOrganisation.generateBeteiligter().generateOrganisation().setBezeichnungAktuell(verfahrensdatenProperty.getBeteiligung().getOrganisation().getBezeichnungAktuell());

        var anschrift = new Anschrift();
        anschrift.setListVersionID(verfahrensdatenProperty.getBeteiligung().getOrganisation().getCodelisten().get("gds-anschriftstyp").getCurrentVersion());
        anschrift.setAnschriftsTyp(verfahrensdatenProperty.getBeteiligung().getOrganisation().getCodelisten().get("gds-anschriftstyp").currentCodelistValueWithKey("anschriftstyp"));
        anschrift.setStrasse(verfahrensdatenProperty.getBeteiligung().getOrganisation().getBezeichnungAnschriftStrasse());
        anschrift.setHausnummer(verfahrensdatenProperty.getBeteiligung().getOrganisation().getBezeichnungAnschriftHausnummer());
        anschrift.setPlz(verfahrensdatenProperty.getBeteiligung().getOrganisation().getBezeichnungAnschriftPlz());
        anschrift.setOrt(verfahrensdatenProperty.getBeteiligung().getOrganisation().getBezeichnungAnschriftOrt());
        beteiligungOrganisation.generateBeteiligter().generateOrganisation().addAnschrift(anschrift);

        beteiligungOrganisation.generateBeteiligter().generateOrganisation().setIban(verfahrensdatenProperty.getBeteiligung().getOrganisation().getBezeichnungBankverbindung());

        return beteiligungOrganisation;
    }

}
