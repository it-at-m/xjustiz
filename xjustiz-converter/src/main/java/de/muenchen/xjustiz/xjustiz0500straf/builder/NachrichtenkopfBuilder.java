package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.*;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class NachrichtenkopfBuilder {

    public final static String CHANGEIT = "TODO";

    public final static String NACHRICHTENKOPF_AUSWAHL_ABSENDER_SONSTIGE = "SKA 2.222";
    public final static String NACHRICHTENKOPF_AUSWAHL_EMPFAENGER_GERICHT = "D2601";
    public final static String NACHRICHTENKOPF_HERSTELLERINFORMATION_PRODUKT = "KVUweb";


    public TypeGDSNachrichtenkopf build(Calendar uniformMessageTime) {

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

        // xJustizVersion
        nachrichtenkopf.setXjustizVersion("3.6.2");

        // Dynamisch : Aktenzeichen : KVU: "EH-KASSZ" (Kassenzeichen) aus KVU Daten
        TypeGDSNachrichtenkopf.Absender absender = new TypeGDSNachrichtenkopf.Absender();
        absender.setAktenzeichen(CHANGEIT);
        nachrichtenkopf.setAbsender(absender);

        //       Statisch : AuswahlAktenzeichen
        TypeGDSNachrichtenkopf.Empfaenger empfaenger = new TypeGDSNachrichtenkopf.Empfaenger();
        TypeGDSNachrichtenkopf.Empfaenger.AuswahlAktenzeichen auswahlAktenzeichen = new TypeGDSNachrichtenkopf.Empfaenger.AuswahlAktenzeichen();
        auswahlAktenzeichen.setAktenzeichenNeu(true);
        empfaenger.setAuswahlAktenzeichen(auswahlAktenzeichen);
        nachrichtenkopf.setEmpfaenger(empfaenger);

        //Dynamisch : Erstellzeitpunkt
        nachrichtenkopf.setErstellungszeitpunkt(uniformMessageTime);

        // Statisch : Auswahl_Absender
        TypeGDSKommunikationspartner.AuswahlKommunikationspartner kommunikationspartnerSka = new TypeGDSKommunikationspartner.AuswahlKommunikationspartner();
        kommunikationspartnerSka.setSonstige(NACHRICHTENKOPF_AUSWAHL_ABSENDER_SONSTIGE);
        TypeGDSKommunikationspartner kommunikationsPartnerAbsender = new TypeGDSKommunikationspartner();
        kommunikationsPartnerAbsender.setAuswahlKommunikationspartner(kommunikationspartnerSka);
        nachrichtenkopf.getAbsender().setInformationen(kommunikationsPartnerAbsender);

        // Statisch : Auswahl_Empfaenger
        TypeGDSKommunikationspartner.AuswahlKommunikationspartner kommunikationspartnerGericht = new TypeGDSKommunikationspartner.AuswahlKommunikationspartner();
        CodeGDSGerichteTyp3 gerichtKommunikationsparter = new CodeGDSGerichteTyp3();
        gerichtKommunikationsparter.setCode(NACHRICHTENKOPF_AUSWAHL_EMPFAENGER_GERICHT);
        gerichtKommunikationsparter.setListVersionID(CHANGEIT);
        kommunikationspartnerGericht.setGericht(gerichtKommunikationsparter);
        TypeGDSKommunikationspartner kommunikationsPartnerEmpfaenger = new TypeGDSKommunikationspartner();
        kommunikationsPartnerEmpfaenger.setAuswahlKommunikationspartner(kommunikationspartnerGericht);
        nachrichtenkopf.getEmpfaenger().setInformationen(kommunikationsPartnerEmpfaenger);

        /*
           Dynamisch : Eigene_Nachricht_ID
           Hier ist eine eindeutige Identifikation der bei diesem Übermittlungsvorgang erstellten Nachricht anzugeben, um spätere Referenzen zu ermöglichen.
           Von EAI-XML-Generator zu vergebene, fortlaufende ID.
        */
        nachrichtenkopf.getAbsender().setEigeneNachrichtenID("3f9a9e40-0b4a-4d65-8e24-89f9fce51f97");
        CodeGDSEreignisTyp3 ereignis = new CodeGDSEreignisTyp3();

        //  Statisch : Ereignis
        ereignis.setCode(CHANGEIT);
        ereignis.setListVersionID(CHANGEIT);
        nachrichtenkopf.getEreignises().add(ereignis);

        // Statisch : Herstellerinformation
        TypeGDSHerstellerinformation herstellerinformation = new TypeGDSHerstellerinformation();
        herstellerinformation.setNameDesProdukts(NACHRICHTENKOPF_HERSTELLERINFORMATION_PRODUKT);
        herstellerinformation.setHerstellerDesProdukts(CHANGEIT);
        herstellerinformation.setVersion(CHANGEIT);
        nachrichtenkopf.setHerstellerinformation(herstellerinformation);

        return nachrichtenkopf;

    }

}
