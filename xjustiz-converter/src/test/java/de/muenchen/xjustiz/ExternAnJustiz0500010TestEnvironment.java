package de.muenchen.xjustiz;

import de.muenchen.xjustiz.generated.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010;
import de.muenchen.xjustiz.xjustiz0500straf.builder.Geschlecht;
import de.muenchen.xjustiz.xjustiz0500straf.content.FachdatenContent;
import de.muenchen.xjustiz.xjustiz0500straf.content.fachdaten.StrasseHausnummer;
import de.muenchen.xjustiz.xjustiz0500straf.content.fachdaten.Tatort;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Anschrift;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Beteiligung;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Rolle;
import jakarta.xml.bind.JAXBContext;
import org.springframework.beans.factory.annotation.Value;

import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExternAnJustiz0500010TestEnvironment {

    @Value("${xjustiz.xjustiz0500straf.codelisten.gds-rollenbezeichnung.codelist-versions.gds-rollenbezeichnung-3-5.betroffener}")
    protected String rollenbezeichnung;

    protected List<Beteiligung> createAffectedTestPerson() {

        Beteiligung affectedPerson = new Beteiligung();
        var rolle = new Rolle();

        rolle.setRollenbezeichnung(rollenbezeichnung);
        affectedPerson.addRolle(rolle);

        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setVorname(NatuerlichePerson.VORNAME);
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setNachname(NatuerlichePerson.NACHNAME);
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setTitel(NatuerlichePerson.TITEL);
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setNamensvorsatz(NatuerlichePerson.NAMENSVORSATZ);
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setGeburtsname(NatuerlichePerson.GEBURTSNAME);
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateGeburt().setGeburtsdatum(NatuerlichePerson.GEBURTSDATUM);
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateGeburt().setGeburtsort(NatuerlichePerson.GEBURTSSORT);
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().setGeschlecht(Geschlecht.GESCHLECHT.MAENNLICH);

        var anschrift = new Anschrift();
        anschrift.setAnschriftenzusatz(NatuerlichePerson.ANSCHRIFTENZUATZ);
        anschrift.setStrasse(NatuerlichePerson.STRASSE);
        anschrift.setHausnummer(NatuerlichePerson.HAUSNUMMER);
        anschrift.setPostfachnummer(NatuerlichePerson.POSTFACHNUMMER);
        anschrift.setPlz(NatuerlichePerson.PLZ);
        anschrift.setOrt(NatuerlichePerson.ORT);
        anschrift.setWohnungsgeber(NatuerlichePerson.WOHNUNGSGEBER);
        anschrift.setStaat(NatuerlichePerson.STAAT);
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().addAnschrift(anschrift);

        return new ArrayList<>(List.of(affectedPerson));
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
       final static String STAAT = "000";

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

}
