package de.muenchen.xjustiz;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import de.muenchen.xjustiz.config.DynamicJsonUnmarshaller;
import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import de.muenchen.xjustiz.generated.CodeGDSEreignisTyp3;
import de.muenchen.xjustiz.generated.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010;
import de.muenchen.xjustiz.generated.TypeGDSBeteiligung;
import de.muenchen.xjustiz.generated.TypeGDSGrunddaten;
import de.muenchen.xjustiz.generated.TypeGDSInstanzdaten;
import de.muenchen.xjustiz.generated.TypeGDSNachrichtenkopf;
import de.muenchen.xjustiz.generated.TypeGDSNatuerlichePerson;
import de.muenchen.xjustiz.generated.TypeGDSOrganisation;
import de.muenchen.xjustiz.generated.TypeGDSRefBeteiligtennummer;
import de.muenchen.xjustiz.generated.TypeGDSSchriftgutobjekte;
import de.muenchen.xjustiz.xjustiz0500straf.builder.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director;
import de.muenchen.xjustiz.xjustiz0500straf.content.ContentContainer;
import de.muenchen.xjustiz.xjustiz0500straf.content.GrunddatenContent;

@SpringBootApplication(scanBasePackages = "de.muenchen.xjustiz")
@CamelSpringBootTest
@SpringBootTest(classes = { XJustizDocumentRouteBuilder.class })
@ActiveProfiles({ "default" })
class ExternAnJustiz0500010OrganisationAktenzeichenByCreatedTest extends ExternAnJustiz0500010TestEnvironment {

    @Produce()
    private ProducerTemplate startxJustiz0500strafBuilderTest;

    @Value("${xjustiz.interface.document.processor}")
    private String testRoute;

    @Autowired
    private CamelContext camelContext;
    
    @Autowired
    private NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director nachrichtDirector;

    private NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 externAnJustiz0500010;
    
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() throws Exception {

        final Exchange request = ExchangeBuilder.anExchange(camelContext)
        		.withHeader(DynamicXmlMarshaller.SCHEMA_NAME, "xjustiz_0500_straf_3_5.xsd")
                .withHeader(DynamicJsonUnmarshaller.UNMARSHAL_CLASS_TYPE, NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.class)
                .withBody(objectMapper.writeValueAsString(nachrichtDirector.build(new ContentContainer(createNachrichtenkopfContent(), createFachdaten(),
                        new GrunddatenContent(new ArrayList<>(List.of(createPersonSubjectToCoerceiveDetention(), createApplicant())), createInstanzdaten()),
                        createSchriftgutAktenzeichenStrukuriert())))).build();
        
        final Exchange response = startxJustiz0500strafBuilderTest.send(testRoute, request);
        assertNull(response.getException(), "Error during XML creation.");
        final String xml = response.getMessage().getBody(String.class);
        this.externAnJustiz0500010 = parseXML(xml);
    }

    @Test
    void test_nachrichtenkopf() {

        final TypeGDSNachrichtenkopf nachrichtenkopf = this.externAnJustiz0500010.getNachrichtenkopf();
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

        assertEquals("0", verfahrensdaten.getInstanzdaten().get(0).getInstanznummer());

        final TypeGDSRefBeteiligtennummer instanzBeteiligter = verfahrensdaten.getInstanzdaten().get(0).getAuswahlInstanzbehoerde().getBeteiligter();
        assertEquals("2", instanzBeteiligter.getRefBeteiligtennummer(), "Static 2, provided that LHM is always specified as the second participant.");
        assertEquals("GP-ID_Kassenkontonummer_Datum",
                verfahrensdaten.getInstanzdaten().get(0).getAktenzeichen().getAuswahlAktenzeichen().getAktenzeichenFreitext());

        final TypeGDSInstanzdaten instanzGericht = verfahrensdaten.getInstanzdaten().get(1);
        assertEquals("026", instanzGericht.getSachgebiet().getCode(), "Error sachgebiet property.");
        assertEquals("2.3", instanzGericht.getSachgebiet().getListVersionID(), "Error sachgebiet property.");
        assertEquals("1", instanzGericht.getInstanznummer());

        assertEquals("D2601", instanzGericht.getAuswahlInstanzbehoerde().getGericht().getCode(), "Error auswahl-instanzbehoerde-gericht property.");
        assertEquals("3.6", instanzGericht.getAuswahlInstanzbehoerde().getGericht().getListVersionID(),
                "Error auswahl-instanzbehoerde-gericht current-version property.");

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
        assertEquals("Created Bezeichnung Aktuell", organisationen.getFirst().getBezeichnung().getBezeichnungAktuell());
        assertEquals("Created Strasse", organisationen.getFirst().getAnschrift().getFirst().getStrasse());
        assertEquals("Created Hausnummer", organisationen.getFirst().getAnschrift().getFirst().getHausnummer());
        assertEquals("Created Plz", organisationen.getFirst().getAnschrift().getFirst().getPostleitzahl());
        assertEquals("Created Ort", organisationen.getFirst().getAnschrift().getFirst().getOrt());

        assertEquals("Created Iban", organisationen.getFirst().getBankverbindung().getFirst().getIban());

        assertEquals("003", organisationen.getFirst().getAnschrift().getFirst().getAnschriftstyp().getCode());
        assertEquals("3.0", organisationen.getFirst().getAnschrift().getFirst().getAnschriftstyp().getListVersionID());

    }

    @Test
    void test_fachdaten() throws DatatypeConfigurationException {

        final NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.Fachdaten fachdaten = this.externAnJustiz0500010.getFachdaten();
        assertEquals("2024-10-01", fachdaten.getBussgeldbescheid().getTat().getAnfangsdatum());
        assertEquals("12:00", fachdaten.getBussgeldbescheid().getTat().getAnfangsuhrzeit());
        assertEquals("2024-10-01", fachdaten.getBussgeldbescheid().getTat().getEndedatum());
        assertEquals("13:05", fachdaten.getBussgeldbescheid().getTat().getEndeuhrzeit());

        assertEquals("2025-11-03", fachdaten.getBussgeldbescheid().getErlassdatum());
        assertEquals(DatatypeFactory.newInstance().newXMLGregorianCalendar(2025, 11, 3, -2147483648, -2147483648, -2147483648, -2147483648, 0),
                fachdaten.getBussgeldbescheid().getRechtskraft().getRechtskraftdatum());
        assertEquals(10.5, fachdaten.getBussgeldbescheid().getAuslagen());
        assertEquals(15.10, fachdaten.getBussgeldbescheid().getGeldbusse());

        assertEquals("EH-TATSTR1", fachdaten.getBussgeldbescheid().getTat().getTatort().getFirst().getAnschrift().getFirst().getStrasse());
        assertEquals("EH-TATHNR1", fachdaten.getBussgeldbescheid().getTat().getTatort().getFirst().getAnschrift().getFirst().getHausnummer());
        assertEquals("EH-TATORT", fachdaten.getBussgeldbescheid().getTat().getTatort().getFirst().getAnschrift().getFirst().getOrt());
        assertEquals("Location", fachdaten.getBussgeldbescheid().getTat().getTatort().getFirst().getOrtsbeschreibung());

        assertEquals("EH-TATSTR2", fachdaten.getBussgeldbescheid().getTat().getTatort().getLast().getAnschrift().getLast().getStrasse());
        assertEquals("EH-TATHNR2", fachdaten.getBussgeldbescheid().getTat().getTatort().getLast().getAnschrift().getLast().getHausnummer());
        assertEquals("EH-TATORT", fachdaten.getBussgeldbescheid().getTat().getTatort().getLast().getAnschrift().getLast().getOrt());
        assertEquals("Location", fachdaten.getBussgeldbescheid().getTat().getTatort().getLast().getOrtsbeschreibung());

    }

    @Test
    void test_schriftgutobjekte() {

        final TypeGDSSchriftgutobjekte schriftgutobjekte = this.externAnJustiz0500010.getSchriftgutobjekte();
        assertEquals("CEEF2150-F915-1F1F-1176-906D00000000", schriftgutobjekte.getAnschreiben().getRefSgo());

        assertEquals("CEEF2150-F915-1F1F-1177-906D00000000", schriftgutobjekte.getDokument().getFirst().getIdentifikation().getId());
        assertEquals(BigInteger.ONE, schriftgutobjekte.getDokument().getFirst().getIdentifikation().getNummerImUebergeordnetenContainer());
        assertEquals("016", schriftgutobjekte.getDokument().getFirst().getXjustizFachspezifischeDaten().getDokumentklasse().getCode());
        assertEquals("1.4", schriftgutobjekte.getDokument().getFirst().getXjustizFachspezifischeDaten().getDokumentklasse().getListVersionID());
        assertEquals("CEEF2150-F915-1F1F-1177-906D00000000_1000809085_5793341761427_20240807_EH.pdf",
                schriftgutobjekte.getDokument().getFirst().getXjustizFachspezifischeDaten().getAnzeigename());
        assertEquals("1000809085_5793341761427_20240807_EH.pdf",
                schriftgutobjekte.getDokument().getFirst().getXjustizFachspezifischeDaten().getDatei().getFirst().getDateiname());
        assertEquals(BigInteger.ONE, schriftgutobjekte.getDokument().getFirst().getXjustizFachspezifischeDaten().getDatei().getFirst().getVersionsnummer());
        assertEquals("001", schriftgutobjekte.getDokument().getFirst().getXjustizFachspezifischeDaten().getDatei().getFirst().getBestandteil().getCode());

        assertEquals("CEEF2150-F915-1F1F-1178-906D00000000", schriftgutobjekte.getDokument().getLast().getIdentifikation().getId());
        assertEquals(BigInteger.ONE, schriftgutobjekte.getDokument().getLast().getIdentifikation().getNummerImUebergeordnetenContainer());
        assertEquals("017", schriftgutobjekte.getDokument().getLast().getXjustizFachspezifischeDaten().getDokumentklasse().getCode());
        assertEquals("CEEF2150-F915-1F1F-1178-906D00000000_1000809085_5793341761427_20240807_URB.pdf",
                schriftgutobjekte.getDokument().getLast().getXjustizFachspezifischeDaten().getAnzeigename());
        assertEquals("1000809085_5793341761427_20240807_URB.pdf",
                schriftgutobjekte.getDokument().getLast().getXjustizFachspezifischeDaten().getDatei().getFirst().getDateiname());
        assertEquals(BigInteger.ONE, schriftgutobjekte.getDokument().getLast().getXjustizFachspezifischeDaten().getDatei().getLast().getVersionsnummer());
        assertEquals("002", schriftgutobjekte.getDokument().getLast().getXjustizFachspezifischeDaten().getDatei().getLast().getBestandteil().getCode());

        assertEquals("CEEF2150-F915-1F1F-1180-906D00000000", schriftgutobjekte.getAkte().getFirst().getIdentifikation().getId());
        assertEquals(BigInteger.ONE, schriftgutobjekte.getAkte().getFirst().getIdentifikation().getNummerImUebergeordnetenContainer());

        assertEquals("XDOMEA-BY", schriftgutobjekte.getAkte().getFirst().getAnwendungsspezifischeErweiterung().getKennung());
        assertEquals("XDOMEA-Erweiterung", schriftgutobjekte.getAkte().getFirst().getAnwendungsspezifischeErweiterung().getName());

        assertEquals("MusterSachgebietsschlüssel", schriftgutobjekte.getAkte().getFirst().getXjustizFachspezifischeDaten().getAktenzeichen().getFirst()
                .getAuswahlAktenzeichen().getAktenzeichenStrukturiert().getSachgebietsschluessel());
        assertEquals("MusterAbteilung", schriftgutobjekte.getAkte().getFirst().getXjustizFachspezifischeDaten().getAktenzeichen().getFirst()
                .getAuswahlAktenzeichen().getAktenzeichenStrukturiert().getAbteilung());
        assertEquals("MusterZusatzkennung", schriftgutobjekte.getAkte().getFirst().getXjustizFachspezifischeDaten().getAktenzeichen().getFirst()
                .getAuswahlAktenzeichen().getAktenzeichenStrukturiert().getZusatzkennung());
        assertEquals("1", schriftgutobjekte.getAkte().getFirst().getXjustizFachspezifischeDaten().getAktenzeichen().getFirst().getAuswahlAktenzeichen()
                .getAktenzeichenStrukturiert().getLaufendeNummer());
        assertEquals("2025", schriftgutobjekte.getAkte().getFirst().getXjustizFachspezifischeDaten().getAktenzeichen().getFirst().getAuswahlAktenzeichen()
                .getAktenzeichenStrukturiert().getJahr());

    }

}
