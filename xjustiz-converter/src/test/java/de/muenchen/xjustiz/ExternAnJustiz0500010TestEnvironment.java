package de.muenchen.xjustiz;

import de.muenchen.xjustiz.generated.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010;
import de.muenchen.xjustiz.xjustiz0500straf.content.FachdatenContent;
import de.muenchen.xjustiz.xjustiz0500straf.content.SchriftgutContent;
import de.muenchen.xjustiz.xjustiz0500straf.content.fachdaten.StrasseHausnummer;
import de.muenchen.xjustiz.xjustiz0500straf.content.fachdaten.Tatort;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Anschrift;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Beteiligung;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Rolle;
import de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte.*;
import de.muenchen.xjustiz.xoev.codelisten.*;
import jakarta.xml.bind.JAXBContext;
import java.io.StringReader;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

public class ExternAnJustiz0500010TestEnvironment {

    protected Beteiligung createPersonSubjectToCoerceiveDetention() {
        Beteiligung beteiligung = new Beteiligung();
        var rolle = new Rolle();

        rolle.setRollenbezeichnung(XoevCodeGDSRollenbezeichnungTyp3.BETROFFENER.getDescriptor());
        beteiligung.addRolle(rolle);

        beteiligung.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setVorname(NatuerlichePerson.VORNAME);
        beteiligung.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setNachname(NatuerlichePerson.NACHNAME);
        beteiligung.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setTitel(NatuerlichePerson.TITEL);
        beteiligung.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setNamensvorsatz(NatuerlichePerson.NAMENSVORSATZ);
        beteiligung.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setGeburtsname(NatuerlichePerson.GEBURTSNAME);
        beteiligung.generateBeteiligter().generateNatuerlichePerson().generateGeburt().setGeburtsdatum(NatuerlichePerson.GEBURTSDATUM);
        beteiligung.generateBeteiligter().generateNatuerlichePerson().generateGeburt().setGeburtsort(NatuerlichePerson.GEBURTSSORT);
        beteiligung.generateBeteiligter().generateNatuerlichePerson().setGeschlecht(XoevGeschlecht.MAENNLICH);

        var anschrift = new Anschrift();
        anschrift.setAnschriftenzusatz(NatuerlichePerson.ANSCHRIFTENZUATZ);
        anschrift.setStrasse(NatuerlichePerson.STRASSE);
        anschrift.setHausnummer(NatuerlichePerson.HAUSNUMMER);
        anschrift.setPostfachnummer(NatuerlichePerson.POSTFACHNUMMER);
        anschrift.setPlz(NatuerlichePerson.PLZ);
        anschrift.setOrt(NatuerlichePerson.ORT);
        anschrift.setWohnungsgeber(NatuerlichePerson.WOHNUNGSGEBER);
        anschrift.setStaat(XoevCodeGDSStaatenTyp3.DEUTSCHLAND.getDescriptor());
        beteiligung.generateBeteiligter().generateNatuerlichePerson().addAnschrift(anschrift);
        return beteiligung;
    }

    protected Beteiligung createApplicant() {

        Beteiligung beteiligung = new Beteiligung();

        var rolle = new Rolle();
        rolle.setRollenbezeichnung(XoevCodeGDSRollenbezeichnungTyp3.BUSSGELDEMPFAENGER.getDescriptor());
        beteiligung.addRolle(rolle);

        beteiligung.generateBeteiligter().generateOrganisation().setBezeichnungAktuell("Created Bezeichnung Aktuell");

        var anschrift = new Anschrift();
        anschrift.setStrasse("Created Strasse");
        anschrift.setHausnummer("Created Hausnummer");
        anschrift.setPlz("Created Plz");
        anschrift.setOrt("Created Ort");
        anschrift.setAnschriftstyp(XoevCodeGDSAnschriftstypen.DIENST_GESCHAEFTSANSCHRIFT.getDescriptor());
        beteiligung.generateBeteiligter().generateOrganisation().addAnschrift(anschrift);

        beteiligung.generateBeteiligter().generateOrganisation().setIban("Created Iban");

        return beteiligung;
    }

    protected NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 parseXML(String xml) throws Exception {
        JAXBContext context = JAXBContext.newInstance(NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.class);
        return (NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010) context.createUnmarshaller().unmarshal(new StringReader(xml));
    }

    protected static class NatuerlichePerson {

        final static String VORNAME = "Maximilian";
        final static String NACHNAME = "Mustermann";
        final static String STRASSE = "Musterstrasse";
        final static String HAUSNUMMER = "1";
        final static String POSTFACHNUMMER = "1234";
        final static String TITEL = "Dr.";
        final static String NAMENSVORSATZ = "von";
        final static String GEBURTSNAME = "Mueller";
        final static String GEBURTSDATUM = "2000-01-01";
        final static String GEBURTSSORT = "Musterhausen";
        final static String ANSCHRIFTENZUATZ = "im Hinterhof";
        final static String PLZ = "1234";
        final static String ORT = "Musterhausen";
        final static String WOHNUNGSGEBER = "???";

    }

    protected FachdatenContent createFachdaten() {

        FachdatenContent fachdatenContent = new FachdatenContent();
        fachdatenContent.setAnfangsDatumUhrzeit(LocalDateTime.of(2024, 10, 1, 12, 0));
        fachdatenContent.setEndeDatumUhrzeit(LocalDateTime.of(2024, 10, 1, 13, 5));
        Tatort tatortContent = new Tatort();
        tatortContent.getStrasseHausnummer().add(new StrasseHausnummer("KVU EH-TATSTR1", "KVU EH-TATHNR1"));
        tatortContent.getStrasseHausnummer().add(new StrasseHausnummer("KVU EH-TATSTR2", "KVU EH-TATHNR2"));
        tatortContent.setOrt("KVU EH-TATORT");
        tatortContent.setOrtsbeschreibung("KVU ???");

        fachdatenContent.getTatorte().add(tatortContent);
        return fachdatenContent;
    }

    protected List<Beteiligung> xmlValidationErrorMissingNachname() {

        Beteiligung defendant = new Beteiligung();

        defendant.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setVorname(NatuerlichePerson.VORNAME);
        defendant.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setTitel(ExternAnJustiz0500010TestEnvironment.NatuerlichePerson.TITEL);
        defendant.generateBeteiligter().generateNatuerlichePerson().generateVollerName()
                .setNamensvorsatz(ExternAnJustiz0500010TestEnvironment.NatuerlichePerson.NAMENSVORSATZ);
        defendant.generateBeteiligter().generateNatuerlichePerson().generateVollerName()
                .setGeburtsname(ExternAnJustiz0500010TestEnvironment.NatuerlichePerson.GEBURTSNAME);
        defendant.generateBeteiligter().generateNatuerlichePerson().generateGeburt()
                .setGeburtsdatum(ExternAnJustiz0500010TestEnvironment.NatuerlichePerson.GEBURTSDATUM);
        defendant.generateBeteiligter().generateNatuerlichePerson().generateGeburt()
                .setGeburtsort(ExternAnJustiz0500010TestEnvironment.NatuerlichePerson.GEBURTSSORT);
        defendant.generateBeteiligter().generateNatuerlichePerson().setGeschlecht(XoevGeschlecht.MAENNLICH);

        return new ArrayList<>(List.of(defendant));
    }

    protected SchriftgutContent createSchriftgutAktenzeichenStrukuriert() {

        SchriftgutContent schriftgutContent = new SchriftgutContent();
        schriftgutContent.setAnschreiben(Optional.of("CEEF2150-F915-1F1F-1176-906D00000000"));

        schriftgutContent.setDokumente(Optional.of(createDocuments()));
        schriftgutContent.setAkten(Optional.of(createDossiersIdentifikationLaufzeitErweiterungFachspezifischeDaten()));

        return schriftgutContent;

    }

    protected SchriftgutContent createSchriftgutFreitext() {

        SchriftgutContent schriftgutContent = new SchriftgutContent();
        schriftgutContent.setAnschreiben(Optional.of("CEEF2150-F915-1F1F-1176-906D00000000"));

        schriftgutContent.setDokumente(Optional.of(createDocuments()));
        schriftgutContent.setAkten(Optional.of(createDossiersFreitext()));

        return schriftgutContent;

    }

    private List<Dokument> createDocuments() {

        List<Dokument> documents = new ArrayList<>();

        // Antrag
        var uuidIdentAntrag = "CEEF2150-F915-1F1F-1177-906D00000000";
        List<Datei> antraege = new ArrayList<>();
        var antragDateiName = "1000809085_5793341761427_20240807_EH.pdf";

        Identifikation identifikationAntrag = new Identifikation(uuidIdentAntrag, BigInteger.valueOf(1));
        Datei antrag = new Datei(antragDateiName, BigInteger.valueOf(1));
        antraege.add(antrag);
        FachspezifischeDatenDokument fachspezifischeDatenDokumentAntrag = new FachspezifischeDatenDokument(XoevCodeGDSDokumentklasse.ANTRAG,
                uuidIdentAntrag.concat("_").concat(antragDateiName), antraege);
        documents.add(new Dokument(identifikationAntrag, fachspezifischeDatenDokumentAntrag));

        // Bescheid
        var uuidIdentBescheid = "CEEF2150-F915-1F1F-1178-906D00000000";
        List<Datei> bescheide = new ArrayList<>();
        var bescheidDateiName = "1000809085_5793341761427_20240807_URB.pdf";

        Identifikation identifikationBescheid = new Identifikation(uuidIdentBescheid, BigInteger.valueOf(1));
        Datei Bescheid = new Datei(bescheidDateiName, BigInteger.valueOf(1));
        bescheide.add(Bescheid);
        FachspezifischeDatenDokument fachspezifischeDatenDokumentBescheid = new FachspezifischeDatenDokument(XoevCodeGDSDokumentklasse.BESCHEID,
                uuidIdentBescheid.concat("_").concat(bescheidDateiName), bescheide);
        documents.add(new Dokument(identifikationBescheid, fachspezifischeDatenDokumentBescheid));

        return documents;
    }

    private List<Akte> createDossiersIdentifikationLaufzeitErweiterungFachspezifischeDaten() {
        List<Akte> dossiers = new ArrayList<>();

        try {
            Identifikation identifikation = new Identifikation("CEEF2150-F915-1F1F-1180-906D00000000", BigInteger.valueOf(1));
            Laufzeit laufzeit = new Laufzeit(DatatypeFactory.newInstance().newXMLGregorianCalendar("2024-03-02"),
                    DatatypeFactory.newInstance().newXMLGregorianCalendar("2025-12-31"));

            AnwendungspezifischeErweiterung erweiterung = new AnwendungspezifischeErweiterung("XDOMEA-BY", "XDOMEA-Erweiterung");
            AktenzeichenStrukuriert aktenzeichenStrukuriert = new AktenzeichenStrukuriert("MusterSachgebietsschlüssel", "MusterZusatzkennung",
                    "MusterAbteilung", "1", "2025");
            FachspezifischeDatenAkte fachspezifischeDatenAkte = new FachspezifischeDatenAkte(aktenzeichenStrukuriert, null, false);
            dossiers.add(new Akte(identifikation, laufzeit, erweiterung, fachspezifischeDatenAkte));

        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }

        return dossiers;
    }

    private List<Akte> createDossiersFreitext() {
        List<Akte> dossiers = new ArrayList<>();

        try {
            Identifikation identifikation = new Identifikation("CEEF2150-F915-1F1F-1180-906D00000000", BigInteger.valueOf(1));
            Laufzeit laufzeit = new Laufzeit(DatatypeFactory.newInstance().newXMLGregorianCalendar("2024-03-02"),
                    DatatypeFactory.newInstance().newXMLGregorianCalendar("2025-12-31"));

            AnwendungspezifischeErweiterung erweiterung = new AnwendungspezifischeErweiterung("XDOMEA-BY", "XDOMEA-Erweiterung");
            AktenzeichenStrukuriert aktenzeichenStrukuriert = new AktenzeichenStrukuriert("MusterSachgebietsschlüssel", "MusterZusatzkennung",
                    "MusterAbteilung", "1", "2025");
            FachspezifischeDatenAkte fachspezifischeDatenAkte = new FachspezifischeDatenAkte(null, "freitext", false);
            dossiers.add(new Akte(identifikation, laufzeit, erweiterung, fachspezifischeDatenAkte));

        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }

        return dossiers;
    }
}
