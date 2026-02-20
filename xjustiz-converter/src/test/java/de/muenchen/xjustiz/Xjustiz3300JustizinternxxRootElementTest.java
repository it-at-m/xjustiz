package de.muenchen.xjustiz;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import de.muenchen.xjustiz.generated.xjustiz3300justizintern10.CodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.xjustiz3300justizintern10.NachrichtIntAbgabeInnerhalbDerJustiz3300001;
import de.muenchen.xjustiz.generated.xjustiz3300justizintern10.TypeGDSGrunddaten;
import de.muenchen.xjustiz.generated.xjustiz3300justizintern10.TypeGDSHerstellerinformation;
import de.muenchen.xjustiz.generated.xjustiz3300justizintern10.TypeGDSKommunikationspartner;
import de.muenchen.xjustiz.generated.xjustiz3300justizintern10.TypeGDSNachrichtenkopf;
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
public class Xjustiz3300JustizinternxxRootElementTest {

    @Produce(value = "direct:xjustiz-document-processor")
    private ProducerTemplate sendToDocumentProcessor;

    @Autowired
    private CamelContext camelContext;

    @Test
    void test_namespace() throws DatatypeConfigurationException {

        final NachrichtIntAbgabeInnerhalbDerJustiz3300001 nachricht = new NachrichtIntAbgabeInnerhalbDerJustiz3300001();
        nachricht.setGrunddaten(new TypeGDSGrunddaten());
        final TypeGDSNachrichtenkopf nachrichtenkopf = new TypeGDSNachrichtenkopf();
        nachrichtenkopf.setErstellungszeitpunkt(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        nachrichtenkopf.setXjustizVersion("3.6.2");

        nachrichtenkopf.setAbsender(getAbsender());
        nachrichtenkopf.setHerstellerinformation(getHerstellerinformation());

        nachrichtenkopf.setEmpfaenger(getEmpfanger());

        nachricht.setNachrichtenkopf(nachrichtenkopf);

        nachricht.setSchriftgutobjekte(new de.muenchen.xjustiz.generated.xjustiz3300justizintern10.TypeGDSSchriftgutobjekte());

        nachricht.setFachdaten(new NachrichtIntAbgabeInnerhalbDerJustiz3300001.Fachdaten());

        final Exchange request = ExchangeBuilder.anExchange(camelContext)
                .withHeader(DynamicXmlMarshaller.SCHEMA_PATH, "xsd/xjustiz-x-x-x-xsd/")
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, "xjustiz_3300_justizintern_1_0.xsd")
                .withBody(nachricht)
                .build();

        final Exchange response = sendToDocumentProcessor.send(request);
        assertNull(response.getException(), "Error during XML creation.");
        final String xml = response.getMessage().getBody(String.class);

        assertTrue(xml.contains(
                "<tns:nachricht.int.abgabeInnerhalbDerJustiz.3300001 xmlns:tns=\"http://www.xjustiz.de\" xmlns:din91379=\"urn:xoev-de:kosit:xoev:datentyp:din-91379_2022-08\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.xjustiz.de xjustiz_3300_justizintern_1_0.xsd\">"),
                "Message root element is invalid.");

    }

    private TypeGDSHerstellerinformation getHerstellerinformation() {

        final TypeGDSHerstellerinformation herstellerinformation = new TypeGDSHerstellerinformation();
        herstellerinformation.setVersion("version");
        herstellerinformation.setHerstellerDesProdukts("herstellerDesProdukte");
        herstellerinformation.setNameDesProdukts("nameDesProdukts");
        return herstellerinformation;

    }

    private static TypeGDSNachrichtenkopf.Absender getAbsender() {
        final TypeGDSNachrichtenkopf.Absender absenderInformation = new TypeGDSNachrichtenkopf.Absender();
        absenderInformation.setEigeneNachrichtenID(UUID.randomUUID().toString());
        final TypeGDSKommunikationspartner.AuswahlKommunikationspartner auswahlKommunikationspartner = new TypeGDSKommunikationspartner.AuswahlKommunikationspartner();
        CodeGDSGerichteTyp3 codeGDSGerichteTyp3 = new CodeGDSGerichteTyp3();
        codeGDSGerichteTyp3.setCode("D2601");
        codeGDSGerichteTyp3.setListVersionID("3.6");
        auswahlKommunikationspartner.setGericht(codeGDSGerichteTyp3);
        TypeGDSKommunikationspartner kommunikationspartner = new TypeGDSKommunikationspartner();
        kommunikationspartner.setAuswahlKommunikationspartner(auswahlKommunikationspartner);
        absenderInformation.setInformationen(kommunikationspartner);

        return absenderInformation;
    }

    private static TypeGDSNachrichtenkopf.Empfaenger getEmpfanger() {

        final TypeGDSNachrichtenkopf.Empfaenger empfaenger = new de.muenchen.xjustiz.generated.xjustiz3300justizintern10.TypeGDSNachrichtenkopf.Empfaenger();

        final CodeGDSGerichteTyp3 codeGDSGerichteTyp3 = new de.muenchen.xjustiz.generated.xjustiz3300justizintern10.CodeGDSGerichteTyp3();
        codeGDSGerichteTyp3.setCode("D2601");
        codeGDSGerichteTyp3.setListVersionID("3.6");

        final TypeGDSKommunikationspartner informationPartner = new TypeGDSKommunikationspartner();
        final TypeGDSKommunikationspartner.AuswahlKommunikationspartner kommunikationspartner = new de.muenchen.xjustiz.generated.xjustiz3300justizintern10.TypeGDSKommunikationspartner.AuswahlKommunikationspartner();
        kommunikationspartner.setGericht(codeGDSGerichteTyp3);
        informationPartner.setAuswahlKommunikationspartner(kommunikationspartner);
        empfaenger.setInformationen(informationPartner);
        final TypeGDSNachrichtenkopf.Empfaenger.AuswahlAktenzeichen auswahlAktenzeichen = new TypeGDSNachrichtenkopf.Empfaenger.AuswahlAktenzeichen();
        auswahlAktenzeichen.setAktenzeichenNeu(true);
        empfaenger.setAuswahlAktenzeichen(auswahlAktenzeichen);
        return empfaenger;
    }

}
