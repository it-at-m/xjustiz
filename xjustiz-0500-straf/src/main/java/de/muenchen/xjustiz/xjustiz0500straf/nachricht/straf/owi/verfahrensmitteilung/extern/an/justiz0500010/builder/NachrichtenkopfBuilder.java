package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder;

import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSEreignisTyp3;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSHerstellerinformation;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSNachrichtenkopf;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.config.NachrichtenProperty;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.NachrichtenkopfContent;
import de.muenchen.xjustiz.xoev.XJustizProperty;
import de.muenchen.xjustiz.xoev.codelisten.XoevCodeGDS;
import de.muenchen.xjustiz.xoev.codelisten.XoevCodeGDSEreignisTyp3;
import de.muenchen.xjustiz.xoev.codelisten.XoevCodeGDSGerichteTyp3;
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

        final TypeGDSNachrichtenkopf.AuswahlAbsender absender = new TypeGDSNachrichtenkopf.AuswahlAbsender();
        nachrichtenkopf.getAktenzeichenAbsender().add(nachrichtenkopfContent.getAktenzeichen());
        absender.setAbsenderSonstige(nachrichtenProperty.getNachrichtenkopf().getAuswahlAbsenderSonstige());
        nachrichtenkopf.setAuswahlAbsender(absender);

        nachrichtenkopf.getAktenzeichenEmpfaenger().add("neu");
        final TypeGDSNachrichtenkopf.AuswahlEmpfaenger empfaenger = new TypeGDSNachrichtenkopf.AuswahlEmpfaenger();
        empfaenger.setEmpfaengerGericht(
                (CodeGDSGerichteTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3, XoevCodeGDSGerichteTyp3.AMTSGERICHT_MUENCHEN.getDescriptor()));
        nachrichtenkopf.setAuswahlEmpfaenger(empfaenger);

        try {
            nachrichtenkopf.setErstellungszeitpunkt(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        nachrichtenkopf.setEigeneNachrichtenID(UUID.randomUUID().toString());

        nachrichtenkopf.getEreignis()
                .add((CodeGDSEreignisTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_EREIGNIS_TYP_3, XoevCodeGDSEreignisTyp3.NEUEINGANG_E_HAFT.getDescriptor()));

        final TypeGDSHerstellerinformation herstellerinformation = new TypeGDSHerstellerinformation();
        herstellerinformation.setNameDesProdukts(nachrichtenProperty.getNachrichtenkopf().getAuswahlHerstellerinformationProduktName());
        herstellerinformation.setHerstellerDesProdukts(nachrichtenProperty.getNachrichtenkopf().getAuswahlHerstellerinformationProdukt());
        herstellerinformation.setVersion(nachrichtenProperty.getNachrichtenkopf().getAuswahlHerstellerinformationProduktVersion());
        nachrichtenkopf.setHerstellerinformation(herstellerinformation);

        return nachrichtenkopf;

    }

}
