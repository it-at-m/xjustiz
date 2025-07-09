package de.muenchen.xjustiz;

import de.muenchen.xjustiz.generated.*;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootApplication(scanBasePackages = "de.muenchen.xjustiz")
@CamelSpringBootTest
@SpringBootTest(classes = {ConverterRouteBuilder.class})
@ActiveProfiles({"default", "organisation"})
public class ExternAnJustiz0500010Test extends ExternAnJustiz0500010TestEnvironment {

    @Produce("direct:start")
    private ProducerTemplate startxJustiz0500strafBuilderTest;

    @Value("${xjustiz.route.converter.from}")
    private String testRoute;

    private NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 externAnJustiz0500010;

    @BeforeEach
    public void init() throws Exception {
        var xml  = startxJustiz0500strafBuilderTest.requestBody(testRoute, createAffectedTestPerson(), String.class);
        this.externAnJustiz0500010 = parseXML(xml);
    }

    @Test
    void test_nachrichtenkopf() {

        TypeGDSNachrichtenkopf nachrichtenkopf = this.externAnJustiz0500010.getNachrichtenkopf();
        assertEquals("D2601", nachrichtenkopf.getEmpfaenger().getInformationen().getAuswahlKommunikationspartner().getGericht().getCode(), "Error auswahl-empfaenger-gericht property.");
        var ereignis = nachrichtenkopf.getEreignises().getFirst();
        assertEquals("117", ereignis.getCode(), "Error nachrichtenkopf-ereignis property.");
        assertEquals( "1.11", ereignis.getListVersionID(), "Error nachrichtenkopf-ereignis current-version property.");
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

        assertEquals("2" , verfahrensdaten.getBeteiligungs().getLast().getRolles().getLast().getRollennummer(), "Two rollen in whole document expected.");
        assertEquals(BigInteger.ONE , verfahrensdaten.getBeteiligungs().getLast().getRolles().getLast().getNr(), "Only one rolle with same rollenbezeichnung expected.");
        assertEquals("2" , verfahrensdaten.getBeteiligungs().getLast().getBeteiligter().getBeteiligtennummer(), "Two beteiligter in whole document expected.");

       List<TypeGDSBeteiligung> beteiligungenNatuerlichePerson = verfahrensdaten.getBeteiligungs().stream().filter(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getNatuerlichePerson() != null).toList();
       assertEquals(1, beteiligungenNatuerlichePerson.size(), "Wrong number of beteiligungen property.");
        var rolleNatuerlichePerson = beteiligungenNatuerlichePerson.getFirst().getRolles().getFirst();
       assertEquals("040", rolleNatuerlichePerson.getRollenbezeichnung().getCode());
        assertEquals("3.5", rolleNatuerlichePerson.getRollenbezeichnung().getListVersionID());

        List<TypeGDSNatuerlichePerson> natuerlichePersonen = beteiligungenNatuerlichePerson.stream().filter(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getNatuerlichePerson() != null).map(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getNatuerlichePerson()).toList();
        assertEquals(1, natuerlichePersonen.size(), "Wrong number of natuerlichePerson property.");
        assertEquals(NatuerlichePerson.nachname, natuerlichePersonen.getFirst().getVollerName().getNachname());
        assertEquals(NatuerlichePerson.vorname, natuerlichePersonen.getFirst().getVollerName().getVorname());

        List<TypeGDSBeteiligung> beteiligungenOrganisation = verfahrensdaten.getBeteiligungs().stream().filter(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getOrganisation() != null).toList();
        assertEquals(1, beteiligungenOrganisation.size(), "Wrong number of beteiligungen property.");
        var rolleOrganisation = beteiligungenOrganisation.getFirst().getRolles().getFirst();
        assertEquals("016", rolleOrganisation.getRollenbezeichnung().getCode());
        assertEquals("3.5", rolleOrganisation.getRollenbezeichnung().getListVersionID());

        List<TypeGDSOrganisation> organisationen = verfahrensdaten.getBeteiligungs().stream().filter(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getOrganisation() != null).map(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getOrganisation()).toList();
        assertEquals(1, organisationen.size(), "Wrong number of organisationen property.");
        assertEquals("Landeshauptstadt München Referat", organisationen.getFirst().getBezeichnung().getBezeichnungAktuell());

        assertEquals("003", organisationen.getFirst().getAnschrifts().getFirst().getAnschriftstyp().getCode());
        assertEquals("3.0", organisationen.getFirst().getAnschrifts().getFirst().getAnschriftstyp().getListVersionID());

    }
}
