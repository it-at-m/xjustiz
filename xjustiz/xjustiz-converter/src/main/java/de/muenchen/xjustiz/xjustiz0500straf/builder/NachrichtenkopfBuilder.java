package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xjustiz0500straf.config.NachrichtenProperty;
import de.muenchen.xjustiz.xjustiz0500straf.content.NachrichtenkopfContent;
import de.muenchen.xjustiz.xoev.XJustizProperty;
import de.muenchen.xjustiz.xoev.codelisten.XoevCodeGDS;
import de.muenchen.xjustiz.xoev.codelisten.XoevCodeGDSEreignisTyp3;
import de.muenchen.xjustiz.xoev.codelisten.XoevCodeGDSGerichteTyp3;
import java.util.Calendar;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NachrichtenkopfBuilder extends Builder {

    @Value("${xjustiz.version}")
    protected String xJustizVersion;



    public NachrichtenkopfBuilder(XJustizProperty xjustizProperty, NachrichtenProperty nachrichtenProperty, NachrichtenkopfContent nachrichtenkopfContent) {
        super(xjustizProperty, nachrichtenProperty);
    }

    public TypeGDSNachrichtenkopf build(NachrichtenkopfContent nachrichtenkopfContent) {

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

        TypeGDSNachrichtenkopf.AuswahlAbsender absender = new TypeGDSNachrichtenkopf.AuswahlAbsender();
        nachrichtenkopf.getAktenzeichenAbsenders().add(nachrichtenkopfContent.getAktenzeichen());
        absender.setAbsenderSonstige(nachrichtenProperty.getNachrichtenkopf().getAuswahlAbsenderSonstige());
        nachrichtenkopf.setAuswahlAbsender(absender);

        nachrichtenkopf.getAktenzeichenEmpfaengers().add("neu");
        TypeGDSNachrichtenkopf.AuswahlEmpfaenger empfaenger = new TypeGDSNachrichtenkopf.AuswahlEmpfaenger();
        empfaenger.setEmpfaengerGericht((CodeGDSGerichteTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3, XoevCodeGDSGerichteTyp3.AMTSGERICHT_MUENCHEN.getDescriptor()));
        nachrichtenkopf.setAuswahlEmpfaenger(empfaenger);

        nachrichtenkopf.setErstellungszeitpunkt(Calendar.getInstance());
        nachrichtenkopf.setEigeneNachrichtenID(UUID.randomUUID().toString());

        nachrichtenkopf.getEreignises()
                .add((CodeGDSEreignisTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_EREIGNIS_TYP_3, XoevCodeGDSEreignisTyp3.NEUEINGANG_E_HAFT.getDescriptor()));

        TypeGDSHerstellerinformation herstellerinformation = new TypeGDSHerstellerinformation();
        herstellerinformation.setNameDesProdukts(nachrichtenProperty.getNachrichtenkopf().getAuswahlHerstellerinformationProduktName());
        herstellerinformation.setHerstellerDesProdukts(nachrichtenProperty.getNachrichtenkopf().getAuswahlHerstellerinformationProdukt());
        herstellerinformation.setVersion(nachrichtenProperty.getNachrichtenkopf().getAuswahlHerstellerinformationProduktVersion());
        nachrichtenkopf.setHerstellerinformation(herstellerinformation);

        return nachrichtenkopf;

    }

}
