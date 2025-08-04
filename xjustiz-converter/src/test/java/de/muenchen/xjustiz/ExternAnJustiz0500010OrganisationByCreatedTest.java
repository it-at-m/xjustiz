package de.muenchen.xjustiz;

import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xjustiz0500straf.content.ContentContainer;
import de.muenchen.xjustiz.xjustiz0500straf.content.GrunddatenContent;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootApplication(scanBasePackages = "de.muenchen.xjustiz")
@CamelSpringBootTest
@SpringBootTest(classes = {XJustizDocumentRouteBuilder.class})
@ActiveProfiles({"default"})
public class ExternAnJustiz0500010OrganisationByCreatedTest extends ExternAnJustiz0500010TestEnvironment {

    @Produce()
    private ProducerTemplate startxJustiz0500strafBuilderTest;

    @Value("${xjustiz.interface.document.processor}")
    private String testRoute;

    @Autowired
    private CamelContext camelContext;

    private NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 externAnJustiz0500010;

    @BeforeEach
    public void init() throws Exception {

        Exchange request = ExchangeBuilder.anExchange(camelContext).withBody(new ContentContainer(createFachdaten(), new GrunddatenContent(new ArrayList<>(List.of(createPersonSubjectToCoerceiveDetention(), createApplicant()))))).build();
        var response = startxJustiz0500strafBuilderTest.send(testRoute, request);
        var xml = response.getMessage().getBody(String.class);
        this.externAnJustiz0500010 = parseXML(xml);
    }

    @Test
    void test_nachrichtenkopf() {

        TypeGDSNachrichtenkopf nachrichtenkopf = this.externAnJustiz0500010.getNachrichtenkopf();
        assertEquals("D2601", nachrichtenkopf.getEmpfaenger().getInformationen().getAuswahlKommunikationspartner().getGericht().getCode(), "Error auswahl-empfaenger-gericht property.");
        assertEquals("Stadt München", nachrichtenkopf.getAbsender().getInformationen().getAuswahlKommunikationspartner().getSonstige(), "Error auswahl-absender-kommunikationspartner-sonstige property.");
        var ereignis = nachrichtenkopf.getEreignises().getFirst();
        assertEquals("117", ereignis.getCode(), "Error nachrichtenkopf-ereignis property.");
        assertEquals("1.11", ereignis.getListVersionID(), "Error nachrichtenkopf-ereignis current-version property.");

        assertEquals("KVU", nachrichtenkopf.getHerstellerinformation().getHerstellerDesProdukts());
        assertEquals("KVU-Name", nachrichtenkopf.getHerstellerinformation().getNameDesProdukts());
        assertEquals("KVU-Version", nachrichtenkopf.getHerstellerinformation().getVersion());
    }

    @Test
    void test_grunddaten() {

        TypeGDSGrunddaten.Verfahrensdaten verfahrensdaten = this.externAnJustiz0500010.getGrunddaten().getVerfahrensdaten();

        var sachgebiet = verfahrensdaten.getInstanzdatens().getFirst().getSachgebiet();
        assertEquals("026", sachgebiet.getCode(), "Error sachgebiet property.");
        assertEquals("2.3", sachgebiet.getListVersionID(), "Error sachgebiet current-version property.");
        var instanzbehoerde = verfahrensdaten.getInstanzdatens().getFirst().getAuswahlInstanzbehoerde().getGericht();
        assertEquals("D2601", instanzbehoerde.getCode(), "Error auswahl-instanzbehoerde-gericht property.");
        assertEquals("3.6", instanzbehoerde.getListVersionID(), "Error auswahl-instanzbehoerde-gericht current-version property.");

        assertEquals("2", verfahrensdaten.getBeteiligungs().getLast().getRolles().getLast().getRollennummer(), "Two rollen in whole document expected.");
        assertEquals(BigInteger.ONE, verfahrensdaten.getBeteiligungs().getLast().getRolles().getLast().getNr(), "Only one rolle with same rollenbezeichnung expected.");
        assertEquals("2", verfahrensdaten.getBeteiligungs().getLast().getBeteiligter().getBeteiligtennummer(), "Two beteiligter in whole document expected.");

        List<TypeGDSBeteiligung> beteiligungenNatuerlichePerson = verfahrensdaten.getBeteiligungs().stream().filter(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getNatuerlichePerson() != null).toList();
        assertEquals(1, beteiligungenNatuerlichePerson.size(), "Wrong number of beteiligungen property.");
        var rolleNatuerlichePerson = beteiligungenNatuerlichePerson.getFirst().getRolles().getFirst();
        assertEquals("040", rolleNatuerlichePerson.getRollenbezeichnung().getCode());
        assertEquals("3.5", rolleNatuerlichePerson.getRollenbezeichnung().getListVersionID());

        List<TypeGDSNatuerlichePerson> natuerlichePersonen = beteiligungenNatuerlichePerson.stream().filter(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getNatuerlichePerson() != null).map(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getNatuerlichePerson()).toList();
        assertEquals(1, natuerlichePersonen.size(), "Wrong number of natuerlichePerson property.");
        assertEquals(NatuerlichePerson.NACHNAME, natuerlichePersonen.getFirst().getVollerName().getNachname());
        assertEquals(NatuerlichePerson.VORNAME, natuerlichePersonen.getFirst().getVollerName().getVorname());
        assertEquals(NatuerlichePerson.STRASSE, natuerlichePersonen.getFirst().getAnschrifts().getFirst().getStrasse());
        assertEquals(NatuerlichePerson.HAUSNUMMER, natuerlichePersonen.getFirst().getAnschrifts().getFirst().getHausnummer());
        assertEquals(NatuerlichePerson.POSTFACHNUMMER, natuerlichePersonen.getFirst().getAnschrifts().getFirst().getPostfachnummer());
        assertEquals(NatuerlichePerson.ANSCHRIFTENZUATZ, natuerlichePersonen.getFirst().getAnschrifts().getFirst().getAnschriftenzusatzs().getFirst());
        assertEquals(NatuerlichePerson.PLZ, natuerlichePersonen.getFirst().getAnschrifts().getFirst().getPostleitzahl());
        assertEquals(NatuerlichePerson.POSTFACHNUMMER, natuerlichePersonen.getFirst().getAnschrifts().getFirst().getPostfachnummer());

        assertEquals("000", natuerlichePersonen.getFirst().getAnschrifts().getFirst().getStaat().getCode());
        assertEquals("7.0", natuerlichePersonen.getFirst().getAnschrifts().getFirst().getStaat().getListVersionID());
        assertEquals(NatuerlichePerson.ORT, natuerlichePersonen.getFirst().getAnschrifts().getFirst().getOrt());
        assertEquals(NatuerlichePerson.WOHNUNGSGEBER, natuerlichePersonen.getFirst().getAnschrifts().getFirst().getWohnungsgeber());

        assertEquals(NatuerlichePerson.GEBURTSSORT, natuerlichePersonen.getFirst().getGeburt().getGeburtsort().getOrt());
        assertEquals(NatuerlichePerson.GEBURTSNAME, natuerlichePersonen.getFirst().getVollerName().getGeburtsname());
        assertEquals(NatuerlichePerson.NAMENSVORSATZ, natuerlichePersonen.getFirst().getVollerName().getNamensvorsatz());
        assertEquals(NatuerlichePerson.TITEL, natuerlichePersonen.getFirst().getVollerName().getTitel());
        assertEquals(NatuerlichePerson.GEBURTSDATUM, natuerlichePersonen.getFirst().getGeburt().getGeburtsdatum());

        assertEquals("1", natuerlichePersonen.getFirst().getGeschlecht().getCode());

        List<TypeGDSBeteiligung> beteiligungenOrganisation = verfahrensdaten.getBeteiligungs().stream().filter(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getOrganisation() != null).toList();
        assertEquals(1, beteiligungenOrganisation.size(), "Wrong number of beteiligungen property.");
        var rolleOrganisation = beteiligungenOrganisation.getFirst().getRolles().getFirst();
        assertEquals("046", rolleOrganisation.getRollenbezeichnung().getCode());
        assertEquals("3.5", rolleOrganisation.getRollenbezeichnung().getListVersionID());

        List<TypeGDSOrganisation> organisationen = verfahrensdaten.getBeteiligungs().stream().filter(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getOrganisation() != null).map(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getOrganisation()).toList();
        assertEquals(1, organisationen.size(), "Wrong number of organisationen property.");
        assertEquals("Created Bezeichnung Aktuell", organisationen.getFirst().getBezeichnung().getBezeichnungAktuell());
        assertEquals("Created Strasse", organisationen.getFirst().getAnschrifts().getFirst().getStrasse());
        assertEquals("Created Hausnummer", organisationen.getFirst().getAnschrifts().getFirst().getHausnummer());
        assertEquals("Created Plz", organisationen.getFirst().getAnschrifts().getFirst().getPostleitzahl());
        assertEquals("Created Ort", organisationen.getFirst().getAnschrifts().getFirst().getOrt());

        assertEquals("Created Iban", organisationen.getFirst().getBankverbindungs().getFirst().getIban());

        assertEquals("003", organisationen.getFirst().getAnschrifts().getFirst().getAnschriftstyp().getCode());
        assertEquals("3.0", organisationen.getFirst().getAnschrifts().getFirst().getAnschriftstyp().getListVersionID());

    }

    @Test
    void test_fachdaten() {

        NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.Fachdaten fachdaten = this.externAnJustiz0500010.getFachdaten();
        assertEquals("2024-10-01", fachdaten.getBussgeldbescheid().getTat().getAnfangsdatum());
        assertEquals("12:00", fachdaten.getBussgeldbescheid().getTat().getAnfangsuhrzeit());
        assertEquals("2024-10-01", fachdaten.getBussgeldbescheid().getTat().getEndedatum());
        assertEquals("13:05", fachdaten.getBussgeldbescheid().getTat().getEndeuhrzeit());

        assertEquals("KVU EH-TATSTR1", fachdaten.getBussgeldbescheid().getTat().getTatorts().getFirst().getAnschrifts().getFirst().getStrasse());
        assertEquals("KVU EH-TATHNR1", fachdaten.getBussgeldbescheid().getTat().getTatorts().getFirst().getAnschrifts().getFirst().getHausnummer());
        assertEquals("KVU EH-TATORT", fachdaten.getBussgeldbescheid().getTat().getTatorts().getFirst().getAnschrifts().getFirst().getOrt());
        assertEquals("KVU ???", fachdaten.getBussgeldbescheid().getTat().getTatorts().getFirst().getOrtsbeschreibung());

        assertEquals("KVU EH-TATSTR2", fachdaten.getBussgeldbescheid().getTat().getTatorts().getLast().getAnschrifts().getLast().getStrasse());
        assertEquals("KVU EH-TATHNR2", fachdaten.getBussgeldbescheid().getTat().getTatorts().getLast().getAnschrifts().getLast().getHausnummer());
        assertEquals("KVU EH-TATORT", fachdaten.getBussgeldbescheid().getTat().getTatorts().getLast().getAnschrifts().getLast().getOrt());
        assertEquals("KVU ???", fachdaten.getBussgeldbescheid().getTat().getTatorts().getLast().getOrtsbeschreibung());

    }

}
