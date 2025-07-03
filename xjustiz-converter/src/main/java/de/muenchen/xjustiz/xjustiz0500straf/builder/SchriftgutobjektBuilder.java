package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.*;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class SchriftgutobjektBuilder {

    private final static String SCHRIFTGUTOBJEKTE_DOKUMENT_FACHSPEZIFISCHE_DATEN_DOKUMENTKLASSE= "016";

    public TypeGDSSchriftgutobjekte build() {


        /**
         *  Schriftgutobjekte
         *  !!!!   Kommen aus der eAkte. Müssen erst im 2.ten Schritt nach dem erneuten abholen
         *         aus der eAkte hinzugefügt werden. Erst dann sind die erforderlichen Schriftgut.Attribut
         *         Inhalte verfügbar.
         *         Die Implementierung der Schriftgutobjekt muss getrennt nachrichtenkopf, grunddaten und fachdaten
         *         erfolgen
         */

        TypeGDSSchriftgutobjekte schriftobjekte = new TypeGDSSchriftgutobjekte();

        List<TypeGDSDokument> dokumente = new ArrayList<>();

        TypeGDSDokument dokument = new TypeGDSDokument();

        // Anschreiben
        TypeGDSRefSGO anschreiben = new TypeGDSRefSGO();
        anschreiben.setRefSgo("CEEF2150-F915-1F1F-1177-906D00000000");
        schriftobjekte.setAnschreiben(anschreiben);

        // Identifikation
        TypeGDSXdomeaIdentifikationObjektType identifikation = new TypeGDSXdomeaIdentifikationObjektType();
        /*
            xDOMEA: xdomea:information-information-0101.schriftgutobjekt.vorgang.dokument.identifikation.id
            Bsp. für ID: CEEF2150-F915-1F1F-1177-906D00000000
         */
        identifikation.setId("CEEF2150-F915-1F1F-1177-906D00000000");
        /*
            xDOMEA: xdomea:information-information-0101.schriftgutobjekt.vorgang.dokument.identifikation.nummerImUebergeordnetenContainer
            Bsp. für nummerImUebergeordnetenContainer: 1
         */
        identifikation.setNummerImUebergeordnetenContainer(BigInteger.ONE);
        dokument.setIdentifikation(identifikation);

        // Fachspezifische Daten
        TypeGDSDokument.XjustizFachspezifischeDaten fachspezifischeDaten = new TypeGDSDokument.XjustizFachspezifischeDaten();

        /* Dokumentenklasse

            xDOMEA: xdomea:information-information-0101.schriftgutobjekt.vorgang.dokument.version.format.name.code -> DOCX
            BRE:
            Schriftgutobjekt OKV - Dokumentklasse: "016", Bestandteiltyp: "001" (Original) gemäß FB (Erik Weber)
            Schriftgutobjekt BUS Urschrift - "017", Bestandteiltyp: "002" (Repräsentant) gemäß FB (Erik Weber)
            Siehe Codeliste GDS.Dokumentklasse_1.4.xlsx
         */
        CodeGDSDokumentklasseTyp3 dokumentklasse = new CodeGDSDokumentklasseTyp3();
        dokumentklasse.setCode(SCHRIFTGUTOBJEKTE_DOKUMENT_FACHSPEZIFISCHE_DATEN_DOKUMENTKLASSE);
        dokumentklasse.setListVersionID(NachrichtenkopfBuilder.CHANGEIT);
        fachspezifischeDaten.setDokumentklasse(dokumentklasse);

        /* Anzeigename
            xDOMEA: xdomea:information-information-0101.schriftgutobjekt.vorgang.dokument.version.format.primaerdokument.dateiname
            Bsp. für anzeigename: CEEF2150-F915-1F1F-1177-908D00000000_Neufassung von Micro_1.docx
         */
        fachspezifischeDaten.setAnzeigename("CEEF2150-F915-1F1F-1177-908D00000000_Neufassung von Micro_1.docx");

        // Datei
        List<TypeGDSDokument.XjustizFachspezifischeDaten.Datei> dateien = new ArrayList<>();
        TypeGDSDokument.XjustizFachspezifischeDaten.Datei datei = new TypeGDSDokument.XjustizFachspezifischeDaten.Datei();
        /* Dateiname
              xDOMEA: xdomea:information-information-0101.schriftgutobjekt.vorgang.dokument.version.format.primaerdokument.dateiname
              Bsp. für anzeigename: CEEF2150-F915-1F1F-1177-908D00000000_Neufassung von Micro_1.docx
        */
        datei.setDateiname("CEEF2150-F915-1F1F-1177-908D00000000_Neufassung von Micro_1.docx");

        /* Bestandteil
            TODO BRE: Bitte prüfen ob Code 002 Repräsentant richtig. Übertragung im beBPo muss per .pdf stattfinden, daher Vorschlag 002.
            Codeliste: GDS.Bestandteiltyp_2.4.xlsx
         */
        CodeGDSBestandteiltyp bestandteiltyp = new CodeGDSBestandteiltyp();
        bestandteiltyp.setCode("002");
        datei.setBestandteil(bestandteiltyp);

        /* Versionsnummer
             xDOMEA: xdomea:information-information-0101.schriftgutobjekt.vorgang.dokument.version.nummer
             Bsp. für versionsnummer: 1
         */
        datei.setVersionsnummer(BigInteger.ONE);

        /* Erstellungszeitpunkt    alias   Veraktungsdatum
            xDOMEA: xdomea:information-information-0101.schriftgutobjekt.vorgang.dokument.version.format.primaerdokument.datumuhrzeit
         */
        // fachspezifischeDaten.setVeraktungsdatum(new XMLGregorianCalendar());

        /*
                @TODO Fehlende Attribute ergänzen ...
         */

        dateien.add(datei);

        dateien.forEach(d -> fachspezifischeDaten.getDateis().add(d));

        dokument.setXjustizFachspezifischeDaten(fachspezifischeDaten);
        dokumente.add(dokument);

        dokumente.forEach(d -> schriftobjekte.getDokuments().add(d));

        return schriftobjekte;

    }

}
