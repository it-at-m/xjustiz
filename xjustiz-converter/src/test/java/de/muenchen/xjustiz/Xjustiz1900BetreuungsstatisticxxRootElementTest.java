package de.muenchen.xjustiz;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.CodeBESTATArtGenehmigung;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.CodeBESTATArtZahlung;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.CodeBESTATBeendigungBetreuung;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.CodeBESTATBetreuerauswahl;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.CodeBESTATEinleitungBetreuung;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.CodeBESTATEntscheidungImLaufendenVerfahren;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.CodeBESTATEntscheidungUeberEinrichtungDerBetreuung;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.CodeBESTATGeschaeftsanfall;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.CodeBESTATVeraenderung;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.CodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.CodeGDSGeschlecht;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.CodeGDSWaehrungTyp3;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.NachrichtBestatMonatsmeldung1900001;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.TypeBESTATVerfahrenserhebung;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.TypeGDSAktenzeichen;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.TypeGDSGeldbetrag;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.TypeGDSGrunddaten;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.TypeGDSHerstellerinformation;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.TypeGDSKommunikationspartner;
import de.muenchen.xjustiz.generated.xjustiz1900betreuungsstatistik31.TypeGDSNachrichtenkopf;
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
public class Xjustiz1900BetreuungsstatisticxxRootElementTest {

    @Produce(value = "direct:xjustiz-document-processor")
    private ProducerTemplate sendToDocumentProcessor;

    @Autowired
    private CamelContext camelContext;

    @Test
    void test_namespaces() throws DatatypeConfigurationException {

        final NachrichtBestatMonatsmeldung1900001 nachricht = new NachrichtBestatMonatsmeldung1900001();

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
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, "xjustiz_1900_betreuungsstatistik_3_1.xsd")
                .withBody(nachricht)
                .build();

        final Exchange response = sendToDocumentProcessor.send(request);
        assertNull(response.getException(), "Error during XML creation.");
        final String xml = response.getMessage().getBody(String.class);

        assertTrue(xml.contains(
                "<tns:nachricht.bestat.monatsmeldung.1900001 xmlns:tns=\"http://www.xjustiz.de\" xmlns:din91379=\"urn:xoev-de:kosit:xoev:datentyp:din-91379_2022-08\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.xjustiz.de xjustiz_1900_betreuungsstatistik_3_1.xsd\">"),
                "Message root element is invalid.");

    }

    private static NachrichtBestatMonatsmeldung1900001.Fachdaten getFachdaten() throws DatatypeConfigurationException {

        final NachrichtBestatMonatsmeldung1900001.Fachdaten fachdaten = new NachrichtBestatMonatsmeldung1900001.Fachdaten();
        final NachrichtBestatMonatsmeldung1900001.Fachdaten.Berichtsmonat berichtsmonat = new NachrichtBestatMonatsmeldung1900001.Fachdaten.Berichtsmonat();
        berichtsmonat.setMonat("1");

        final NachrichtBestatMonatsmeldung1900001.Fachdaten.Berichtsmonat.BesondereErhebung besondereErhebung = new NachrichtBestatMonatsmeldung1900001.Fachdaten.Berichtsmonat.BesondereErhebung();
        besondereErhebung.setSchluesselzahlErhebungseinheit("schluesselzahlErhebungseinheit");

        final NachrichtBestatMonatsmeldung1900001.Fachdaten.Berichtsmonat.BesondereErhebung.SonstigerGeschaeftsanfall sonstigerGeschaeftsanfall = new NachrichtBestatMonatsmeldung1900001.Fachdaten.Berichtsmonat.BesondereErhebung.SonstigerGeschaeftsanfall();
        sonstigerGeschaeftsanfall.setZahl("1");
        final CodeBESTATGeschaeftsanfall codeBESTATGeschaeftsanfall = new CodeBESTATGeschaeftsanfall();
        codeBESTATGeschaeftsanfall.setCode("1");
        codeBESTATGeschaeftsanfall.setListVersionID("2.0");
        sonstigerGeschaeftsanfall.setGeschaeftsanfall(codeBESTATGeschaeftsanfall);
        for (int i = 0; i < 42; i++) {
            besondereErhebung.getSonstigerGeschaeftsanfall().add(sonstigerGeschaeftsanfall);
        }

        berichtsmonat.getBesondereErhebung().add(besondereErhebung);

        final TypeBESTATVerfahrenserhebung typeBESTATVerfahrenserhebung = new TypeBESTATVerfahrenserhebung();
        typeBESTATVerfahrenserhebung.setVerfahrenID(UUID.randomUUID().toString());

        final TypeGDSAktenzeichen typeGDSAktenzeichen = new TypeGDSAktenzeichen();
        final TypeGDSAktenzeichen.AuswahlAktenzeichen auswahlAktenzeichen = new TypeGDSAktenzeichen.AuswahlAktenzeichen();
        auswahlAktenzeichen.setAktenzeichenFreitext("freitext");
        typeGDSAktenzeichen.setAuswahlAktenzeichen(auswahlAktenzeichen);
        typeBESTATVerfahrenserhebung.setAktenzeichen(typeGDSAktenzeichen);

        typeBESTATVerfahrenserhebung.setSchluesselzahlErhebungseinheit("schluesselzahlErhebungseinheit");
        typeBESTATVerfahrenserhebung.setLfdNrVe("lfdNrVe");
        typeBESTATVerfahrenserhebung.set002TagDesEingangsDerSache(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        typeBESTATVerfahrenserhebung.set003AbgabeInnerhalb(true);

        final CodeBESTATEinleitungBetreuung codeBESTATEinleitungBetreuung = new CodeBESTATEinleitungBetreuung();
        codeBESTATEinleitungBetreuung.setCode("1");
        codeBESTATEinleitungBetreuung.setListVersionID("2.0");
        typeBESTATVerfahrenserhebung.setEinleitungDesVerfahrens(codeBESTATEinleitungBetreuung);

        final TypeBESTATVerfahrenserhebung.PersonBetroffener personBetroffener = new TypeBESTATVerfahrenserhebung.PersonBetroffener();
        personBetroffener.set006Geburtsjahr("2000");
        CodeGDSGeschlecht codeGDSGeschlecht = new CodeGDSGeschlecht();
        codeGDSGeschlecht.setCode("1");
        codeGDSGeschlecht.setListVersionID("2.1");
        personBetroffener.set005Geschlecht(codeGDSGeschlecht);
        typeBESTATVerfahrenserhebung.setPersonBetroffener(personBetroffener);

        final TypeBESTATVerfahrenserhebung.EntscheidungEinrichtung entscheidungEinrichtung = new TypeBESTATVerfahrenserhebung.EntscheidungEinrichtung();
        CodeBESTATEntscheidungUeberEinrichtungDerBetreuung codeBESTATEntscheidungUeberEinrichtungDerBetreuung = new CodeBESTATEntscheidungUeberEinrichtungDerBetreuung();
        codeBESTATEntscheidungUeberEinrichtungDerBetreuung.setCode("1");
        codeBESTATEntscheidungUeberEinrichtungDerBetreuung.setListVersionID("2.0");
        entscheidungEinrichtung.setArtEntscheidungEinrichtung(codeBESTATEntscheidungUeberEinrichtungDerBetreuung);
        entscheidungEinrichtung.setDatumEntscheidung(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        typeBESTATVerfahrenserhebung.getEntscheidungEinrichtung().add(entscheidungEinrichtung);

        final TypeBESTATVerfahrenserhebung.ArtBetreuer artBetreuer = new TypeBESTATVerfahrenserhebung.ArtBetreuer();
        final CodeBESTATBetreuerauswahl codeBESTATBetreuerauswahl = new CodeBESTATBetreuerauswahl();
        codeBESTATBetreuerauswahl.setCode("1");
        codeBESTATBetreuerauswahl.setListVersionID("2.0");
        artBetreuer.setBetreuungDurch(codeBESTATBetreuerauswahl);
        artBetreuer.setVorlaeufig(true);
        artBetreuer.setZahl(BigInteger.ONE);
        for (int i = 0; i < 16; i++) {
            typeBESTATVerfahrenserhebung.getArtBetreuer().add(artBetreuer);
        }

        berichtsmonat.getVerfahrenserhebung().add(typeBESTATVerfahrenserhebung);

        final TypeBESTATVerfahrenserhebung.VeraenderungImBerichtszeitraum veraenderungImBerichtszeitraum = new TypeBESTATVerfahrenserhebung.VeraenderungImBerichtszeitraum();
        veraenderungImBerichtszeitraum.setVorlaeufig(true);
        final CodeBESTATVeraenderung codeBESTATVeraenderung = new CodeBESTATVeraenderung();
        codeBESTATVeraenderung.setCode("1");
        codeBESTATVeraenderung.setListVersionID("2.0");
        veraenderungImBerichtszeitraum.setArtVeraenderung(codeBESTATVeraenderung);
        typeBESTATVerfahrenserhebung.getVeraenderungImBerichtszeitraum().add(veraenderungImBerichtszeitraum);

        final TypeBESTATVerfahrenserhebung.EntscheidungImLaufendenVerfahren entscheidungImLaufendenVerfahren = new TypeBESTATVerfahrenserhebung.EntscheidungImLaufendenVerfahren();
        CodeBESTATEntscheidungImLaufendenVerfahren codeBESTATEntscheidungImLaufendenVerfahren = new CodeBESTATEntscheidungImLaufendenVerfahren();
        codeBESTATEntscheidungImLaufendenVerfahren.setCode("1");
        codeBESTATEntscheidungImLaufendenVerfahren.setListVersionID("2.0");
        entscheidungImLaufendenVerfahren.setArtEntscheidungLaufend(codeBESTATEntscheidungImLaufendenVerfahren);
        entscheidungImLaufendenVerfahren.setVorlaeufig(true);
        typeBESTATVerfahrenserhebung.getEntscheidungImLaufendenVerfahren().add(entscheidungImLaufendenVerfahren);

        typeBESTATVerfahrenserhebung.set007AnzahlSachverstaendigengutachten(BigInteger.ONE);

        final TypeBESTATVerfahrenserhebung.AnordnungEinwilligungsvorbehalt anordnungEinwilligungsvorbehalt = new TypeBESTATVerfahrenserhebung.AnordnungEinwilligungsvorbehalt();
        anordnungEinwilligungsvorbehalt.setAnzahlOrd(BigInteger.ONE);
        anordnungEinwilligungsvorbehalt.setAnzahlVorl(BigInteger.ONE);
        typeBESTATVerfahrenserhebung.setAnordnungEinwilligungsvorbehalt(anordnungEinwilligungsvorbehalt);

        final TypeBESTATVerfahrenserhebung.Genehmigungen genehmigungen = new TypeBESTATVerfahrenserhebung.Genehmigungen();
        final CodeBESTATArtGenehmigung codeBESTATArtGenehmigung = new CodeBESTATArtGenehmigung();
        codeBESTATArtGenehmigung.setCode("1");
        codeBESTATArtGenehmigung.setListVersionID("2.0");
        genehmigungen.setArtGenehmigung(codeBESTATArtGenehmigung);
        genehmigungen.setZahl(BigInteger.ONE);
        genehmigungen.setVorlaeufig(true);
        for (int i = 0; i < 60; i++) {
            typeBESTATVerfahrenserhebung.getGenehmigungen().add(genehmigungen);
        }

        typeBESTATVerfahrenserhebung.set008AnzahlEhrenamtlicheVerfahrenspfleger(BigInteger.ONE);
        typeBESTATVerfahrenserhebung.set009AnzahlBerufsmaessigeVerfahrenspfleger(BigInteger.ONE);
        final TypeBESTATVerfahrenserhebung.Beendigung beendigung = new TypeBESTATVerfahrenserhebung.Beendigung();
        beendigung.setVorlaeufig(true);
        beendigung.setDatumBeendigung(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        final CodeBESTATBeendigungBetreuung codeBESTATBeendigungBetreuungen = new CodeBESTATBeendigungBetreuung();
        codeBESTATBeendigungBetreuungen.setCode("1");
        codeBESTATBeendigungBetreuungen.setListVersionID("2.0");
        beendigung.setArtBeendigung(codeBESTATBeendigungBetreuungen);
        typeBESTATVerfahrenserhebung.getBeendigung().add(beendigung);

        typeBESTATVerfahrenserhebung.set010Mittellosigkeit(true);
        typeBESTATVerfahrenserhebung.set011AufenthaltImHeim(true);

        final TypeBESTATVerfahrenserhebung.Zahlungen zahlungen = new TypeBESTATVerfahrenserhebung.Zahlungen();
        final CodeBESTATArtZahlung codeBESTATArtZahlung = new CodeBESTATArtZahlung();
        codeBESTATArtZahlung.setCode("1");
        codeBESTATArtZahlung.setListVersionID("2.0");
        zahlungen.setArtZahlung(codeBESTATArtZahlung);
        final TypeGDSGeldbetrag typeGDSGeldbetrag = new TypeGDSGeldbetrag();
        typeGDSGeldbetrag.setZahl(100);
        final TypeGDSGeldbetrag.AuswahlWaehrung auswahlWaehrung = new TypeGDSGeldbetrag.AuswahlWaehrung();
        final CodeGDSWaehrungTyp3 codeGDSWaehrungTyp3 = new CodeGDSWaehrungTyp3();
        codeGDSWaehrungTyp3.setCode("1");
        codeGDSWaehrungTyp3.setListVersionID("2.0");
        auswahlWaehrung.setWaehrung(codeGDSWaehrungTyp3);
        typeGDSGeldbetrag.setAuswahlWaehrung(auswahlWaehrung);
        zahlungen.setBetragZahlung(typeGDSGeldbetrag);
        typeBESTATVerfahrenserhebung.setZahlungen(zahlungen);

        typeBESTATVerfahrenserhebung.set017TagWeglegung(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));

        fachdaten.setBerichtsmonat(berichtsmonat);

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
