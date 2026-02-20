package de.muenchen.xjustiz;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import de.muenchen.xjustiz.generated.xjustiz2900dabag32.CodeDABAGEintragungsgrundlagentyp;
import de.muenchen.xjustiz.generated.xjustiz2900dabag32.CodeDABAGWirtschaftsart;
import de.muenchen.xjustiz.generated.xjustiz2900dabag32.CodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.xjustiz2900dabag32.NachrichtDabagGrundbuchauszug2900002;
import de.muenchen.xjustiz.generated.xjustiz2900dabag32.TypeDABAGBuchungsstelle;
import de.muenchen.xjustiz.generated.xjustiz2900dabag32.TypeDABAGEintragungsgrundlage;
import de.muenchen.xjustiz.generated.xjustiz2900dabag32.TypeDABAGEintragungsgrundlagentyp;
import de.muenchen.xjustiz.generated.xjustiz2900dabag32.TypeDABAGFlurstueck;
import de.muenchen.xjustiz.generated.xjustiz2900dabag32.TypeDABAGGrundbuchblatt;
import de.muenchen.xjustiz.generated.xjustiz2900dabag32.TypeDABAGGrundstueck;
import de.muenchen.xjustiz.generated.xjustiz2900dabag32.TypeDABAGIdentifikationFlurstueck;
import de.muenchen.xjustiz.generated.xjustiz2900dabag32.TypeGDSGrunddaten;
import de.muenchen.xjustiz.generated.xjustiz2900dabag32.TypeGDSHerstellerinformation;
import de.muenchen.xjustiz.generated.xjustiz2900dabag32.TypeGDSKommunikationspartner;
import de.muenchen.xjustiz.generated.xjustiz2900dabag32.TypeGDSNachrichtenkopf;
import java.math.BigInteger;
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
public class Xjustiz2900DabagxxRootElementTest {

    @Produce(value = "direct:xjustiz-document-processor")
    private ProducerTemplate sendToDocumentProcessor;

    @Autowired
    private CamelContext camelContext;

    @Test
    void test_namespaces() throws DatatypeConfigurationException {

        final NachrichtDabagGrundbuchauszug2900002 nachricht = new NachrichtDabagGrundbuchauszug2900002();

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
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, "xjustiz_2900_dabag_3_2.xsd")
                .withBody(nachricht)
                .build();

        final Exchange response = sendToDocumentProcessor.send(request);
        assertNull(response.getException(), "Error during XML creation.");
        final String xml = response.getMessage().getBody(String.class);

        assertTrue(xml.contains(
                "<tns:nachricht.dabag.grundbuchauszug.2900002 xmlns:tns=\"http://www.xjustiz.de\" xmlns:din91379=\"urn:xoev-de:kosit:xoev:datentyp:din-91379_2022-08\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.xjustiz.de xjustiz_2900_dabag_3_2.xsd\">"),
                "Message root element is invalid.");

    }

    private static NachrichtDabagGrundbuchauszug2900002.Fachdaten getFachdaten() throws DatatypeConfigurationException {

        final NachrichtDabagGrundbuchauszug2900002.Fachdaten nachricht = new NachrichtDabagGrundbuchauszug2900002.Fachdaten();

        final TypeDABAGEintragungsgrundlage typeDABAGEintragungsgrundlage = new TypeDABAGEintragungsgrundlage();
        final TypeDABAGEintragungsgrundlagentyp typeDABAGEintragungsgrundlagentyp = new TypeDABAGEintragungsgrundlagentyp();
        final CodeDABAGEintragungsgrundlagentyp codeDABAGEintragungsgrundlagentyp = new CodeDABAGEintragungsgrundlagentyp();
        codeDABAGEintragungsgrundlagentyp.setCode("001");
        codeDABAGEintragungsgrundlagentyp.setListVersionID("1.0");
        typeDABAGEintragungsgrundlagentyp.setCodeliste(codeDABAGEintragungsgrundlagentyp);
        typeDABAGEintragungsgrundlage.setEintragungsgrundlagentyp(typeDABAGEintragungsgrundlagentyp);
        typeDABAGEintragungsgrundlage.setId(UUID.randomUUID().toString());
        typeDABAGEintragungsgrundlage.setSichtbar(false);

        final TypeDABAGGrundbuchblatt typeDABAGGrundbuchblatt = new TypeDABAGGrundbuchblatt();
        final CodeGDSGerichteTyp3 codeGDSGerichteTyp3 = new CodeGDSGerichteTyp3();
        codeGDSGerichteTyp3.setCode("001");
        codeGDSGerichteTyp3.setListVersionID("2.0");
        typeDABAGGrundbuchblatt.setAmtsgericht(codeGDSGerichteTyp3);
        typeDABAGGrundbuchblatt.setBlattnummer("1");
        TypeDABAGBuchungsstelle typeDABAGBuchungsstelle = new TypeDABAGBuchungsstelle();
        typeDABAGBuchungsstelle.setId(UUID.randomUUID().toString());
        typeDABAGBuchungsstelle.setLfdNummer("1");

        final TypeDABAGBuchungsstelle.AuswahlBuchungsstelle auswahlBuchungsstelle = new TypeDABAGBuchungsstelle.AuswahlBuchungsstelle();

        final TypeDABAGGrundstueck typeDABAGGrundstueck = new TypeDABAGGrundstueck();
        final TypeDABAGFlurstueck typeDABAGFlurstueck = new TypeDABAGFlurstueck();

        final TypeDABAGIdentifikationFlurstueck typeDABAGIdentifikationFlurstueck = new TypeDABAGIdentifikationFlurstueck();
        typeDABAGIdentifikationFlurstueck.setId("ID");
        typeDABAGIdentifikationFlurstueck.setGemarkungsschluessel("Gemarkungsschluessel");
        final TypeDABAGIdentifikationFlurstueck.Flurstuecksnummer flurstuecksnummer = new TypeDABAGIdentifikationFlurstueck.Flurstuecksnummer();
        flurstuecksnummer.setNenner("Nenner");
        flurstuecksnummer.setZaehler("Zaehler");
        typeDABAGIdentifikationFlurstueck.setFlurstuecksnummer(flurstuecksnummer);
        typeDABAGIdentifikationFlurstueck.setFlurnummer(BigInteger.ONE);

        typeDABAGFlurstueck.setIdentifikationFlurstueck(typeDABAGIdentifikationFlurstueck);
        typeDABAGFlurstueck.getLage().add("Lage");

        final CodeDABAGWirtschaftsart codeDABAGWirtschaftsart = new CodeDABAGWirtschaftsart();
        codeDABAGWirtschaftsart.setCode("001");
        codeDABAGWirtschaftsart.setListVersionID("1.0");
        typeDABAGFlurstueck.getWirtschaftsart().add(codeDABAGWirtschaftsart);

        typeDABAGGrundstueck.getFlurstuecke().add(typeDABAGFlurstueck);

        auswahlBuchungsstelle.setGrundstueck(typeDABAGGrundstueck);
        typeDABAGBuchungsstelle.setAuswahlBuchungsstelle(auswahlBuchungsstelle);

        typeDABAGGrundbuchblatt.getBuchungsstelle().add(typeDABAGBuchungsstelle);

        final TypeDABAGGrundbuchblatt.Grundbuchbezirk grundbuchbezirk = new TypeDABAGGrundbuchblatt.Grundbuchbezirk();
        grundbuchbezirk.setNummer("Nummer");
        grundbuchbezirk.setName("Name");
        typeDABAGGrundbuchblatt.setGrundbuchbezirk(grundbuchbezirk);
        nachricht.getGrundbuchblatt().add(typeDABAGGrundbuchblatt);

        return nachricht;
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
