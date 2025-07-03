package de.muenchen.xjustiz;

import de.muenchen.xjustiz.config.contents.Fachdaten;
import de.muenchen.xjustiz.config.contents.beteiligte.Beteiligter;
import de.muenchen.xjustiz.config.contents.beteiligte.Beteiligung;
import de.muenchen.xjustiz.config.contents.fachdaten.StrasseHausnummer;
import de.muenchen.xjustiz.config.contents.fachdaten.Tatort;
import de.muenchen.xjustiz.generated.*;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class JustizMessageBuilder implements Processor {

    private final static String CHANGEIT = "TODO";

    private final static String NACHRICHTENKOPF_AUSWAHL_ABSENDER_SONSTIGE = "SKA 2.222";
    private final static String NACHRICHTENKOPF_AUSWAHL_EMPFAENGER_GERICHT = "D2601";
    private final static String NACHRICHTENKOPF_HERSTELLERINFORMATION_PRODUKT = "KVUweb";

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

    private final static String SCHRIFTGUTOBJEKTE_DOKUMENT_FACHSPEZIFISCHE_DATEN_DOKUMENTKLASSE= "016";

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody(build());
    }


    public NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 build() {

        var calendarNow = Calendar.getInstance();

        NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 message0500010 = new NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010();
        TypeGDSNachrichtenkopf nachrichtenkopf = new TypeGDSNachrichtenkopf();
        TypeGDSGrunddaten grunddaten = new TypeGDSGrunddaten();
        TypeGDSSchriftgutobjekte schriftobjekte = new TypeGDSSchriftgutobjekte();
        NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.Fachdaten fachdaten = new NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.Fachdaten();

        message0500010.setNachrichtenkopf(nachrichtenkopf);
        message0500010.setGrunddaten(grunddaten);
        message0500010.setSchriftgutobjekte(schriftobjekte);
        message0500010.setFachdaten(fachdaten);

        /**
         *
         * Nachrichtenkopf
         * - Aktenzeichen
         * - AuswahlAktenkennzeichen
         * - Erstellzeitpunkt
         * - AuswahlAbsender
         * - AuswahlEmpfaenger
         * - Ereignis
         * - Herstellerinformation
         *
         */

        // xJustizVersion
        nachrichtenkopf.setXjustizVersion("3.6.2");

        // Dynamisch : Aktenzeichen : KVU: "EH-KASSZ" (Kassenzeichen) aus KVU Daten
        TypeGDSNachrichtenkopf.Absender absender = new TypeGDSNachrichtenkopf.Absender();
        absender.setAktenzeichen(CHANGEIT);
        nachrichtenkopf.setAbsender(absender);

        //       Statisch : AuswahlAktenzeichen
        TypeGDSNachrichtenkopf.Empfaenger empfaenger = new TypeGDSNachrichtenkopf.Empfaenger();
        TypeGDSNachrichtenkopf.Empfaenger.AuswahlAktenzeichen auswahlAktenzeichen = new TypeGDSNachrichtenkopf.Empfaenger.AuswahlAktenzeichen();
        auswahlAktenzeichen.setAktenzeichenNeu(true);
        empfaenger.setAuswahlAktenzeichen(auswahlAktenzeichen);
        nachrichtenkopf.setEmpfaenger(empfaenger);

        //Dynamisch : Erstellzeitpunkt
        nachrichtenkopf.setErstellungszeitpunkt(calendarNow);

        // Statisch : Auswahl_Absender
        TypeGDSKommunikationspartner.AuswahlKommunikationspartner kommunikationspartnerSka = new TypeGDSKommunikationspartner.AuswahlKommunikationspartner();
        kommunikationspartnerSka.setSonstige(NACHRICHTENKOPF_AUSWAHL_ABSENDER_SONSTIGE);
        TypeGDSKommunikationspartner kommunikationsPartnerAbsender = new TypeGDSKommunikationspartner();
        kommunikationsPartnerAbsender.setAuswahlKommunikationspartner(kommunikationspartnerSka);
        nachrichtenkopf.getAbsender().setInformationen(kommunikationsPartnerAbsender);

        // Statisch : Auswahl_Empfaenger
        TypeGDSKommunikationspartner.AuswahlKommunikationspartner kommunikationspartnerGericht = new TypeGDSKommunikationspartner.AuswahlKommunikationspartner();
        CodeGDSGerichteTyp3 gerichtKommunikationsparter = new CodeGDSGerichteTyp3();
        gerichtKommunikationsparter.setCode(NACHRICHTENKOPF_AUSWAHL_EMPFAENGER_GERICHT);
        gerichtKommunikationsparter.setListVersionID(CHANGEIT);
        kommunikationspartnerGericht.setGericht(gerichtKommunikationsparter);
        TypeGDSKommunikationspartner kommunikationsPartnerEmpfaenger = new TypeGDSKommunikationspartner();
        kommunikationsPartnerEmpfaenger.setAuswahlKommunikationspartner(kommunikationspartnerGericht);
        nachrichtenkopf.getEmpfaenger().setInformationen(kommunikationsPartnerEmpfaenger);

        /*
           Dynamisch : Eigene_Nachricht_ID
           Hier ist eine eindeutige Identifikation der bei diesem Übermittlungsvorgang erstellten Nachricht anzugeben, um spätere Referenzen zu ermöglichen.
           Von EAI-XML-Generator zu vergebene, fortlaufende ID.
        */
        message0500010.getNachrichtenkopf().getAbsender().setEigeneNachrichtenID("3f9a9e40-0b4a-4d65-8e24-89f9fce51f97");
        CodeGDSEreignisTyp3 ereignis = new CodeGDSEreignisTyp3();

        //  Statisch : Ereignis
        ereignis.setCode(CHANGEIT);
        ereignis.setListVersionID(CHANGEIT);
        message0500010.getNachrichtenkopf().getEreignises().add(ereignis);

        // Statisch : Herstellerinformation
        TypeGDSHerstellerinformation herstellerinformation = new TypeGDSHerstellerinformation();
        herstellerinformation.setNameDesProdukts(NACHRICHTENKOPF_HERSTELLERINFORMATION_PRODUKT);
        herstellerinformation.setHerstellerDesProdukts(CHANGEIT);
        herstellerinformation.setVersion(CHANGEIT);
        message0500010.getNachrichtenkopf().setHerstellerinformation(herstellerinformation);

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
        TypeGDSInstanzdaten instanzGericht = new TypeGDSInstanzdaten();
        TypeGDSBehoerde behoerde = new TypeGDSBehoerde();

        // Statistisch : Sachgebiet
        TypeGDSGrunddaten.Verfahrensdaten verfahrensdaten = new TypeGDSGrunddaten.Verfahrensdaten();
        TypeGDSInstanzdaten instanzSachgebiet = new TypeGDSInstanzdaten();
        instanzSachgebiet.setSachgebietszusatz(CHANGEIT);
        instanzSachgebiet.setAuswahlInstanzbehoerde(behoerde);

        CodeGDSSachgebietTyp3 sachgebiet = new CodeGDSSachgebietTyp3();
        sachgebiet.setCode(GRUNDDATEN_INSTANZDATEN_SACHGEBIET);
        sachgebiet.setListVersionID(CHANGEIT);

        instanzSachgebiet.setSachgebiet(sachgebiet);
        verfahrensdaten.getInstanzdatens().add(instanzSachgebiet);
        grunddaten.setVerfahrensdaten(verfahrensdaten);


        instanzGericht.setAuswahlInstanzbehoerde(behoerde);
        CodeGDSGerichteTyp3 gerichtInstanzBehoerde = new CodeGDSGerichteTyp3();
        gerichtInstanzBehoerde.setCode(NACHRICHTENKOPF_AUSWAHL_EMPFAENGER_GERICHT);
        gerichtInstanzBehoerde.setListVersionID(CHANGEIT);
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

        Fachdaten fachdatenContent = new Fachdaten();
        fachdatenContent.setAnfangsDatumUhrzeit(LocalDateTime.of(2024, 10, 1, 12, 0));
        fachdatenContent.setEndeDatumUhrzeit(LocalDateTime.of(2024, 10, 1, 13, 5));
        Tatort tatortContent = new Tatort();
        tatortContent.setAnschriftsTyp("006");
        tatortContent.getStrasseHausnummer().add(new StrasseHausnummer("KVU EH-TATSTR1", "KVU EH-TATHNR1"));
        tatortContent.getStrasseHausnummer().add(new StrasseHausnummer("KVU EH-TATSTR2", "KVU EH-TATHNR2"));
        tatortContent.setOrt("KVU EH-TATORT");
        tatortContent.setOrtsbeschreibung("KVU ???");

        fachdatenContent.getTatorte().add(tatortContent);


        /**
         *  Fachdaten
         */
        TypeSTRAFOWIBussgeldbescheid bussgeldbescheid = new TypeSTRAFOWIBussgeldbescheid();

        TypeSTRAFOWITat tat = new TypeSTRAFOWITat();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        tat.setAnfangsdatum(fachdatenContent.getAnfangsDatumUhrzeit().format(dateFormatter));
        tat.setEndedatum(fachdatenContent.getEndeDatumUhrzeit().format(dateFormatter));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        tat.setAnfangsuhrzeit(fachdatenContent.getAnfangsDatumUhrzeit().format(timeFormatter));
        tat.setEndeuhrzeit(fachdatenContent.getEndeDatumUhrzeit().format(timeFormatter));

        fachdatenContent.getTatorte().forEach(t-> {
            TypeSTRAFTatort tatort = new TypeSTRAFTatort();
            tatort.setOrtsbeschreibung(t.getOrtsbeschreibung());

            //    Angabe von zwei Strassen + Hausnummern möglich. In Beispieldatensatz auch tlw. gegeben
            t.getStrasseHausnummer().forEach( sh -> {

                TypeSTRAFTatort.Anschrift anschrift = new TypeSTRAFTatort.Anschrift();
                CodeGDSAnschriftstyp anschriftTyp = new CodeGDSAnschriftstyp();
                anschriftTyp.setCode(t.getAnschriftsTyp());
                anschriftTyp.setListVersionID("3.0");
                anschrift.setAnschriftstyp(anschriftTyp);

                anschrift.setStrasse(sh.getStrasse());
                anschrift.setHausnummer(sh.getHausnummer());
                anschrift.setOrt(t.getOrt());


                tatort.getAnschrifts().add(anschrift);
            });
            tat.getTatorts().add(tatort);
        });

        bussgeldbescheid.setTat(tat);
        fachdaten.setBussgeldbescheid(bussgeldbescheid);


        /**
         *  Schriftgutobjekte
         *  !!!!   Kommen aus der eAkte. Müssen erst im 2.ten Schritt nach dem erneuten abholen
         *         aus der eAkte hinzugefügt werden. Erst dann sind die erforderlichen Schriftgut.Attribut
         *         Inhalte verfügbar.
         *         Die Implementierung der Schriftgutobjekt muss getrennt nachrichtenkopf, grunddaten und fachdaten
         *         erfolgen
         */

        List<TypeGDSDokument> dokumente = new ArrayList<>();

        TypeGDSDokument dokument = new TypeGDSDokument();

        // Anschreiben
        TypeGDSRefSGO anschreiben = new TypeGDSRefSGO();
        anschreiben.setRefSgo("CEEF2150-F915-1F1F-1177-906D00000000");
        schriftobjekte.setAnschreiben(anschreiben);

        // Identifikation
        TypeGDSXdomeaIdentifikationObjektType identifikation = new TypeGDSXdomeaIdentifikationObjektType();
        /*
            xDOMEA: xdomea:information-information-0101.schriftgutobjekt.vorgang.dokument.identifikation.id
            Bsp. für ID: CEEF2150-F915-1F1F-1177-906D00000000
         */
        identifikation.setId("CEEF2150-F915-1F1F-1177-906D00000000");
        /*
            xDOMEA: xdomea:information-information-0101.schriftgutobjekt.vorgang.dokument.identifikation.nummerImUebergeordnetenContainer
            Bsp. für nummerImUebergeordnetenContainer: 1
         */
        identifikation.setNummerImUebergeordnetenContainer(BigInteger.ONE);
        dokument.setIdentifikation(identifikation);

        // Fachspezifische Daten
        TypeGDSDokument.XjustizFachspezifischeDaten fachspezifischeDaten = new TypeGDSDokument.XjustizFachspezifischeDaten();

        /* Dokumentenklasse

            xDOMEA: xdomea:information-information-0101.schriftgutobjekt.vorgang.dokument.version.format.name.code -> DOCX
            BRE:
            Schriftgutobjekt OKV - Dokumentklasse: "016", Bestandteiltyp: "001" (Original) gemäß FB (Erik Weber)
            Schriftgutobjekt BUS Urschrift - "017", Bestandteiltyp: "002" (Repräsentant) gemäß FB (Erik Weber)
            Siehe Codeliste GDS.Dokumentklasse_1.4.xlsx
         */
        CodeGDSDokumentklasseTyp3 dokumentklasse = new CodeGDSDokumentklasseTyp3();
        dokumentklasse.setCode(SCHRIFTGUTOBJEKTE_DOKUMENT_FACHSPEZIFISCHE_DATEN_DOKUMENTKLASSE);
        dokumentklasse.setListVersionID(CHANGEIT);
        fachspezifischeDaten.setDokumentklasse(dokumentklasse);

        /* Anzeigename
            xDOMEA: xdomea:information-information-0101.schriftgutobjekt.vorgang.dokument.version.format.primaerdokument.dateiname
            Bsp. für anzeigename: CEEF2150-F915-1F1F-1177-908D00000000_Neufassung von Micro_1.docx
         */
        fachspezifischeDaten.setAnzeigename("CEEF2150-F915-1F1F-1177-908D00000000_Neufassung von Micro_1.docx");

        // Datei
        List<TypeGDSDokument.XjustizFachspezifischeDaten.Datei> dateien = new ArrayList<>();
        TypeGDSDokument.XjustizFachspezifischeDaten.Datei datei = new TypeGDSDokument.XjustizFachspezifischeDaten.Datei();
        /* Dateiname
              xDOMEA: xdomea:information-information-0101.schriftgutobjekt.vorgang.dokument.version.format.primaerdokument.dateiname
              Bsp. für anzeigename: CEEF2150-F915-1F1F-1177-908D00000000_Neufassung von Micro_1.docx
        */
        datei.setDateiname("CEEF2150-F915-1F1F-1177-908D00000000_Neufassung von Micro_1.docx");

        /* Bestandteil
            TODO BRE: Bitte prüfen ob Code 002 Repräsentant richtig. Übertragung im beBPo muss per .pdf stattfinden, daher Vorschlag 002.
            Codeliste: GDS.Bestandteiltyp_2.4.xlsx
         */
        CodeGDSBestandteiltyp bestandteiltyp = new CodeGDSBestandteiltyp();
        bestandteiltyp.setCode("002");
        datei.setBestandteil(bestandteiltyp);

        /* Versionsnummer
             xDOMEA: xdomea:information-information-0101.schriftgutobjekt.vorgang.dokument.version.nummer
             Bsp. für versionsnummer: 1
         */
        datei.setVersionsnummer(BigInteger.ONE);

        /* Erstellungszeitpunkt    alias   Veraktungsdatum
            xDOMEA: xdomea:information-information-0101.schriftgutobjekt.vorgang.dokument.version.format.primaerdokument.datumuhrzeit
         */
       // fachspezifischeDaten.setVeraktungsdatum(new XMLGregorianCalendar());

        /*
                @TODO Fehlende Attribute ergänzen ...
         */

        dateien.add(datei);

        dateien.forEach(d -> fachspezifischeDaten.getDateis().add(d));

        dokument.setXjustizFachspezifischeDaten(fachspezifischeDaten);
        dokumente.add(dokument);

        dokumente.forEach(d -> schriftobjekte.getDokuments().add(d));




        // Final message
        return message0500010;

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
            rollenbez.setListVersionID(CHANGEIT);
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
                staatenTyp.setListVersionID(CHANGEIT);
                anschrift.setStaat(staatenTyp);
                person.getAnschrifts().add(anschrift);
            });
            beteiligter.getAuswahlBeteiligter().setNatuerlichePerson(person);
        });
        return beteiligter;
    }


}
