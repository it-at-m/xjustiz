package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xjustiz0500straf.content.beteiligung.Beteiligter;
import de.muenchen.xjustiz.xjustiz0500straf.content.beteiligung.Beteiligung;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class GrunddatenBuilder {

    private final static String GRUNDDATEN_INSTANZDATEN_SACHGEBIET = "026";
    private final static String GRUNDDATEN_INSTANZDATEN_BEITEILIGUNG_ROLLENBEZEICHNUNG_ORGANISATION = "016";
    private final static String GRUNDDATEN_INSTANZDATEN_BEITEILIGUNG_ROLLENBEZEICHNUNG_PERSON = "040";
    private final static String GRUNDDATEN_VERFAHRENSDATEN_BETEILIGUNG_BETEILIGTER_ORGANISATION_BEZEICHNUNG = "Landeshauptstadt München Stadtkämmerei";
    private final static String GRUNDDATEN_VERFAHRENSDATEN_BETEILIGUNG_BETEILIGTER_ORGANISATION_BEZEICHNUNG_ANSCHRIFT_ANSCHRIFTSTYP = "003";
    private final static String GRUNDDATEN_VERFAHRENSDATEN_BETEILIGUNG_BETEILIGTER_ORGANISATION_BEZEICHNUNG_ANSCHRIFT_STRASSE = "Landsbergerstraße";
    private final static String GRUNDDATEN_VERFAHRENSDATEN_BETEILIGUNG_BETEILIGTER_ORGANISATION_BEZEICHNUNG_ANSCHRIFT_HAUSNUMMER = "36";
    private final static String GRUNDDATEN_VERFAHRENSDATEN_BETEILIGUNG_BETEILIGTER_ORGANISATION_BEZEICHNUNG_ANSCHRIFT_PLZ = "80339";
    private final static String GRUNDDATEN_VERFAHRENSDATEN_BETEILIGUNG_BETEILIGTER_ORGANISATION_BEZEICHNUNG_ANSCHRIFT_ORT = "München";

    private final static String GRUNDDATEN_VERFAHRENSDATEN_BETEILIGUNG_BETEILIGTER_ORGANISATION_BEZEICHNUNG_BANKVERBINDUNG = "DE86 7015 0000 0000 2030 00";


    public TypeGDSGrunddaten build() {


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
      /*
         Dynamisch : AuswahlInstanzbehoerde
                Die auszuwählende Instanzbehörde ist eine Station, die der Vorgang durchlaufen hat.
                Dies kann ein Gericht oder eine Staatsanwaltschaft, eine Justizbehörde, aber auch eine andere Behörde sein.
                Meist entspricht es dem Gericht (der Staatsanwaltschaft), an das (die) die Nachricht adressiert wird.
                Darüber hinaus können weitere Behörden (z.B. Gericht der Vorinstanz, Polizei in Strafverfahren),
                die den Vorgang/das Verfahren bereits bearbeitet haben, angegeben werden. -->
      */

        TypeGDSGrunddaten grunddaten = new TypeGDSGrunddaten();

        TypeGDSInstanzdaten instanzGericht = new TypeGDSInstanzdaten();
        TypeGDSBehoerde behoerde = new TypeGDSBehoerde();

        // Statistisch : Sachgebiet
        TypeGDSGrunddaten.Verfahrensdaten verfahrensdaten = new TypeGDSGrunddaten.Verfahrensdaten();
        TypeGDSInstanzdaten instanzSachgebiet = new TypeGDSInstanzdaten();
        instanzSachgebiet.setSachgebietszusatz(NachrichtenkopfBuilder.CHANGEIT);
        instanzSachgebiet.setAuswahlInstanzbehoerde(behoerde);

        CodeGDSSachgebietTyp3 sachgebiet = new CodeGDSSachgebietTyp3();
        sachgebiet.setCode(GRUNDDATEN_INSTANZDATEN_SACHGEBIET);
        sachgebiet.setListVersionID(NachrichtenkopfBuilder.CHANGEIT);

        instanzSachgebiet.setSachgebiet(sachgebiet);
        verfahrensdaten.getInstanzdatens().add(instanzSachgebiet);
        grunddaten.setVerfahrensdaten(verfahrensdaten);


        instanzGericht.setAuswahlInstanzbehoerde(behoerde);
        CodeGDSGerichteTyp3 gerichtInstanzBehoerde = new CodeGDSGerichteTyp3();
        gerichtInstanzBehoerde.setCode(NachrichtenkopfBuilder.NACHRICHTENKOPF_AUSWAHL_EMPFAENGER_GERICHT);
        gerichtInstanzBehoerde.setListVersionID(NachrichtenkopfBuilder.CHANGEIT);
        instanzGericht.getAuswahlInstanzbehoerde().setGericht(gerichtInstanzBehoerde);
        verfahrensdaten.getInstanzdatens().add(instanzGericht);


        // Beteiligte Organisation
        List<Beteiligung> beteiligte = new ArrayList<>();

        BigInteger rollennNummer = BigInteger.ONE;
        Integer rollenBezeichnungNummerOrganisation = 1;
        Integer beteiligtenNummer = 1;

        Beteiligung beteiligungOrganisation = new Beteiligung();
        beteiligungOrganisation.generateRolle().setLaufendeNummer(rollennNummer);
        beteiligungOrganisation.generateRolle().setRollenbezeichnung(rollenBezeichnungNummerOrganisation.toString());
        beteiligungOrganisation.generateRolle().setRollenbezeichnung(GRUNDDATEN_INSTANZDATEN_BEITEILIGUNG_ROLLENBEZEICHNUNG_ORGANISATION);

        beteiligungOrganisation.generateBeteiligter().setBeteiligtenNummer(beteiligtenNummer.toString());
        beteiligungOrganisation.generateBeteiligter().generateOrganisation().setBezeichnungAktuell(GRUNDDATEN_VERFAHRENSDATEN_BETEILIGUNG_BETEILIGTER_ORGANISATION_BEZEICHNUNG);

        beteiligungOrganisation.generateBeteiligter().generateOrganisation().generateAnschrift().setAnschriftsTyp(GRUNDDATEN_VERFAHRENSDATEN_BETEILIGUNG_BETEILIGTER_ORGANISATION_BEZEICHNUNG_ANSCHRIFT_ANSCHRIFTSTYP);
        beteiligungOrganisation.generateBeteiligter().generateOrganisation().generateAnschrift().setStrasse(GRUNDDATEN_VERFAHRENSDATEN_BETEILIGUNG_BETEILIGTER_ORGANISATION_BEZEICHNUNG_ANSCHRIFT_STRASSE);
        beteiligungOrganisation.generateBeteiligter().generateOrganisation().generateAnschrift().setHausnummer(GRUNDDATEN_VERFAHRENSDATEN_BETEILIGUNG_BETEILIGTER_ORGANISATION_BEZEICHNUNG_ANSCHRIFT_HAUSNUMMER);
        beteiligungOrganisation.generateBeteiligter().generateOrganisation().generateAnschrift().setPlz(GRUNDDATEN_VERFAHRENSDATEN_BETEILIGUNG_BETEILIGTER_ORGANISATION_BEZEICHNUNG_ANSCHRIFT_PLZ);
        beteiligungOrganisation.generateBeteiligter().generateOrganisation().generateAnschrift().setOrt(GRUNDDATEN_VERFAHRENSDATEN_BETEILIGUNG_BETEILIGTER_ORGANISATION_BEZEICHNUNG_ANSCHRIFT_ORT);
        beteiligungOrganisation.generateBeteiligter().generateOrganisation().setIban(GRUNDDATEN_VERFAHRENSDATEN_BETEILIGUNG_BETEILIGTER_ORGANISATION_BEZEICHNUNG_BANKVERBINDUNG);

        beteiligte.add(beteiligungOrganisation);

        // Betroffene/Beteiligte Person
        rollennNummer.add(BigInteger.ONE);
        Integer rollenBezeichnungNummerPerson = 1;
        ++beteiligtenNummer;

        Beteiligung affectedPerson = new Beteiligung();
        affectedPerson.generateRolle().setLaufendeNummer(rollennNummer);
        affectedPerson.generateRolle().setRollenbezeichnung(rollenBezeichnungNummerPerson.toString());
        affectedPerson.generateRolle().setRollenbezeichnung(GRUNDDATEN_INSTANZDATEN_BEITEILIGUNG_ROLLENBEZEICHNUNG_PERSON);

        affectedPerson.generateBeteiligter().setBeteiligtenNummer(beteiligtenNummer.toString());
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setVorname("Maximilian");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setNachname("Mustermann");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setTitel("Dr.");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setNamensvorsatz("von");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setGeburtsname("Müller");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateGeburt().setGeburtsdatum("2000-01-01");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateGeburt().setGeburtsort("Musterhausen");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().setGeschlecht("männlich");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateAnschrift().setAnschriftenzusatz("im Hinterhof");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateAnschrift().setStrasse("Musterstrasse");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateAnschrift().setHausnummer("1");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateAnschrift().setPostfachnummer("1");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateAnschrift().setPlz("11111");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateAnschrift().setOrt("Musterhausen");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateAnschrift().setWohnungsgeber("?");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateAnschrift().setStaat("000");

        beteiligte.add(affectedPerson);

        // Beteiligte dem Verfahren hinzufuegen
        beteiligte.forEach(b -> verfahrensdaten.getBeteiligungs().add(beteiligungBuilder(b)));

        return grunddaten;

    }

    private TypeGDSBeteiligung beteiligungBuilder(Beteiligung beteiligung) {

        // Dynamisch : Rollenbezeichnung
        TypeGDSBeteiligung xjustizBeteiligung = new TypeGDSBeteiligung();

        // Dynamische Rollennummer und Nummer
        beteiligung.getRolle().ifPresent(r -> {

            TypeGDSBeteiligung.Rolle rolle = new TypeGDSBeteiligung.Rolle();
            rolle.setRollennummer(r.getRollennummer());
            rolle.setNr(r.getLaufendeNummer());

            CodeGDSRollenbezeichnungTyp3 rollenbez = new CodeGDSRollenbezeichnungTyp3();
            rollenbez.setCode(r.getRollenbezeichnung());
            rollenbez.setListVersionID(NachrichtenkopfBuilder.CHANGEIT);
            rolle.setRollenbezeichnung(rollenbez);
            xjustizBeteiligung.getRolles().add(rolle);
        });


        beteiligung.getBeteiligter().ifPresent(b -> {
            if (b.getOrganisation().isPresent())
                xjustizBeteiligung.setBeteiligter(beteiligteOrganisation(b));
            else
                xjustizBeteiligung.setBeteiligter(beteiligtePerson(b));

        });
        return xjustizBeteiligung;

    }

    private static TypeGDSBeteiligter beteiligteOrganisation(Beteiligter b) {

        // Dynamisch Beteiligtennummer
        TypeGDSBeteiligter beteiligter = new TypeGDSBeteiligter();
        beteiligter.setBeteiligtennummer(b.getBeteiligtenNummer());

        b.getOrganisation().ifPresent(o -> {
            TypeGDSOrganisation organisation = new TypeGDSOrganisation();

            // Statisch : BezeichnungAktuell
            TypeGDSOrganisation.Bezeichnung bezeichnung = new TypeGDSOrganisation.Bezeichnung();
            bezeichnung.setBezeichnungAktuell(o.getBezeichnungAktuell());
            organisation.setBezeichnung(bezeichnung);
            TypeGDSBeteiligter.AuswahlBeteiligter auswahlBeteiligter = new TypeGDSBeteiligter.AuswahlBeteiligter();
            beteiligter.setAuswahlBeteiligter(auswahlBeteiligter);

            // Statisch : Anschrift
            o.getAnschrift().ifPresent(a -> {
                TypeGDSAnschrift anschrift = new TypeGDSAnschrift();
                CodeGDSAnschriftstyp anschriftstyp = new CodeGDSAnschriftstyp();
                anschriftstyp.setCode(a.getAnschriftsTyp());
                anschrift.setAnschriftstyp(anschriftstyp);
                anschrift.setStrasse(a.getStrasse());
                anschrift.setHausnummer(a.getHausnummer());
                anschrift.setPostleitzahl(a.getPlz());
                anschrift.setOrt(a.getOrt());
                organisation.getAnschrifts().add(anschrift);
            });

            // Statisch : Bankverbindung
            TypeGDSBankverbindung bankverbindung = new TypeGDSBankverbindung();
            bankverbindung.setIban(o.getIban());
            organisation.getBankverbindungs().add(bankverbindung);

            beteiligter.getAuswahlBeteiligter().setOrganisation(organisation);

        });

        return beteiligter;
    }

    private static TypeGDSBeteiligter beteiligtePerson(Beteiligter b) {

        // Dynamisch Beteiligtennummer
        TypeGDSBeteiligter beteiligter = new TypeGDSBeteiligter();
        beteiligter.setBeteiligtennummer(b.getBeteiligtenNummer());
        TypeGDSBeteiligter.AuswahlBeteiligter auswahlBeteiligter = new TypeGDSBeteiligter.AuswahlBeteiligter();
        beteiligter.setAuswahlBeteiligter(auswahlBeteiligter);

        b.getNatuerlichePerson().ifPresent(p -> {

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

            p.getAnschrift().ifPresent(a -> {
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
            beteiligter.getAuswahlBeteiligter().setNatuerlichePerson(person);
        });
        return beteiligter;
    }




}
