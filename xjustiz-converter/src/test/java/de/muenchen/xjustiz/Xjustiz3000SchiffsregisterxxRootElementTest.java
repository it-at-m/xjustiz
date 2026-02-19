package de.muenchen.xjustiz;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import de.muenchen.xjustiz.generated.xjustiz3000schiffsregister31.CodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.xjustiz3000schiffsregister31.CodeSCHIRBaustoff;
import de.muenchen.xjustiz.generated.xjustiz3000schiffsregister31.CodeSCHIRHeimathafen;
import de.muenchen.xjustiz.generated.xjustiz3000schiffsregister31.CodeSCHIRSchiffsgattung;
import de.muenchen.xjustiz.generated.xjustiz3000schiffsregister31.NachrichtSchirSchiffsdaten3000001;
import de.muenchen.xjustiz.generated.xjustiz3000schiffsregister31.TypeGDSGrunddaten;
import de.muenchen.xjustiz.generated.xjustiz3000schiffsregister31.TypeGDSHerstellerinformation;
import de.muenchen.xjustiz.generated.xjustiz3000schiffsregister31.TypeGDSKommunikationspartner;
import de.muenchen.xjustiz.generated.xjustiz3000schiffsregister31.TypeGDSNachrichtenkopf;
import de.muenchen.xjustiz.generated.xjustiz3000schiffsregister31.TypeGDSNameNatuerlichePerson;
import de.muenchen.xjustiz.generated.xjustiz3000schiffsregister31.TypeGDSNatuerlichePerson;
import de.muenchen.xjustiz.generated.xjustiz3000schiffsregister31.TypeSCHIRStringList;
import de.muenchen.xjustiz.generated.xjustiz3000schiffsregister31.TypeSCHIRStringListValue;
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
public class Xjustiz3000SchiffsregisterxxRootElementTest {

    @Produce(value = "direct:xjustiz-document-processor")
    private ProducerTemplate sendToDocumentProcessor;

    @Autowired
    private CamelContext camelContext;

    @Test
    void test_namespaces() throws DatatypeConfigurationException {

        final NachrichtSchirSchiffsdaten3000001 nachricht = new NachrichtSchirSchiffsdaten3000001();

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
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, "xjustiz_3000_schiffsregister_3_1.xsd")
                .withBody(nachricht)
                .build();

        final Exchange response = sendToDocumentProcessor.send(request);
        assertNull(response.getException(), "Error during XML creation.");
        final String xml = response.getMessage().getBody(String.class);

        assertTrue(xml.contains(
                "<tns:nachricht.schir.schiffsdaten.3000001 xmlns:tns=\"http://www.xjustiz.de\" xmlns:din91379=\"urn:xoev-de:kosit:xoev:datentyp:din-91379_2022-08\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.xjustiz.de xjustiz_3000_schiffsregister_3_1.xsd\">"),
                "Message root element is invalid.");

    }

    private static NachrichtSchirSchiffsdaten3000001.Fachdaten getFachdaten() throws DatatypeConfigurationException {

        final NachrichtSchirSchiffsdaten3000001.Fachdaten nachricht = new NachrichtSchirSchiffsdaten3000001.Fachdaten();
        final NachrichtSchirSchiffsdaten3000001.Fachdaten.Aufschrift aufschrift = new NachrichtSchirSchiffsdaten3000001.Fachdaten.Aufschrift();

        final CodeGDSGerichteTyp3 codeGDSGerichteTyp3 = new CodeGDSGerichteTyp3();
        codeGDSGerichteTyp3.setCode("001");
        codeGDSGerichteTyp3.setListVersionID("1.0");
        aufschrift.setAmtsgericht(codeGDSGerichteTyp3);
        aufschrift.setRegisterblattnummer("Registerblattnummer");

        nachricht.setAufschrift(aufschrift);

        final NachrichtSchirSchiffsdaten3000001.Fachdaten.AbteilungIBSR abteilungIBSR = new NachrichtSchirSchiffsdaten3000001.Fachdaten.AbteilungIBSR();

        final TypeSCHIRStringList typeSCHIRStringList = new TypeSCHIRStringList();
        final TypeSCHIRStringListValue typeSCHIRStringListValue = new TypeSCHIRStringListValue();
        typeSCHIRStringListValue.setWert("Wert");
        typeSCHIRStringList.getEintraege().add(typeSCHIRStringListValue);
        abteilungIBSR.setName(typeSCHIRStringList);
        abteilungIBSR.setVerdraengung(typeSCHIRStringList);
        abteilungIBSR.setMaschinenleistung(typeSCHIRStringList);

        abteilungIBSR.setAngabenEichschein(typeSCHIRStringList);
        abteilungIBSR.getAngabenEichschein().getEintraege().add(typeSCHIRStringListValue);

        final NachrichtSchirSchiffsdaten3000001.Fachdaten.AbteilungIBSR.Eintragung eintragung = new NachrichtSchirSchiffsdaten3000001.Fachdaten.AbteilungIBSR.Eintragung();
        eintragung.setEintragungText("Text");
        eintragung.setDatum(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        abteilungIBSR.getEintragung().add(eintragung);

        final NachrichtSchirSchiffsdaten3000001.Fachdaten.AbteilungIBSR.Schiffsgattung schiffsgattung = new NachrichtSchirSchiffsdaten3000001.Fachdaten.AbteilungIBSR.Schiffsgattung();
        final CodeSCHIRSchiffsgattung codeSCHIRSchiffsgattung = new CodeSCHIRSchiffsgattung();
        codeSCHIRSchiffsgattung.setCode("001");
        codeSCHIRSchiffsgattung.setListVersionID("1.0");
        schiffsgattung.setGattung(codeSCHIRSchiffsgattung);
        abteilungIBSR.getSchiffsgattung().add(schiffsgattung);

        final CodeSCHIRBaustoff codeSCHIRBaustoff = new CodeSCHIRBaustoff();
        codeSCHIRBaustoff.setCode("001");
        codeSCHIRBaustoff.setListVersionID("1.0");
        abteilungIBSR.setBaustoff(codeSCHIRBaustoff);

        abteilungIBSR.setBaustoffSonstige("baustoffSonstige");

        final NachrichtSchirSchiffsdaten3000001.Fachdaten.AbteilungIBSR.Stapellauf stapellauf = new NachrichtSchirSchiffsdaten3000001.Fachdaten.AbteilungIBSR.Stapellauf();
        stapellauf.setJahr(BigInteger.ONE);
        stapellauf.setBauort("Bauort");
        stapellauf.setWerft("Werft");
        abteilungIBSR.getStapellauf().add(stapellauf);

        final NachrichtSchirSchiffsdaten3000001.Fachdaten.AbteilungIBSR.Heimatort heimatort = new NachrichtSchirSchiffsdaten3000001.Fachdaten.AbteilungIBSR.Heimatort();
        final CodeSCHIRHeimathafen codeSCHIRHeimathafen = new CodeSCHIRHeimathafen();
        codeSCHIRHeimathafen.setCode("001");
        codeSCHIRHeimathafen.setListVersionID("1.0");
        heimatort.setHafen(codeSCHIRHeimathafen);
        abteilungIBSR.setHeimatort(heimatort);

        nachricht.setAbteilungIBSR(abteilungIBSR);

        final NachrichtSchirSchiffsdaten3000001.Fachdaten.AbteilungII ableitungII = new NachrichtSchirSchiffsdaten3000001.Fachdaten.AbteilungII();
        final NachrichtSchirSchiffsdaten3000001.Fachdaten.AbteilungII.Eintrag eintrag = new NachrichtSchirSchiffsdaten3000001.Fachdaten.AbteilungII.Eintrag();
        eintrag.setLfdNr("1");

        final TypeGDSNatuerlichePerson natuerlichePerson = new TypeGDSNatuerlichePerson();
        final TypeGDSNameNatuerlichePerson nameNatuerlichePerson = new TypeGDSNameNatuerlichePerson();
        nameNatuerlichePerson.setNachname("Nachname");
        natuerlichePerson.setVollerName(nameNatuerlichePerson);
        eintrag.setEigentuemerPerson(natuerlichePerson);
        ableitungII.getEintrag().add(eintrag);
        nachricht.setAbteilungII(ableitungII);

        nachricht.setAbteilungIII(new NachrichtSchirSchiffsdaten3000001.Fachdaten.AbteilungIII());

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
