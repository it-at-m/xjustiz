package de.muenchen.xjustiz;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import de.muenchen.xjustiz.generated.xjustiz2500gerichtsvollzieher33.CodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.xjustiz2500gerichtsvollzieher33.NachrichtGvzUebernahmebestaetigung2500002;
import de.muenchen.xjustiz.generated.xjustiz2500gerichtsvollzieher33.TypeGDSHerstellerinformation;
import de.muenchen.xjustiz.generated.xjustiz2500gerichtsvollzieher33.TypeGDSKommunikationspartner;
import de.muenchen.xjustiz.generated.xjustiz2500gerichtsvollzieher33.TypeGDSNachrichtenkopf;
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
public class Xjustiz2500GerichtsvollzieherxxRootElementTest {

    @Produce(value = "direct:xjustiz-document-processor")
    private ProducerTemplate sendToDocumentProcessor;

    @Autowired
    private CamelContext camelContext;

    @Test
    void test_namespaces() throws DatatypeConfigurationException {

        final NachrichtGvzUebernahmebestaetigung2500002 nachricht = new NachrichtGvzUebernahmebestaetigung2500002();

        nachricht.setFachdaten(getFachdaten());

        final TypeGDSNachrichtenkopf nachrichtenkopf = new TypeGDSNachrichtenkopf();
        nachrichtenkopf.setErstellungszeitpunkt(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        nachrichtenkopf.setXjustizVersion("3.6.2");
        nachrichtenkopf.setAbsender(getAbsender());
        nachrichtenkopf.setEmpfaenger(getEmpfaenger());
        nachrichtenkopf.setHerstellerinformation(getHerstellerinformation());

        nachricht.setNachrichtenkopf(nachrichtenkopf);

        final Exchange request = ExchangeBuilder.anExchange(camelContext)
                .withHeader(DynamicXmlMarshaller.SCHEMA_PATH, "xsd/xjustiz-x-x-x-xsd/")
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, "xjustiz_2500_gerichtsvollzieher_3_3.xsd")
                .withBody(nachricht)
                .build();

        final Exchange response = sendToDocumentProcessor.send(request);
        assertNull(response.getException(), "Error during XML creation.");
        final String xml = response.getMessage().getBody(String.class);

        assertTrue(xml.contains(
                "<tns:nachricht.gvz.uebernahmebestaetigung.2500002 xmlns:tns=\"http://www.xjustiz.de\" xmlns:din91379=\"urn:xoev-de:kosit:xoev:datentyp:din-91379_2022-08\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.xjustiz.de xjustiz_2500_gerichtsvollzieher_3_3.xsd\">"),
                "Message root element is invalid.");

    }

    private static NachrichtGvzUebernahmebestaetigung2500002.Fachdaten getFachdaten() throws DatatypeConfigurationException {

        final NachrichtGvzUebernahmebestaetigung2500002.Fachdaten nachricht = new NachrichtGvzUebernahmebestaetigung2500002.Fachdaten();
        final NachrichtGvzUebernahmebestaetigung2500002.Fachdaten.Uebernahmebestaetigung uebernahmebestaetigung = new NachrichtGvzUebernahmebestaetigung2500002.Fachdaten.Uebernahmebestaetigung();

        final NachrichtGvzUebernahmebestaetigung2500002.Fachdaten.Uebernahmebestaetigung.AzUebernehmenderGV azUebernehmenderGV = new NachrichtGvzUebernahmebestaetigung2500002.Fachdaten.Uebernahmebestaetigung.AzUebernehmenderGV();
        azUebernehmenderGV.setDrNummer(BigInteger.ONE);
        azUebernehmenderGV.setDrJahrgang(BigInteger.ONE);
        azUebernehmenderGV.setDrRegister("Register");
        uebernahmebestaetigung.setAzUebernehmenderGV(azUebernehmenderGV);

        final NachrichtGvzUebernahmebestaetigung2500002.Fachdaten.Uebernahmebestaetigung.AzAbgebenderGV azAbzugebenderGV = new NachrichtGvzUebernahmebestaetigung2500002.Fachdaten.Uebernahmebestaetigung.AzAbgebenderGV();
        azAbzugebenderGV.setDrNummer(BigInteger.ONE);
        azAbzugebenderGV.setDrJahrgang(BigInteger.ONE);
        azAbzugebenderGV.setDrRegister("Register");
        uebernahmebestaetigung.setAzAbgebenderGV(azAbzugebenderGV);

        nachricht.getUebernahmebestaetigung().add(uebernahmebestaetigung);

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
