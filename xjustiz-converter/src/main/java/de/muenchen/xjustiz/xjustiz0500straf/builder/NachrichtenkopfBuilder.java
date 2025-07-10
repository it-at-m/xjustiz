package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xjustiz0500straf.config.NachrichtenProperty;
import de.muenchen.xjustiz.xjustiz0500straf.content.NachrichtenkopfContent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NachrichtenkopfBuilder {

    @Value("${xjustiz.version}")
    protected String xJustizVersion;

    private final NachrichtenProperty nachrichtenProperty;
    private final NachrichtenkopfContent nachrichtenkopfContent;

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
        gerichtKommunikationsparter.setListVersionID(nachrichtenProperty.getCodelisten().get("gds-gerichte").getCurrentVersion());
        gerichtKommunikationsparter.setCode(nachrichtenProperty.getCodelisten().get("gds-gerichte").currentCodelistValueWithKey("amtsgericht-muenchen"));
        kommunikationspartnerGericht.setGericht(gerichtKommunikationsparter);
        TypeGDSKommunikationspartner kommunikationsPartnerEmpfaenger = new TypeGDSKommunikationspartner();
        kommunikationsPartnerEmpfaenger.setAuswahlKommunikationspartner(kommunikationspartnerGericht);
        nachrichtenkopf.getEmpfaenger().setInformationen(kommunikationsPartnerEmpfaenger);

        nachrichtenkopf.getAbsender().setEigeneNachrichtenID(UUID.randomUUID().toString());
        CodeGDSEreignisTyp3 ereignis = new CodeGDSEreignisTyp3();

        ereignis.setListVersionID(nachrichtenProperty.getCodelisten().get("gds-ereignis").getCurrentVersion());
        ereignis.setCode(nachrichtenProperty.getCodelisten().get("gds-ereignis").currentCodelistValueWithKey("neueingang-e-haft"));

        nachrichtenkopf.getEreignises().add(ereignis);

        TypeGDSHerstellerinformation herstellerinformation = new TypeGDSHerstellerinformation();
        herstellerinformation.setNameDesProdukts(nachrichtenProperty.getNachrichtenkopf().getAuswahlHerstellerinformationProduktName());
        herstellerinformation.setHerstellerDesProdukts(nachrichtenProperty.getNachrichtenkopf().getAuswahlHerstellerinformationProdukt());
        herstellerinformation.setVersion(nachrichtenProperty.getNachrichtenkopf().getAuswahlHerstellerinformationProduktVersion());
        nachrichtenkopf.setHerstellerinformation(herstellerinformation);

        return nachrichtenkopf;

    }

}
