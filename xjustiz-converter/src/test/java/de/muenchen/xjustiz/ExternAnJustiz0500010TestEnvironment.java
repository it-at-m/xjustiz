package de.muenchen.xjustiz;

import de.muenchen.xjustiz.generated.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Anschrift;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Beteiligung;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Rolle;
import jakarta.xml.bind.JAXBContext;
import org.springframework.beans.factory.annotation.Value;

import java.io.StringReader;
import java.util.List;

public class ExternAnJustiz0500010TestEnvironment {

    @Value("${xjustiz.xjustiz0500straf.grunddaten.verfahrensdaten.codelisten.gds-rollenbezeichnung.codelist-versions.gds-rollenbezeichnung-3-5.natuerliche-person}")
    protected String rollenbezeichnung;

    protected List<Beteiligung> createAffectedTestPerson() {

        Beteiligung affectedPerson = new Beteiligung();
        var rolle = new Rolle();

        rolle.setRollenbezeichnung(rollenbezeichnung);
        affectedPerson.addRolle(rolle);

        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setVorname(NatuerlichePerson.vorname);
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setNachname(NatuerlichePerson.nachname);
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setTitel("Dr.");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setNamensvorsatz("von");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateVollerName().setGeburtsname("Müller");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateGeburt().setGeburtsdatum("2000-01-01");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().generateGeburt().setGeburtsort("Musterhausen");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().setGeschlecht("männlich");

        var anschrift = new Anschrift();
        anschrift.setAnschriftenzusatz("im Hinterhof");
        anschrift.setStrasse("Musterstrasse");
        anschrift.setHausnummer("1");
        anschrift.setPostfachnummer("1234");
        anschrift.setPlz("11111");
        anschrift.setOrt("Musterhausen");
        anschrift.setWohnungsgeber("?");
        anschrift.setStaat("000");
        affectedPerson.generateBeteiligter().generateNatuerlichePerson().addAnschrift(anschrift);

        return List.of(affectedPerson);
    }

    protected NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 parseXML(String xml) throws Exception {
        JAXBContext context = JAXBContext.newInstance(NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.class);
        return (NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010) context.createUnmarshaller().unmarshal(new StringReader(xml));
    }

    protected static class NatuerlichePerson {

       final static String vorname = "Maximilian";
       final static String nachname = "Mustermann";

    }

}
