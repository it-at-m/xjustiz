package de.muenchen.xjustiz;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import de.muenchen.xjustiz.generated.xjustiz2600zwangsvollstreckung34.CodeGDSAnschriftstyp;
import de.muenchen.xjustiz.generated.xjustiz2600zwangsvollstreckung34.CodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.xjustiz2600zwangsvollstreckung34.NachrichtZvstrDurchsuchungsanordnung2600001;
import de.muenchen.xjustiz.generated.xjustiz2600zwangsvollstreckung34.TypeGDSGrunddaten;
import de.muenchen.xjustiz.generated.xjustiz2600zwangsvollstreckung34.TypeGDSHerstellerinformation;
import de.muenchen.xjustiz.generated.xjustiz2600zwangsvollstreckung34.TypeGDSKommunikationspartner;
import de.muenchen.xjustiz.generated.xjustiz2600zwangsvollstreckung34.TypeGDSNachrichtenkopf;
import de.muenchen.xjustiz.generated.xjustiz2600zwangsvollstreckung34.TypeGDSRefRollennummer;
import de.muenchen.xjustiz.generated.xjustiz2600zwangsvollstreckung34.TypeGDSSchriftgutobjekte;
import de.muenchen.xjustiz.generated.xjustiz2600zwangsvollstreckung34.TypeZVSTRBeteiligterZusatz;
import java.math.BigDecimal;
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
public class Xjustiz2600ZwangsvollstreckungxxRootElementTest {

    @Produce(value = "direct:xjustiz-document-processor")
    private ProducerTemplate sendToDocumentProcessor;

    @Autowired
    private CamelContext camelContext;

    @Test
    void test_namespaces() throws DatatypeConfigurationException {

        final NachrichtZvstrDurchsuchungsanordnung2600001 nachricht = new NachrichtZvstrDurchsuchungsanordnung2600001();

        nachricht.setFachdaten(getFachdaten());
        nachricht.setGrunddaten(new TypeGDSGrunddaten());
        nachricht.setSchriftgutobjekte(new TypeGDSSchriftgutobjekte());

        final TypeGDSNachrichtenkopf nachrichtenkopf = new TypeGDSNachrichtenkopf();
        nachrichtenkopf.setErstellungszeitpunkt(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        nachrichtenkopf.setXjustizVersion("3.6.2");
        nachrichtenkopf.setAbsender(getAbsender());
        nachrichtenkopf.setEmpfaenger(getEmpfaenger());
        nachrichtenkopf.setHerstellerinformation(getHerstellerinformation());

        nachricht.setNachrichtenkopf(nachrichtenkopf);

        final Exchange request = ExchangeBuilder.anExchange(camelContext)
                .withHeader(DynamicXmlMarshaller.SCHEMA_PATH, "xsd/xjustiz-x-x-x-xsd/")
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, "xjustiz_2600_zwangsvollstreckung_3_4.xsd")
                .withBody(nachricht)
                .build();

        final Exchange response = sendToDocumentProcessor.send(request);
        assertNull(response.getException(), "Error during XML creation.");
        final String xml = response.getMessage().getBody(String.class);

        assertTrue(xml.contains(
                "<tns:nachricht.zvstr.durchsuchungsanordnung.2600001 xmlns:tns=\"http://www.xjustiz.de\" xmlns:din91379=\"urn:xoev-de:kosit:xoev:datentyp:din-91379_2022-08\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.xjustiz.de xjustiz_2600_zwangsvollstreckung_3_4.xsd\">"),
                "Message root element is invalid.");

    }

    private static NachrichtZvstrDurchsuchungsanordnung2600001.Fachdaten getFachdaten() throws DatatypeConfigurationException {

        final NachrichtZvstrDurchsuchungsanordnung2600001.Fachdaten fachdaten = new NachrichtZvstrDurchsuchungsanordnung2600001.Fachdaten();

        final TypeZVSTRBeteiligterZusatz typeZVSTRBeteiligterZusatz = new TypeZVSTRBeteiligterZusatz();
        final TypeGDSRefRollennummer typeGDSRefRollennummer = new TypeGDSRefRollennummer();
        typeGDSRefRollennummer.setRefRollennummer("Rollennummer");
        typeZVSTRBeteiligterZusatz.setRefBeteiligter(typeGDSRefRollennummer);
        fachdaten.getBeteiligterZusatz().add(typeZVSTRBeteiligterZusatz);

        final NachrichtZvstrDurchsuchungsanordnung2600001.Fachdaten.Beschlussdaten beschlussdaten = new NachrichtZvstrDurchsuchungsanordnung2600001.Fachdaten.Beschlussdaten();
        final NachrichtZvstrDurchsuchungsanordnung2600001.Fachdaten.Beschlussdaten.AuswahlBeschluss auswahlBeschluss = new NachrichtZvstrDurchsuchungsanordnung2600001.Fachdaten.Beschlussdaten.AuswahlBeschluss();
        auswahlBeschluss.setDurchsuchungsbeschluss(true);
        beschlussdaten.setAuswahlBeschluss(auswahlBeschluss);

        beschlussdaten.setWeitereGlaeubigerGemaessAnlage(true);
        beschlussdaten.setWeitereVollstreckungstitelGemaessAnlage(true);
        final NachrichtZvstrDurchsuchungsanordnung2600001.Fachdaten.Beschlussdaten.Forderung forderung = new NachrichtZvstrDurchsuchungsanordnung2600001.Fachdaten.Beschlussdaten.Forderung();
        final NachrichtZvstrDurchsuchungsanordnung2600001.Fachdaten.Beschlussdaten.Forderung.AnteilsartRestforderungen anteilsartRestforderungen = new NachrichtZvstrDurchsuchungsanordnung2600001.Fachdaten.Beschlussdaten.Forderung.AnteilsartRestforderungen();
        anteilsartRestforderungen.setRestforderungen("Restforderungen");
        anteilsartRestforderungen.setHoeheInEuro(BigDecimal.ONE);
        forderung.setAnteilsartRestforderungen(anteilsartRestforderungen);
        beschlussdaten.setForderung(forderung);

        final NachrichtZvstrDurchsuchungsanordnung2600001.Fachdaten.Beschlussdaten.AuswahlErmaechtigungenGerichtsvollzieher auswahlErmaechtigungenGerichtsvollzieher = new NachrichtZvstrDurchsuchungsanordnung2600001.Fachdaten.Beschlussdaten.AuswahlErmaechtigungenGerichtsvollzieher();
        final NachrichtZvstrDurchsuchungsanordnung2600001.Fachdaten.Beschlussdaten.AuswahlErmaechtigungenGerichtsvollzieher.ZuDurchsuchendesObjekt zuDurchsuchendesObjekt = new NachrichtZvstrDurchsuchungsanordnung2600001.Fachdaten.Beschlussdaten.AuswahlErmaechtigungenGerichtsvollzieher.ZuDurchsuchendesObjekt();
        final CodeGDSAnschriftstyp codeGDSAnschriftstyp = new CodeGDSAnschriftstyp();
        codeGDSAnschriftstyp.setCode("002");
        codeGDSAnschriftstyp.setListVersionID("3.0");
        zuDurchsuchendesObjekt.setZuDurchsuchendesObjektOertlichkeit(codeGDSAnschriftstyp);
        zuDurchsuchendesObjekt.setBetroffenePerson(typeGDSRefRollennummer);
        zuDurchsuchendesObjekt.setErmaechtigungGerichtsvollzieher(true);
        auswahlErmaechtigungenGerichtsvollzieher.setZuDurchsuchendesObjekt(zuDurchsuchendesObjekt);

        beschlussdaten.getAuswahlErmaechtigungenGerichtsvollzieher().add(auswahlErmaechtigungenGerichtsvollzieher);

        fachdaten.setBeschlussdaten(beschlussdaten);

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
