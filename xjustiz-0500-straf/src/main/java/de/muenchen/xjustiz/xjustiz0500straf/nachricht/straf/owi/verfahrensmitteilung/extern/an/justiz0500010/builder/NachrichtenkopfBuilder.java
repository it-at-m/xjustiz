package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder;

import de.muenchen.xjustiz.generated.xjustiz0500straf36.CodeGDSEreignisTyp3;
import de.muenchen.xjustiz.generated.xjustiz0500straf36.CodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.xjustiz0500straf36.TypeGDSHerstellerinformation;
import de.muenchen.xjustiz.generated.xjustiz0500straf36.TypeGDSKommunikationspartner;
import de.muenchen.xjustiz.generated.xjustiz0500straf36.TypeGDSNachrichtenkopf;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.config.NachrichtenProperty;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.NachrichtenkopfContent;
import de.muenchen.xjustizlib.xoev.XJustizProperty;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDS;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDSEreignisTyp3;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDSGerichteTyp3;
import java.util.GregorianCalendar;
import java.util.UUID;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NachrichtenkopfBuilder extends Builder {

    @Value("${xjustiz.version}")
    protected String xJustizVersion;

    public NachrichtenkopfBuilder(final XJustizProperty xjustizProperty, final NachrichtenProperty nachrichtenProperty) {
        super(xjustizProperty, nachrichtenProperty);
    }

    public TypeGDSNachrichtenkopf build(final NachrichtenkopfContent nachrichtenkopfContent) {

        /**
         *
         * Nachrichtenkopf
         * - Aktenzeichen
         * - AuswahlAktenkennzeichen
         * - Erstellzeitpunkt
         * - AuswahlAbsender
         * - AuswahlEmpfaenger
         * - Ereignis
         * - Herstellerinformation
         *
         */

        final TypeGDSNachrichtenkopf nachrichtenkopf = new TypeGDSNachrichtenkopf();

        nachrichtenkopf.setXjustizVersion(xJustizVersion);

        final TypeGDSNachrichtenkopf.Absender absender = new TypeGDSNachrichtenkopf.Absender();
        absender.setAktenzeichen(nachrichtenkopfContent.getAktenzeichen());
        nachrichtenkopf.setAbsender(absender);

        final TypeGDSNachrichtenkopf.Empfaenger empfaenger = new TypeGDSNachrichtenkopf.Empfaenger();

        TypeGDSNachrichtenkopf.Empfaenger.AuswahlAktenzeichen auswahlAktenzeichen = new TypeGDSNachrichtenkopf.Empfaenger.AuswahlAktenzeichen();
        auswahlAktenzeichen.setAktenzeichenNeu(true);
        empfaenger.setAuswahlAktenzeichen(auswahlAktenzeichen);
        nachrichtenkopf.setEmpfaenger(empfaenger);

        try {
            nachrichtenkopf.setErstellungszeitpunkt(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }

        TypeGDSKommunikationspartner.AuswahlKommunikationspartner kommunikationspartnerSka = new TypeGDSKommunikationspartner.AuswahlKommunikationspartner();
        kommunikationspartnerSka.setSonstige(nachrichtenProperty.getNachrichtenkopf().getAuswahlAbsenderSonstige());
        TypeGDSKommunikationspartner kommunikationsPartnerAbsender = new TypeGDSKommunikationspartner();
        kommunikationsPartnerAbsender.setAuswahlKommunikationspartner(kommunikationspartnerSka);
        nachrichtenkopf.getAbsender().setInformationen(kommunikationsPartnerAbsender);

        TypeGDSKommunikationspartner.AuswahlKommunikationspartner kommunikationspartnerGericht = new TypeGDSKommunikationspartner.AuswahlKommunikationspartner();
        CodeGDSGerichteTyp3 gerichtKommunikationsparter = new CodeGDSGerichteTyp3();
        kommunikationspartnerGericht.setGericht(
                (CodeGDSGerichteTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3, XoevCodeGDSGerichteTyp3.AMTSGERICHT_MUENCHEN.getDescriptor()));
        TypeGDSKommunikationspartner kommunikationsPartnerEmpfaenger = new TypeGDSKommunikationspartner();
        kommunikationsPartnerEmpfaenger.setAuswahlKommunikationspartner(kommunikationspartnerGericht);
        nachrichtenkopf.getEmpfaenger().setInformationen(kommunikationsPartnerEmpfaenger);

        nachrichtenkopf.getAbsender().setEigeneNachrichtenID(UUID.randomUUID().toString());
        nachrichtenkopf.getEreignis()
                .add((CodeGDSEreignisTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_EREIGNIS_TYP_3, XoevCodeGDSEreignisTyp3.NEUEINGANG_E_HAFT.getDescriptor()));

        TypeGDSHerstellerinformation herstellerinformation = new TypeGDSHerstellerinformation();
        herstellerinformation.setNameDesProdukts(nachrichtenProperty.getNachrichtenkopf().getAuswahlHerstellerinformationProduktName());
        herstellerinformation.setHerstellerDesProdukts(nachrichtenProperty.getNachrichtenkopf().getAuswahlHerstellerinformationProdukt());
        herstellerinformation.setVersion(nachrichtenProperty.getNachrichtenkopf().getAuswahlHerstellerinformationProduktVersion());
        nachrichtenkopf.setHerstellerinformation(herstellerinformation);

        return nachrichtenkopf;

    }

}
