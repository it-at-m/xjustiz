package de.muenchen.xjustiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.NachrichtGdsBasisnachricht0005006;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSGrunddaten;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSNachrichtenkopf;
import de.muenchen.xjustiz.generated.xjustiz3300justizintern10.NachrichtIntAbgabeInnerhalbDerJustiz3300001;
import de.muenchen.xjustiz.generated.xjustiz3300justizintern10.TypeGDSSchriftgutobjekte;
import jakarta.xml.bind.JAXBException;
import java.io.FileNotFoundException;
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
public class RootElementTest {

    @Produce(value = "direct:xjustiz-document-processor")
    private ProducerTemplate sendToDocumentProcessor;

    @Autowired
    private CamelContext camelContext;

    @Test
    void test_nachrichtGdsBasisnachricht0005006() throws DatatypeConfigurationException {

        NachrichtGdsBasisnachricht0005006 nachricht = new NachrichtGdsBasisnachricht0005006();
        nachricht.setGrunddaten(new TypeGDSGrunddaten());
        TypeGDSNachrichtenkopf nachrichtenkopf = new TypeGDSNachrichtenkopf();
        nachrichtenkopf.setErstellungszeitpunkt(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        nachrichtenkopf.setXjustizVersion("3.5.1");
        final TypeGDSNachrichtenkopf.AuswahlAbsender absender = new TypeGDSNachrichtenkopf.AuswahlAbsender();
        nachrichtenkopf.getAktenzeichenAbsender().add("Aktenzeichen");
        absender.setAbsenderSonstige("Absender");
        nachrichtenkopf.setAuswahlAbsender(absender);
        final TypeGDSNachrichtenkopf.AuswahlEmpfaenger empfaenger = new TypeGDSNachrichtenkopf.AuswahlEmpfaenger();
        CodeGDSGerichteTyp3 codeGDSGerichteTyp3 = new CodeGDSGerichteTyp3();
        codeGDSGerichteTyp3.setCode("D2601");
        codeGDSGerichteTyp3.setListVersionID("3.6");
        empfaenger.setEmpfaengerGericht(codeGDSGerichteTyp3);
        nachrichtenkopf.setAuswahlEmpfaenger(empfaenger);
        nachrichtenkopf.setEigeneNachrichtenID(UUID.randomUUID().toString());

        nachricht.setNachrichtenkopf(nachrichtenkopf);

        final Exchange request = ExchangeBuilder.anExchange(camelContext)
                .withHeader(DynamicXmlMarshaller.SCHEMA_PATH, "xsd/XJustiz-3.5.1-XSD/")
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, "xjustiz_0005_nachrichten_3_1.xsd")
                .withBody(nachricht)
                .build();

        final Exchange response = sendToDocumentProcessor.send(request);
        assertNull(response.getException(), "Error during XML creation.");
        final String xml = response.getMessage().getBody(String.class);

        assertTrue(xml.contains(
                "<tns:nachricht.gds.basisnachricht.0005006 xmlns:tns=\"http://www.xjustiz.de\" xmlns:din91379=\"urn:xoev-de:kosit:xoev:datentyp:din-91379_2022-08\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.xjustiz.de xjustiz_0005_nachrichten_3_1.xsd\">"),
                "Message root element is invalid.");

    }

    //@Test
    void test_nachrichtGdsBasisnachricht0005006_invalid_schema_name() throws DatatypeConfigurationException {

        NachrichtGdsBasisnachricht0005006 nachricht = new NachrichtGdsBasisnachricht0005006();
        nachricht.setGrunddaten(new TypeGDSGrunddaten());
        TypeGDSNachrichtenkopf nachrichtenkopf = new TypeGDSNachrichtenkopf();
        nachrichtenkopf.setErstellungszeitpunkt(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        nachrichtenkopf.setXjustizVersion("3.5.1");
        final TypeGDSNachrichtenkopf.AuswahlAbsender absender = new TypeGDSNachrichtenkopf.AuswahlAbsender();
        nachrichtenkopf.getAktenzeichenAbsender().add("Aktenzeichen");
        absender.setAbsenderSonstige("Absender");
        nachrichtenkopf.setAuswahlAbsender(absender);
        final TypeGDSNachrichtenkopf.AuswahlEmpfaenger empfaenger = new TypeGDSNachrichtenkopf.AuswahlEmpfaenger();
        CodeGDSGerichteTyp3 codeGDSGerichteTyp3 = new CodeGDSGerichteTyp3();
        codeGDSGerichteTyp3.setCode("D2601");
        codeGDSGerichteTyp3.setListVersionID("3.6");
        empfaenger.setEmpfaengerGericht(codeGDSGerichteTyp3);
        nachrichtenkopf.setAuswahlEmpfaenger(empfaenger);
        nachrichtenkopf.setEigeneNachrichtenID(UUID.randomUUID().toString());

        nachricht.setNachrichtenkopf(nachrichtenkopf);

        final Exchange request = ExchangeBuilder.anExchange(camelContext)
                .withHeader(DynamicXmlMarshaller.SCHEMA_PATH, "xsd/XJustiz-3.5.1-XSD/")
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, "changeMe")
                .withBody(nachricht)
                .build();

        final Exchange response = sendToDocumentProcessor.send(request);
        assertNotNull(response.getException(), "Error during XML creation.");
        JAXBException exception = response.getException(JAXBException.class);
        assertEquals("\"de.muenchen.xjustiz.generated.changeMe\" enthält ObjectFactory.class oder jaxb.index nicht", exception.getMessage());

    }

    //@Test
    void test_nachrichtGdsBasisnachricht0005006_invalid_schema_path() throws DatatypeConfigurationException {

        NachrichtGdsBasisnachricht0005006 nachricht = new NachrichtGdsBasisnachricht0005006();
        nachricht.setGrunddaten(new TypeGDSGrunddaten());
        TypeGDSNachrichtenkopf nachrichtenkopf = new TypeGDSNachrichtenkopf();
        nachrichtenkopf.setErstellungszeitpunkt(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        nachrichtenkopf.setXjustizVersion("3.5.1");
        final TypeGDSNachrichtenkopf.AuswahlAbsender absender = new TypeGDSNachrichtenkopf.AuswahlAbsender();
        nachrichtenkopf.getAktenzeichenAbsender().add("Aktenzeichen");
        absender.setAbsenderSonstige("Absender");
        nachrichtenkopf.setAuswahlAbsender(absender);
        final TypeGDSNachrichtenkopf.AuswahlEmpfaenger empfaenger = new TypeGDSNachrichtenkopf.AuswahlEmpfaenger();
        CodeGDSGerichteTyp3 codeGDSGerichteTyp3 = new CodeGDSGerichteTyp3();
        codeGDSGerichteTyp3.setCode("D2601");
        codeGDSGerichteTyp3.setListVersionID("3.6");
        empfaenger.setEmpfaengerGericht(codeGDSGerichteTyp3);
        nachrichtenkopf.setAuswahlEmpfaenger(empfaenger);
        nachrichtenkopf.setEigeneNachrichtenID(UUID.randomUUID().toString());

        nachricht.setNachrichtenkopf(nachrichtenkopf);

        final Exchange request = ExchangeBuilder.anExchange(camelContext)
                .withHeader(DynamicXmlMarshaller.SCHEMA_PATH, "changeMe")
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, "xjustiz_0005_nachrichten_3_1.xsd")
                .withBody(nachricht)
                .build();

        final Exchange response = sendToDocumentProcessor.send(request);
        assertNotNull(response.getException(), "Error during XML creation.");
        FileNotFoundException exception = response.getException(FileNotFoundException.class);
        assertEquals("Cannot find resource: changeMexjustiz_0005_nachrichten_3_1.xsd for URI: changeMexjustiz_0005_nachrichten_3_1.xsd",
                exception.getMessage());

    }

    @Test
    void test_nachrichtIntAbgabeInnerhalbDerJustiz3300001() throws DatatypeConfigurationException {

        NachrichtIntAbgabeInnerhalbDerJustiz3300001 nachricht = new NachrichtIntAbgabeInnerhalbDerJustiz3300001();
        nachricht.setGrunddaten(new de.muenchen.xjustiz.generated.xjustiz3300justizintern10.TypeGDSGrunddaten());
        de.muenchen.xjustiz.generated.xjustiz3300justizintern10.TypeGDSNachrichtenkopf nachrichtenkopf = new de.muenchen.xjustiz.generated.xjustiz3300justizintern10.TypeGDSNachrichtenkopf();
        nachrichtenkopf.setErstellungszeitpunkt(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        nachrichtenkopf.setXjustizVersion("3.5.1");
        final de.muenchen.xjustiz.generated.xjustiz3300justizintern10.TypeGDSNachrichtenkopf.AuswahlAbsender absender = new de.muenchen.xjustiz.generated.xjustiz3300justizintern10.TypeGDSNachrichtenkopf.AuswahlAbsender();
        nachrichtenkopf.getAktenzeichenAbsender().add("Aktenzeichen");
        absender.setAbsenderSonstige("Absender");
        nachrichtenkopf.setAuswahlAbsender(absender);
        final de.muenchen.xjustiz.generated.xjustiz3300justizintern10.TypeGDSNachrichtenkopf.AuswahlEmpfaenger empfaenger = new de.muenchen.xjustiz.generated.xjustiz3300justizintern10.TypeGDSNachrichtenkopf.AuswahlEmpfaenger();
        de.muenchen.xjustiz.generated.xjustiz3300justizintern10.CodeGDSGerichteTyp3 codeGDSGerichteTyp3 = new de.muenchen.xjustiz.generated.xjustiz3300justizintern10.CodeGDSGerichteTyp3();
        codeGDSGerichteTyp3.setCode("D2601");
        codeGDSGerichteTyp3.setListVersionID("3.6");
        empfaenger.setEmpfaengerGericht(codeGDSGerichteTyp3);
        nachrichtenkopf.setAuswahlEmpfaenger(empfaenger);
        nachrichtenkopf.setEigeneNachrichtenID(UUID.randomUUID().toString());
        nachricht.setNachrichtenkopf(nachrichtenkopf);

        TypeGDSSchriftgutobjekte schriftgutobjekte = new TypeGDSSchriftgutobjekte();
        nachricht.setSchriftgutobjekte(schriftgutobjekte);

        NachrichtIntAbgabeInnerhalbDerJustiz3300001.Fachdaten fachdaten = new NachrichtIntAbgabeInnerhalbDerJustiz3300001.Fachdaten();
        nachricht.setFachdaten(fachdaten);

        final Exchange request = ExchangeBuilder.anExchange(camelContext)
                .withHeader(DynamicXmlMarshaller.SCHEMA_PATH, "xsd/XJustiz-3.5.1-XSD/")
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

}
