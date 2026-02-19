package de.muenchen.xjustiz;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import de.muenchen.xjustiz.generated.xjustiz2100ehug31.CodeEHUGEbanzMeldungBFJ;
import de.muenchen.xjustiz.generated.xjustiz2100ehug31.CodeEHUGInfoBFJ;
import de.muenchen.xjustiz.generated.xjustiz2100ehug31.CodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.xjustiz2100ehug31.CodeGDSWaehrungTyp3;
import de.muenchen.xjustiz.generated.xjustiz2100ehug31.NachrichtEhugUebergabe2100001;
import de.muenchen.xjustiz.generated.xjustiz2100ehug31.TypeEHUGFachdaten;
import de.muenchen.xjustiz.generated.xjustiz2100ehug31.TypeGDSGeldbetrag;
import de.muenchen.xjustiz.generated.xjustiz2100ehug31.TypeGDSGrunddaten;
import de.muenchen.xjustiz.generated.xjustiz2100ehug31.TypeGDSHerstellerinformation;
import de.muenchen.xjustiz.generated.xjustiz2100ehug31.TypeGDSKommunikationspartner;
import de.muenchen.xjustiz.generated.xjustiz2100ehug31.TypeGDSNachrichtenkopf;
import java.util.GregorianCalendar;
import java.util.UUID;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication()
@CamelSpringBootTest
@SpringBootTest(classes = { XJustizDocumentRouteBuilder.class })
@ActiveProfiles({ "default" })
public class Xjustiz2100EhugxxRootElementTest {

    @Produce(value = "direct:xjustiz-document-processor")
    private ProducerTemplate sendToDocumentProcessor;

    @Autowired
    private CamelContext camelContext;

    @Test
    void test_namespaces() throws DatatypeConfigurationException {

        final NachrichtEhugUebergabe2100001 nachricht = new NachrichtEhugUebergabe2100001();

        nachricht.setFachdaten(getFachdaten());
        nachricht.setGrunddaten(new TypeGDSGrunddaten());

        final TypeGDSNachrichtenkopf nachrichtenkopf = new TypeGDSNachrichtenkopf();
        nachrichtenkopf.setErstellungszeitpunkt(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        nachrichtenkopf.setXjustizVersion("3.6.2");
        nachrichtenkopf.setAbsender(getAbsender());
        nachrichtenkopf.setEmpfaenger(getEmpfaenger());
        nachrichtenkopf.setHerstellerinformation(getHerstellerinformation());

        nachricht.setNachrichtenkopf(nachrichtenkopf);

        final Exchange request = ExchangeBuilder.anExchange(camelContext)
                .withHeader(DynamicXmlMarshaller.SCHEMA_PATH, "xsd/xjustiz-x-x-x-xsd/")
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, "xjustiz_2100_ehug_3_1.xsd")
                .withBody(nachricht)
                .build();

        final Exchange response = sendToDocumentProcessor.send(request);
        assertNull(response.getException(), "Error during XML creation.");
        final String xml = response.getMessage().getBody(String.class);

        assertTrue(xml.contains(
                "<tns:nachricht.ehug.uebergabe.2100001 xmlns:tns=\"http://www.xjustiz.de\" xmlns:din91379=\"urn:xoev-de:kosit:xoev:datentyp:din-91379_2022-08\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.xjustiz.de xjustiz_2100_ehug_3_1.xsd\">"),
                "Message root element is invalid.");

    }

    private static TypeEHUGFachdaten getFachdaten() throws DatatypeConfigurationException {

        final TypeEHUGFachdaten typeEHUGFachdaten = new TypeEHUGFachdaten();
        typeEHUGFachdaten.setFachdatenEhugVersion("3.2");
        final TypeEHUGFachdaten.VerfahrensgangBFJ verfahrensgangBFJ = new TypeEHUGFachdaten.VerfahrensgangBFJ();
        verfahrensgangBFJ.setBeschlussVom(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        verfahrensgangBFJ.setZustellungAm(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        verfahrensgangBFJ.setEingangAm(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        verfahrensgangBFJ.setBeschwerdeVom(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        verfahrensgangBFJ.setDatumOffenlegungVollstaendig(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        verfahrensgangBFJ.setDatumOffenlegungVollstaendig(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));

        final TypeGDSGeldbetrag typeGDSGeldbetrag = new TypeGDSGeldbetrag();
        typeGDSGeldbetrag.setZahl(1);
        final TypeGDSGeldbetrag.AuswahlWaehrung auswahlWaehrung = new TypeGDSGeldbetrag.AuswahlWaehrung();
        final CodeGDSWaehrungTyp3 codeGDSWaehrungTyp3 = new CodeGDSWaehrungTyp3();
        codeGDSWaehrungTyp3.setCode("1");
        codeGDSWaehrungTyp3.setListVersionID("1.0");
        auswahlWaehrung.setWaehrung(codeGDSWaehrungTyp3);
        typeGDSGeldbetrag.setAuswahlWaehrung(auswahlWaehrung);
        verfahrensgangBFJ.setFestgesetztesOrdnungsgeld(typeGDSGeldbetrag);

        final TypeEHUGFachdaten.VerfahrensgangBFJ.Ordnungsgeld ordnungsgeld = new TypeEHUGFachdaten.VerfahrensgangBFJ.Ordnungsgeld();
        ordnungsgeld.setDatumAndrohungsverfuegung(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        ordnungsgeld.setEingangEinspruch(false);
        ordnungsgeld.setHoeheAngedrOG(typeGDSGeldbetrag);
        ordnungsgeld.setDatumEinspruchEingang(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        ordnungsgeld.setDatumOGBeschluss(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        ordnungsgeld.setAblaufNachfrist(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        ordnungsgeld.setHoeheFestgOG(typeGDSGeldbetrag);
        ordnungsgeld.setDatumZUOG(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        ordnungsgeld.setDatumSofBeschw(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        ordnungsgeld.setEingangSofBeschw(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        verfahrensgangBFJ.getOrdnungsgeld().add(ordnungsgeld);

        verfahrensgangBFJ.setZustellungAm(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));

        final TypeEHUGFachdaten.VerfahrensgangBFJ.Berichtsteil berichtsteil = new TypeEHUGFachdaten.VerfahrensgangBFJ.Berichtsteil();
        final TypeEHUGFachdaten.VerfahrensgangBFJ.Berichtsteil.Korrekturmeldung korrekturmeldung = new TypeEHUGFachdaten.VerfahrensgangBFJ.Berichtsteil.Korrekturmeldung();
        korrekturmeldung.setKorrekturgrundFreitext("freitext");
        final CodeEHUGEbanzMeldungBFJ codeEHUGEbanzMeldungBFJ = new CodeEHUGEbanzMeldungBFJ();
        codeEHUGEbanzMeldungBFJ.setCode("001");
        codeEHUGEbanzMeldungBFJ.setListVersionID("2.0");
        korrekturmeldung.setKorrekturgrund(codeEHUGEbanzMeldungBFJ);
        berichtsteil.setKorrekturmeldung(korrekturmeldung);
        verfahrensgangBFJ.getBerichtsteil().add(berichtsteil);

        typeEHUGFachdaten.setVerfahrensgangBFJ(verfahrensgangBFJ);

        final TypeEHUGFachdaten.ErgaenzungenGesellschaft ergaenzungenGesellschaft = new TypeEHUGFachdaten.ErgaenzungenGesellschaft();
        ergaenzungenGesellschaft.setRefRollennummer("rollennumer");
        final CodeEHUGInfoBFJ codeEHUGInfoBFJ = new CodeEHUGInfoBFJ();
        codeEHUGInfoBFJ.setCode("001");
        codeEHUGInfoBFJ.setListVersionID("2.0");
        ergaenzungenGesellschaft.setInsolvenz(codeEHUGInfoBFJ);
        ergaenzungenGesellschaft.setLiquidation(codeEHUGInfoBFJ);
        ergaenzungenGesellschaft.setLoeschung(codeEHUGInfoBFJ);
        final TypeEHUGFachdaten.ErgaenzungenGesellschaft.AuswahlGeschaeftsjahr auswahlGeschaeftsjahr = new TypeEHUGFachdaten.ErgaenzungenGesellschaft.AuswahlGeschaeftsjahr();
        auswahlGeschaeftsjahr.setGeschaeftsjahr(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        ergaenzungenGesellschaft.setAuswahlGeschaeftsjahr(auswahlGeschaeftsjahr);
        typeEHUGFachdaten.setErgaenzungenGesellschaft(ergaenzungenGesellschaft);

        return typeEHUGFachdaten;
    }

    private static TypeGDSNachrichtenkopf.Empfaenger getEmpfaenger() {
        final TypeGDSNachrichtenkopf.Empfaenger empfaenger = new TypeGDSNachrichtenkopf.Empfaenger();
        empfaenger.setInformationen(getEmpfangerInformation());
        final TypeGDSNachrichtenkopf.Empfaenger.AuswahlAktenzeichen auswahlAktenzeichen = new TypeGDSNachrichtenkopf.Empfaenger.AuswahlAktenzeichen();
        auswahlAktenzeichen.setAktenzeichenFreitext("Freitext");
        empfaenger.setAuswahlAktenzeichen(auswahlAktenzeichen);
        return empfaenger;
    }

    private static TypeGDSNachrichtenkopf.Absender getAbsender() {
        final TypeGDSNachrichtenkopf.Absender absender = new TypeGDSNachrichtenkopf.Absender();
        absender.setAktenzeichen("Aktenzeichen");
        absender.setInformationen(getAbsenderInformation());
        absender.setEigeneNachrichtenID(UUID.randomUUID().toString());
        return absender;
    }

    private TypeGDSHerstellerinformation getHerstellerinformation() {

        final TypeGDSHerstellerinformation herstellerinformation = new TypeGDSHerstellerinformation();
        herstellerinformation.setVersion("version");
        herstellerinformation.setHerstellerDesProdukts("herstellerDesProdukte");
        herstellerinformation.setNameDesProdukts("nameDesProdukts");
        return herstellerinformation;

    }

    private static TypeGDSKommunikationspartner getAbsenderInformation() {
        final TypeGDSKommunikationspartner absenderInformation = new TypeGDSKommunikationspartner();
        final TypeGDSKommunikationspartner.AuswahlKommunikationspartner kommunikationspartner = new TypeGDSKommunikationspartner.AuswahlKommunikationspartner();
        kommunikationspartner.setSonstige("Sonstige");
        absenderInformation.setAuswahlKommunikationspartner(kommunikationspartner);
        return absenderInformation;
    }

    private static TypeGDSKommunikationspartner getEmpfangerInformation() {
        final TypeGDSKommunikationspartner absenderInformation = new TypeGDSKommunikationspartner();
        final TypeGDSKommunikationspartner.AuswahlKommunikationspartner kommunikationspartner = new TypeGDSKommunikationspartner.AuswahlKommunikationspartner();
        final CodeGDSGerichteTyp3 codeGDSGerichteTyp3 = new CodeGDSGerichteTyp3();
        codeGDSGerichteTyp3.setCode("D2601");
        codeGDSGerichteTyp3.setListVersionID("3.6");
        kommunikationspartner.setGericht(codeGDSGerichteTyp3);
        absenderInformation.setAuswahlKommunikationspartner(kommunikationspartner);
        return absenderInformation;
    }

}
