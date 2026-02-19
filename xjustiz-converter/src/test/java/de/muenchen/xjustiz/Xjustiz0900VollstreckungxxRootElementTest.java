package de.muenchen.xjustiz;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.CodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.CodeVSTRAnredePartei;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.CodeVSTREintragungsgruendeInsolvenzgericht;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.CodeVSTREntscheidungsinhaltSchuldnerwiderspruch;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.CodeVSTRGerichtsvollzieherDienstbezeichnung;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.NachrichtVstrEntscheidungSchuldnerwiderspruch0900001;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.TypeGDSAktenzeichen;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.TypeGDSGrunddaten;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.TypeGDSHerstellerinformation;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.TypeGDSKommunikationspartner;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.TypeGDSNachrichtenkopf;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.TypeGDSRefBeteiligtennummer;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.TypeVSTRBeteiligterZusatz;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.TypeVSTREintragungsanordnungAllgemein;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.TypeVSTREntscheidungSchuldnerwiderspruch;
import de.muenchen.xjustiz.generated.xjustiz0900vollstreckung33.TypeVSTRGerichtsvollzieher;
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
public class Xjustiz0900VollstreckungxxRootElementTest {

    @Produce(value = "direct:xjustiz-document-processor")
    private ProducerTemplate sendToDocumentProcessor;

    @Autowired
    private CamelContext camelContext;

    @Test
    void test_namespaces() throws DatatypeConfigurationException {

        final NachrichtVstrEntscheidungSchuldnerwiderspruch0900001 nachricht = new NachrichtVstrEntscheidungSchuldnerwiderspruch0900001();
        nachricht.setGrunddaten(new TypeGDSGrunddaten());

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
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, "xjustiz_0900_vollstreckung_3_3.xsd")
                .withBody(nachricht)
                .build();

        final Exchange response = sendToDocumentProcessor.send(request);
        assertNull(response.getException(), "Error during XML creation.");
        final String xml = response.getMessage().getBody(String.class);

        assertTrue(xml.contains(
                "<tns:nachricht.vstr.entscheidung.schuldnerwiderspruch.0900001 xmlns:tns=\"http://www.xjustiz.de\" xmlns:din91379=\"urn:xoev-de:kosit:xoev:datentyp:din-91379_2022-08\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.xjustiz.de xjustiz_0900_vollstreckung_3_3.xsd\">"),
                "Message root element is invalid.");

    }

    private static NachrichtVstrEntscheidungSchuldnerwiderspruch0900001.Fachdaten getFachdaten() throws DatatypeConfigurationException {

        final NachrichtVstrEntscheidungSchuldnerwiderspruch0900001.Fachdaten fachdaten = new NachrichtVstrEntscheidungSchuldnerwiderspruch0900001.Fachdaten();
        final TypeVSTRBeteiligterZusatz typeVSTRBeteiligterZusatz = new TypeVSTRBeteiligterZusatz();
        final TypeGDSRefBeteiligtennummer typeGDSRefBeteiligtennummer = new TypeGDSRefBeteiligtennummer();
        typeGDSRefBeteiligtennummer.setRefBeteiligtennummer("beteiligtenNummer");
        typeVSTRBeteiligterZusatz.setBeteiligterReferenz(typeGDSRefBeteiligtennummer);
        final CodeVSTRAnredePartei codeVSTRAnredePartei = new CodeVSTRAnredePartei();
        codeVSTRAnredePartei.setCode("001");
        codeVSTRAnredePartei.setListVersionID("1.0");
        typeVSTRBeteiligterZusatz.setAnrede(codeVSTRAnredePartei);
        fachdaten.setBeteiligterZusatz(typeVSTRBeteiligterZusatz);
        final TypeVSTREntscheidungSchuldnerwiderspruch entscheidungSchuldnerwiderspruch = new TypeVSTREntscheidungSchuldnerwiderspruch();
        final CodeVSTREntscheidungsinhaltSchuldnerwiderspruch codeVSTREntscheidungsinhaltSchuldnerwiderspruch = new CodeVSTREntscheidungsinhaltSchuldnerwiderspruch();
        codeVSTREntscheidungsinhaltSchuldnerwiderspruch.setListVersionID("2.0");
        codeVSTREntscheidungsinhaltSchuldnerwiderspruch.setCode("001");
        entscheidungSchuldnerwiderspruch.setInhaltDerEntscheidung(codeVSTREntscheidungsinhaltSchuldnerwiderspruch);
        entscheidungSchuldnerwiderspruch.setDatumDerEntscheidung(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        final TypeVSTREintragungsanordnungAllgemein eintragungsanordnungAllgemein = new TypeVSTREintragungsanordnungAllgemein();
        eintragungsanordnungAllgemein.setDatumDerEintragungsanordnung(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        final TypeGDSAktenzeichen typeGDSAktenzeichen = new TypeGDSAktenzeichen();
        final TypeGDSAktenzeichen.AuswahlAktenzeichen auswahlAktenzeichen = new TypeGDSAktenzeichen.AuswahlAktenzeichen();
        auswahlAktenzeichen.setAktenzeichenFreitext("auswahlAktenzeichenFreitext");
        typeGDSAktenzeichen.setAuswahlAktenzeichen(auswahlAktenzeichen);
        eintragungsanordnungAllgemein.setAktenzeichenEintragungsanordnung(typeGDSAktenzeichen);
        final TypeVSTREintragungsanordnungAllgemein.AuswahlAnordnungsbehoerdeEintragungsgrund anordnungsbehoerdeEintragungsgrund = new TypeVSTREintragungsanordnungAllgemein.AuswahlAnordnungsbehoerdeEintragungsgrund();

        final TypeVSTREintragungsanordnungAllgemein.AuswahlAnordnungsbehoerdeEintragungsgrund.AnordnungGerichtsvollzieherVollstreckungsbehoerde vollstreckungsbehoerde = new TypeVSTREintragungsanordnungAllgemein.AuswahlAnordnungsbehoerdeEintragungsgrund.AnordnungGerichtsvollzieherVollstreckungsbehoerde();
        final TypeVSTREintragungsanordnungAllgemein.AuswahlAnordnungsbehoerdeEintragungsgrund.AnordnungGerichtsvollzieherVollstreckungsbehoerde.AuswahlGerichtsvollzieherVollstreckungsbehoerde anordungGerichtsvollzieherVollstreckungsbehoerde = new TypeVSTREintragungsanordnungAllgemein.AuswahlAnordnungsbehoerdeEintragungsgrund.AnordnungGerichtsvollzieherVollstreckungsbehoerde.AuswahlGerichtsvollzieherVollstreckungsbehoerde();

        final TypeVSTRGerichtsvollzieher typeVSTRGerichtsvollzieher = new TypeVSTRGerichtsvollzieher();
        typeVSTRGerichtsvollzieher.setName("typeVSTRGerichtsvollzieherName");
        typeVSTRGerichtsvollzieher.setVorname("typeVSTRGerichtsvollzieherVorname");
        final CodeGDSGerichteTyp3 codeGDSGerichteTyp3 = new CodeGDSGerichteTyp3();
        codeGDSGerichteTyp3.setCode("001");
        codeGDSGerichteTyp3.setListVersionID("2.0");
        typeVSTRGerichtsvollzieher.setAmtsgericht(codeGDSGerichteTyp3);
        final CodeVSTRGerichtsvollzieherDienstbezeichnung codeVSTRGerichtsvollzieherDienstbezeichnung = new CodeVSTRGerichtsvollzieherDienstbezeichnung();
        codeVSTRGerichtsvollzieherDienstbezeichnung.setCode("001");
        codeVSTRGerichtsvollzieherDienstbezeichnung.setListVersionID("2.0");
        typeVSTRGerichtsvollzieher.setDienstbezeichnung(codeVSTRGerichtsvollzieherDienstbezeichnung);

        anordungGerichtsvollzieherVollstreckungsbehoerde.setGerichtsvollzieher(typeVSTRGerichtsvollzieher);

        vollstreckungsbehoerde.setAuswahlGerichtsvollzieherVollstreckungsbehoerde(anordungGerichtsvollzieherVollstreckungsbehoerde);
        anordnungsbehoerdeEintragungsgrund.setAnordnungGerichtsvollzieherVollstreckungsbehoerde(vollstreckungsbehoerde);

        eintragungsanordnungAllgemein.setAuswahlAnordnungsbehoerdeEintragungsgrund(anordnungsbehoerdeEintragungsgrund);

        final TypeVSTREintragungsanordnungAllgemein.AuswahlAnordnungsbehoerdeEintragungsgrund eintragungsgrund = new TypeVSTREintragungsanordnungAllgemein.AuswahlAnordnungsbehoerdeEintragungsgrund();
        final TypeVSTREintragungsanordnungAllgemein.AuswahlAnordnungsbehoerdeEintragungsgrund.AnordnungInsolvenzgericht anordnungInsolvenzgericht = new TypeVSTREintragungsanordnungAllgemein.AuswahlAnordnungsbehoerdeEintragungsgrund.AnordnungInsolvenzgericht();
        anordnungInsolvenzgericht.setInsolvenzgericht(codeGDSGerichteTyp3);
        final CodeVSTREintragungsgruendeInsolvenzgericht eintragungsgruendeInsolvenzgericht = new CodeVSTREintragungsgruendeInsolvenzgericht();
        eintragungsgruendeInsolvenzgericht.setCode("001");
        eintragungsgruendeInsolvenzgericht.setListVersionID("2.1");
        anordnungInsolvenzgericht.setEintragungsgrundInsolvenzgericht(eintragungsgruendeInsolvenzgericht);
        anordnungInsolvenzgericht.setDatumErlassDesBeschlusses(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        eintragungsgrund.setAnordnungInsolvenzgericht(anordnungInsolvenzgericht);

        eintragungsanordnungAllgemein.setAuswahlAnordnungsbehoerdeEintragungsgrund(eintragungsgrund);
        entscheidungSchuldnerwiderspruch.setEintragungsanordnungAllgemein(eintragungsanordnungAllgemein);

        fachdaten.setEntscheidungSchuldnerwiderspruch(entscheidungSchuldnerwiderspruch);

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
