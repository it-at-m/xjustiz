package de.muenchen.xjustiz;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSEreignisTyp3;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSSachgebietTyp3;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSBeteiligung;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSGrunddaten;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSNachrichtenkopf;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSNatuerlichePerson;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSOrganisation;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.ExternAnJustiz0500010DocumentStart;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.ContentContainer;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.GrunddatenContent;
import de.muenchen.xjustizlib.xoev.XJustizProperty;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication(scanBasePackages = "de.muenchen.xjustiz")
@CamelSpringBootTest
@SpringBootTest(classes = { XJustizDocumentRouteBuilder.class })
@ActiveProfiles({ "default", "organisation" })
@EnableConfigurationProperties(XJustizProperty.class)
class ExternAnJustiz0500010OrganisationByConfigPropertyTest extends ExternAnJustiz0500010TestEnvironment {

    @Produce()
    private ProducerTemplate startxJustiz0500strafBuilderTest;

    @Value("${xjustiz.interface.document.processor}")
    private String documentInstanceRoute;

    @Value("${xjustiz.xjustiz0500straf.xsd.name}")
    private String schemaName;

    @Value("${xjustiz.xsd.path}")
    private String schemaPath;

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private ExternAnJustiz0500010DocumentStart documentBuilder;

    private NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 externAnJustiz0500010;

    @BeforeEach
    public void init() throws Exception {

        final Exchange request = ExchangeBuilder.anExchange(camelContext)
                .withHeader(DynamicXmlMarshaller.SCHEMA_PATH, schemaPath)
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, schemaName)
                .withBody(documentBuilder.start(new ContentContainer(createNachrichtenkopfContent(), createFachdaten(),
                        new GrunddatenContent(new ArrayList<>(List.of(createPersonSubjectToCoerceiveDetention())), createInstanzdaten()),
                        createSchriftgutAktenzeichenStrukuriert())))
                .build();

        final Exchange response = startxJustiz0500strafBuilderTest.send(documentInstanceRoute, request);
        final String xml = response.getMessage().getBody(String.class);
        this.externAnJustiz0500010 = parseXML(xml);
    }

    @Test
    void test_nachrichtenkopf() {

        final TypeGDSNachrichtenkopf nachrichtenkopf = this.externAnJustiz0500010.getNachrichtenkopf();
        assertEquals("Aktenzeichen", nachrichtenkopf.getAktenzeichenAbsender().getFirst());
        assertEquals("D2601", nachrichtenkopf.getAuswahlEmpfaenger().getEmpfaengerGericht().getCode(),
                "Error auswahl-empfaenger-gericht property.");
        assertEquals("Stadt München", nachrichtenkopf.getAuswahlAbsender().getAbsenderSonstige(),
                "Error auswahl-absender-kommunikationspartner-sonstige property.");
        final CodeGDSEreignisTyp3 ereignis = nachrichtenkopf.getEreignis().getFirst();
        assertEquals("117", ereignis.getCode(), "Error nachrichtenkopf-ereignis property.");
        assertEquals("1.11", ereignis.getListVersionID(), "Error nachrichtenkopf-ereignis current-version property.");

        assertEquals("KVU", nachrichtenkopf.getHerstellerinformation().getHerstellerDesProdukts());
        assertEquals("KVU-Name", nachrichtenkopf.getHerstellerinformation().getNameDesProdukts());
        assertEquals("KVU-Version", nachrichtenkopf.getHerstellerinformation().getVersion());

    }

    @Test
    void test_grunddaten() {

        final TypeGDSGrunddaten.Verfahrensdaten verfahrensdaten = this.externAnJustiz0500010.getGrunddaten().getVerfahrensdaten();

        assertEquals(2, verfahrensdaten.getInstanzdaten().size());
        final CodeGDSSachgebietTyp3 gericht = verfahrensdaten.getInstanzdaten().get(1).getSachgebiet();
        assertEquals("026", gericht.getCode(), "Error sachgebiet property.");
        assertEquals("2.3", gericht.getListVersionID(), "Error sachgebiet current-version property.");
        final CodeGDSGerichteTyp3 instanzbehoerde = verfahrensdaten.getInstanzdaten().get(1).getAuswahlInstanzbehoerde().getGericht();
        assertEquals("D2601", instanzbehoerde.getCode(), "Error auswahl-instanzbehoerde-gericht property.");
        assertEquals("3.6", instanzbehoerde.getListVersionID(), "Error auswahl-instanzbehoerde-gericht current-version property.");

        assertEquals("1", verfahrensdaten.getInstanzdaten().get(1).getInstanznummer());
        assertEquals("neu", verfahrensdaten.getInstanzdaten().get(1).getAktenzeichen().getAuswahlAktenzeichen().getAktenzeichenFreitext());

        assertEquals("2", verfahrensdaten.getBeteiligung().getLast().getRolle().getLast().getRollennummer(), "Two rollen in whole document expected.");
        assertEquals(BigInteger.ONE, verfahrensdaten.getBeteiligung().getLast().getRolle().getLast().getNr(),
                "Only one rolle with same rollenbezeichnung expected.");
        assertEquals("2", verfahrensdaten.getBeteiligung().getLast().getBeteiligter().getBeteiligtennummer(), "Two beteiligter in whole document expected.");

        final List<TypeGDSBeteiligung> beteiligungenNatuerlichePerson = verfahrensdaten.getBeteiligung().stream()
                .filter(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getNatuerlichePerson() != null).toList();
        assertEquals(1, beteiligungenNatuerlichePerson.size(), "Wrong number of beteiligungen property.");
        final TypeGDSBeteiligung.Rolle rolleNatuerlichePerson = beteiligungenNatuerlichePerson.getFirst().getRolle().getFirst();
        assertEquals("040", rolleNatuerlichePerson.getRollenbezeichnung().getCode());
        assertEquals("3.5", rolleNatuerlichePerson.getRollenbezeichnung().getListVersionID());

        final List<TypeGDSNatuerlichePerson> natuerlichePersonen = beteiligungenNatuerlichePerson.stream()
                .filter(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getNatuerlichePerson() != null)
                .map(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getNatuerlichePerson()).toList();
        assertEquals(1, natuerlichePersonen.size(), "Wrong number of natuerlichePerson property.");
        assertEquals(NatuerlichePerson.NACHNAME, natuerlichePersonen.getFirst().getVollerName().getNachname());
        assertEquals(NatuerlichePerson.VORNAME, natuerlichePersonen.getFirst().getVollerName().getVorname());
        assertEquals(NatuerlichePerson.STRASSE, natuerlichePersonen.getFirst().getAnschrift().getFirst().getStrasse());
        assertEquals(NatuerlichePerson.HAUSNUMMER, natuerlichePersonen.getFirst().getAnschrift().getFirst().getHausnummer());
        assertEquals(NatuerlichePerson.POSTFACHNUMMER, natuerlichePersonen.getFirst().getAnschrift().getFirst().getPostfachnummer());
        assertEquals(NatuerlichePerson.ANSCHRIFTENZUATZ, natuerlichePersonen.getFirst().getAnschrift().getFirst().getAnschriftenzusatz().getFirst());
        assertEquals(NatuerlichePerson.PLZ, natuerlichePersonen.getFirst().getAnschrift().getFirst().getPostleitzahl());
        assertEquals(NatuerlichePerson.POSTFACHNUMMER, natuerlichePersonen.getFirst().getAnschrift().getFirst().getPostfachnummer());

        assertEquals("000", natuerlichePersonen.getFirst().getAnschrift().getFirst().getStaat().getCode());
        assertEquals("7.0", natuerlichePersonen.getFirst().getAnschrift().getFirst().getStaat().getListVersionID());
        assertEquals(NatuerlichePerson.ORT, natuerlichePersonen.getFirst().getAnschrift().getFirst().getOrt());
        assertEquals(NatuerlichePerson.WOHNUNGSGEBER, natuerlichePersonen.getFirst().getAnschrift().getFirst().getWohnungsgeber());

        assertEquals(NatuerlichePerson.GEBURTSSORT, natuerlichePersonen.getFirst().getGeburt().getGeburtsort().getOrt());
        assertEquals(NatuerlichePerson.GEBURTSNAME, natuerlichePersonen.getFirst().getVollerName().getGeburtsname());
        assertEquals(NatuerlichePerson.NAMENSVORSATZ, natuerlichePersonen.getFirst().getVollerName().getNamensvorsatz());
        assertEquals(NatuerlichePerson.TITEL, natuerlichePersonen.getFirst().getVollerName().getTitel());
        assertEquals(NatuerlichePerson.GEBURTSDATUM, natuerlichePersonen.getFirst().getGeburt().getGeburtsdatum());

        assertEquals("1", natuerlichePersonen.getFirst().getGeschlecht().getCode());

        final List<TypeGDSBeteiligung> beteiligungenOrganisation = verfahrensdaten.getBeteiligung().stream()
                .filter(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getOrganisation() != null).toList();
        assertEquals(1, beteiligungenOrganisation.size(), "Wrong number of beteiligungen property.");
        final TypeGDSBeteiligung.Rolle rolleOrganisation = beteiligungenOrganisation.getFirst().getRolle().getFirst();
        assertEquals("046", rolleOrganisation.getRollenbezeichnung().getCode());
        assertEquals("3.5", rolleOrganisation.getRollenbezeichnung().getListVersionID());

        final List<TypeGDSOrganisation> organisationen = verfahrensdaten.getBeteiligung().stream()
                .filter(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getOrganisation() != null)
                .map(beteiligung -> beteiligung.getBeteiligter().getAuswahlBeteiligter().getOrganisation()).toList();
        assertEquals(1, organisationen.size(), "Wrong number of organisationen property.");
        assertEquals("Landeshauptstadt München Referat", organisationen.getFirst().getBezeichnung().getBezeichnungAktuell());
        assertEquals("Straße", organisationen.getFirst().getAnschrift().getFirst().getStrasse());
        assertEquals("1", organisationen.getFirst().getAnschrift().getFirst().getHausnummer());
        assertEquals("8000", organisationen.getFirst().getAnschrift().getFirst().getPostleitzahl());
        assertEquals("München", organisationen.getFirst().getAnschrift().getFirst().getOrt());

        assertEquals("DE99 9999 9999 9999 9999 00", organisationen.getFirst().getBankverbindung().getFirst().getIban());

        assertEquals("003", organisationen.getFirst().getAnschrift().getFirst().getAnschriftstyp().getCode());
        assertEquals("3.0", organisationen.getFirst().getAnschrift().getFirst().getAnschriftstyp().getListVersionID());

    }

}
