package de.muenchen.xjustiz;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import de.muenchen.xjustiz.generated.xjustiz1300aussonderung32.CodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.xjustiz1300aussonderung32.NachrichtAussArchivJustizZuArchiv1300001;
import de.muenchen.xjustiz.generated.xjustiz1300aussonderung32.TypeAUSSAufbewahrungsdauer;
import de.muenchen.xjustiz.generated.xjustiz1300aussonderung32.TypeAUSSFachdaten;
import de.muenchen.xjustiz.generated.xjustiz1300aussonderung32.TypeAUSSMetadatenAussonderung;
import de.muenchen.xjustiz.generated.xjustiz1300aussonderung32.TypeGDSHerstellerinformation;
import de.muenchen.xjustiz.generated.xjustiz1300aussonderung32.TypeGDSKommunikationspartner;
import de.muenchen.xjustiz.generated.xjustiz1300aussonderung32.TypeGDSNachrichtenkopf;
import de.muenchen.xjustiz.generated.xjustiz1300aussonderung32.TypeGDSRefSGO;
import de.muenchen.xjustiz.generated.xjustiz1300aussonderung32.TypeGDSSchriftgutobjekte;
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
public class Xjustiz1300AussonderungxxRootElementTest {

    @Produce(value = "direct:xjustiz-document-processor")
    private ProducerTemplate sendToDocumentProcessor;

    @Autowired
    private CamelContext camelContext;

    @Test
    void test_namespaces() throws DatatypeConfigurationException {

        final NachrichtAussArchivJustizZuArchiv1300001 nachricht = new NachrichtAussArchivJustizZuArchiv1300001();

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
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, "xjustiz_1300_aussonderung_3_2.xsd")
                .withBody(nachricht)
                .build();

        final Exchange response = sendToDocumentProcessor.send(request);
        assertNull(response.getException(), "Error during XML creation.");
        final String xml = response.getMessage().getBody(String.class);

        assertTrue(xml.contains(
                "<ns2:nachricht.auss.archiv.justizZuArchiv.1300001 xmlns:ns2=\"http://www.xjustiz.de\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.xjustiz.de xjustiz_1300_aussonderung_3_2.xsd\">"),
                "Message root element is invalid.");

    }

    private static NachrichtAussArchivJustizZuArchiv1300001.Fachdaten getFachdaten() {

        final NachrichtAussArchivJustizZuArchiv1300001.Fachdaten fachdaten = new NachrichtAussArchivJustizZuArchiv1300001.Fachdaten();
        final NachrichtAussArchivJustizZuArchiv1300001.Fachdaten.Aussonderungsobjekt aussonderungsobjekt = new NachrichtAussArchivJustizZuArchiv1300001.Fachdaten.Aussonderungsobjekt();

        final TypeAUSSFachdaten typeAUSSFachdaten = new TypeAUSSFachdaten();
        final TypeAUSSMetadatenAussonderung typeAUSSMetadatenAussonderung = new TypeAUSSMetadatenAussonderung();
        final TypeAUSSAufbewahrungsdauer typeAUSSAufbewahrungsdauer = new TypeAUSSAufbewahrungsdauer();
        final TypeAUSSAufbewahrungsdauer.AuswahlAufbewahrungsdauer auswahlAufbewahrungsdauer = new TypeAUSSAufbewahrungsdauer.AuswahlAufbewahrungsdauer();
        auswahlAufbewahrungsdauer.setUnbefristet(true);
        typeAUSSAufbewahrungsdauer.setAuswahlAufbewahrungsdauer(auswahlAufbewahrungsdauer);
        typeAUSSMetadatenAussonderung.setAufbewahrungsdauer(typeAUSSAufbewahrungsdauer);
        typeAUSSFachdaten.setArchivspezifischeMetadaten(typeAUSSMetadatenAussonderung);
        aussonderungsobjekt.setFachdatenAussonderung(typeAUSSFachdaten);

        final TypeGDSSchriftgutobjekte typeGDSSchriftgutobjekte = new TypeGDSSchriftgutobjekte();
        final TypeGDSRefSGO typeGDSRefSGO = new TypeGDSRefSGO();
        typeGDSRefSGO.setRefSgo(UUID.randomUUID().toString());
        typeGDSSchriftgutobjekte.setAnschreiben(typeGDSRefSGO);
        aussonderungsobjekt.setSchriftgutobjekte(typeGDSSchriftgutobjekte);

        fachdaten.getAussonderungsobjekt().add(aussonderungsobjekt);

        return fachdaten;
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
