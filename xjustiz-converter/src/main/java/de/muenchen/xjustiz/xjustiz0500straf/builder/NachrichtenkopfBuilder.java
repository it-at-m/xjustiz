package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.config.codelisten.XoevCodeGDSEreignisTyp3;
import de.muenchen.xjustiz.config.codelisten.XoevCodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xjustiz0500straf.config.NachrichtenProperty;
import de.muenchen.xjustiz.config.XJustizProperty;
import de.muenchen.xjustiz.xjustiz0500straf.content.NachrichtenkopfContent;
import de.muenchen.xjustiz.config.codelisten.XoevCodeGDS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.UUID;

@Component
public class NachrichtenkopfBuilder extends Builder {

    @Value("${xjustiz.version}")
    protected String xJustizVersion;

    private final NachrichtenkopfContent nachrichtenkopfContent;

    public NachrichtenkopfBuilder(XJustizProperty xjustizProperty, NachrichtenProperty nachrichtenProperty, NachrichtenkopfContent nachrichtenkopfContent) {
        super(xjustizProperty, nachrichtenProperty);
        this.nachrichtenkopfContent = nachrichtenkopfContent;
    }

    public TypeGDSNachrichtenkopf build() {

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

        TypeGDSNachrichtenkopf nachrichtenkopf = new TypeGDSNachrichtenkopf();

        nachrichtenkopf.setXjustizVersion(xJustizVersion);

        TypeGDSNachrichtenkopf.Absender absender = new TypeGDSNachrichtenkopf.Absender();
        absender.setAktenzeichen(nachrichtenkopfContent.getAktenzeichen());
        nachrichtenkopf.setAbsender(absender);

        TypeGDSNachrichtenkopf.Empfaenger empfaenger = new TypeGDSNachrichtenkopf.Empfaenger();
        TypeGDSNachrichtenkopf.Empfaenger.AuswahlAktenzeichen auswahlAktenzeichen = new TypeGDSNachrichtenkopf.Empfaenger.AuswahlAktenzeichen();
        auswahlAktenzeichen.setAktenzeichenNeu(true);
        empfaenger.setAuswahlAktenzeichen(auswahlAktenzeichen);
        nachrichtenkopf.setEmpfaenger(empfaenger);

        nachrichtenkopf.setErstellungszeitpunkt(Calendar.getInstance());

        TypeGDSKommunikationspartner.AuswahlKommunikationspartner kommunikationspartnerSka = new TypeGDSKommunikationspartner.AuswahlKommunikationspartner();
        kommunikationspartnerSka.setSonstige(nachrichtenProperty.getNachrichtenkopf().getAuswahlAbsenderSonstige());
        TypeGDSKommunikationspartner kommunikationsPartnerAbsender = new TypeGDSKommunikationspartner();
        kommunikationsPartnerAbsender.setAuswahlKommunikationspartner(kommunikationspartnerSka);
        nachrichtenkopf.getAbsender().setInformationen(kommunikationsPartnerAbsender);

        TypeGDSKommunikationspartner.AuswahlKommunikationspartner kommunikationspartnerGericht = new TypeGDSKommunikationspartner.AuswahlKommunikationspartner();
        CodeGDSGerichteTyp3 gerichtKommunikationsparter = new CodeGDSGerichteTyp3();
        kommunikationspartnerGericht.setGericht((CodeGDSGerichteTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3, XoevCodeGDSGerichteTyp3.AMTSGERICHT_MUENCHEN.getDescriptor()));
        TypeGDSKommunikationspartner kommunikationsPartnerEmpfaenger = new TypeGDSKommunikationspartner();
        kommunikationsPartnerEmpfaenger.setAuswahlKommunikationspartner(kommunikationspartnerGericht);
        nachrichtenkopf.getEmpfaenger().setInformationen(kommunikationsPartnerEmpfaenger);

        nachrichtenkopf.getAbsender().setEigeneNachrichtenID(UUID.randomUUID().toString());
        nachrichtenkopf.getEreignises().add((CodeGDSEreignisTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_EREIGNIS_TYP_3, XoevCodeGDSEreignisTyp3.NEUEINGANG_E_HAFT.getDescriptor()));

        TypeGDSHerstellerinformation herstellerinformation = new TypeGDSHerstellerinformation();
        herstellerinformation.setNameDesProdukts(nachrichtenProperty.getNachrichtenkopf().getAuswahlHerstellerinformationProduktName());
        herstellerinformation.setHerstellerDesProdukts(nachrichtenProperty.getNachrichtenkopf().getAuswahlHerstellerinformationProdukt());
        herstellerinformation.setVersion(nachrichtenProperty.getNachrichtenkopf().getAuswahlHerstellerinformationProduktVersion());
        nachrichtenkopf.setHerstellerinformation(herstellerinformation);

        return nachrichtenkopf;

    }

}
