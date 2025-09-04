package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xjustiz0500straf.content.SchriftgutContent;
import de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte.Akte;
import de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte.Dokument;
import de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte.Identifikation;
import de.muenchen.xjustiz.xoev.XJustizProperty;
import de.muenchen.xjustiz.xoev.codelisten.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SchriftgutobjektBuilder extends XJustizBuilder {

    public SchriftgutobjektBuilder(XJustizProperty xjustizProperty) {
        super(xjustizProperty);
    }

    public TypeGDSSchriftgutobjekte build(SchriftgutContent schriftgutContent) {

        TypeGDSSchriftgutobjekte schriftobjekte = new TypeGDSSchriftgutobjekte();

        schriftgutContent.getAnschreiben().ifPresent(refsgo -> {
            TypeGDSRefSGO anschreiben = new TypeGDSRefSGO();
            anschreiben.setRefSgo(refsgo);
            schriftobjekte.setAnschreiben(anschreiben);
        });

        schriftgutContent.getDokumente().ifPresent(contentDokumente -> {
            createDocuments(contentDokumente).forEach(d -> schriftobjekte.getDokuments().add(d));
        });

        schriftgutContent.getAkten().ifPresent(contentAkten -> {
            createDossier(contentAkten).forEach(a -> schriftobjekte.getAktes().add(a));
        });

        return schriftobjekte;
    }

    private List<TypeGDSDokument> createDocuments(List<Dokument> contentDokumente) {

        List<TypeGDSDokument> documents = new ArrayList<>();

        contentDokumente.forEach(contentDokument -> {

            TypeGDSDokument document = new TypeGDSDokument();

            document.setIdentifikation(createIdentifikation(contentDokument.getIdentifikation()));

            TypeGDSDokument.XjustizFachspezifischeDaten fachspezifischeDaten = new TypeGDSDokument.XjustizFachspezifischeDaten();

            fachspezifischeDaten.setDokumentklasse((CodeGDSDokumentklasseTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_DOKUMENTKLASSE,
                    contentDokument.getFachspezifischeDatenDokument().getDokumentklasse().getDescriptor()));
            fachspezifischeDaten.setAnzeigename(contentDokument.getFachspezifischeDatenDokument().getAnzeigename());

            List<TypeGDSDokument.XjustizFachspezifischeDaten.Datei> dateien = new ArrayList<>();

            contentDokument.getFachspezifischeDatenDokument().getDateien().forEach(d -> {
                TypeGDSDokument.XjustizFachspezifischeDaten.Datei datei = new TypeGDSDokument.XjustizFachspezifischeDaten.Datei();
                datei.setDateiname(d.getDateiname());

                if (contentDokument.getFachspezifischeDatenDokument().getDokumentklasse() == XoevCodeGDSDokumentklasse.ANTRAG)
                    datei.setBestandteil((CodeGDSBestandteiltyp) createCodeGDSClass(XoevCodeGDS.CODE_GDS_BESTANDTEILTYP,
                            XoevCodeGDSBestandteiltyp.ORIGINAL.getDescriptor()));
                else
                    datei.setBestandteil((CodeGDSBestandteiltyp) createCodeGDSClass(XoevCodeGDS.CODE_GDS_BESTANDTEILTYP,
                            XoevCodeGDSBestandteiltyp.REPRAESENTANT.getDescriptor()));

                datei.setVersionsnummer(d.getVersionsnummer());
                dateien.add(datei);
            });
            dateien.forEach(d -> fachspezifischeDaten.getDateis().add(d));

            document.setXjustizFachspezifischeDaten(fachspezifischeDaten);

            documents.add(document);

        });
        return documents;
    }

    private List<TypeGDSAkte> createDossier(List<Akte> contentAkten) {

        List<TypeGDSAkte> dossiers = new ArrayList<>();
        contentAkten.forEach(contentAkte -> {

            TypeGDSAkte dossier = new TypeGDSAkte();
            dossier.setIdentifikation(createIdentifikation(contentAkte.getIdentifikation()));

            TypeGDSXdomeaZeitraumType zeitraum = new TypeGDSXdomeaZeitraumType();
            zeitraum.setBeginn(contentAkte.getLaufzeit().getBeginn());
            zeitraum.setEnde(contentAkte.getLaufzeit().getEnde());
            dossier.setLaufzeit(zeitraum);

            TypeGDSXdomeaAnwendungsspezifischeErweiterungType erweiterung = new TypeGDSXdomeaAnwendungsspezifischeErweiterungType();
            erweiterung.setKennung(contentAkte.getAnwendungspezifischeErweiterung().getKennung());
            erweiterung.setName(contentAkte.getAnwendungspezifischeErweiterung().getName());
            dossier.setAnwendungsspezifischeErweiterung(erweiterung);

            TypeGDSAkte.XjustizFachspezifischeDaten fachspezifischeDaten = new TypeGDSAkte.XjustizFachspezifischeDaten();
            fachspezifischeDaten
                    .setAktentyp((CodeGDSAktentyp) createCodeGDSClass(XoevCodeGDS.CODE_GDS_AKTENTYP, XoevCodeGDSAktentyp.BUSSGELDAKTE.getDescriptor()));

            TypeGDSAktenzeichen aktenzeichen = new TypeGDSAktenzeichen();
            aktenzeichen.setAzArt(
                    (CodeGDSAktenzeichenart) createCodeGDSClass(XoevCodeGDS.CODE_GDS_AKTENZEICHENART, XoevCodeGDSAktenzeichenart.AKTUELL.getDescriptor()));

            TypeGDSBehoerde behoerde = new TypeGDSBehoerde();
            behoerde.setGericht((CodeGDSGerichteTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3,
                    XoevCodeGDSGerichteTyp3.AMTSGERICHT_MUENCHEN.getDescriptor()));
            aktenzeichen.setAuswahlAzVergebendeStation(behoerde);

            TypeGDSAktenzeichen.AuswahlAktenzeichen auswahlAktenzeichen = new TypeGDSAktenzeichen.AuswahlAktenzeichen();
            TypeGDSAktenzeichen.AuswahlAktenzeichen.AktenzeichenStrukturiert strukturiert = new TypeGDSAktenzeichen.AuswahlAktenzeichen.AktenzeichenStrukturiert();
            strukturiert.setSachgebietsschluessel(
                    contentAkte.getFachspezifischeDatenAkte().getAktenzeichenAuswahlAktenzeichenAktenzeichenStrukturiert().getSachgebietsschluessel());
            strukturiert.setZusatzkennung(
                    contentAkte.getFachspezifischeDatenAkte().getAktenzeichenAuswahlAktenzeichenAktenzeichenStrukturiert().getZusatzkennung());
            strukturiert.setAbteilung(contentAkte.getFachspezifischeDatenAkte().getAktenzeichenAuswahlAktenzeichenAktenzeichenStrukturiert().getAbteilung());
            strukturiert.setLaufendeNummer(
                    contentAkte.getFachspezifischeDatenAkte().getAktenzeichenAuswahlAktenzeichenAktenzeichenStrukturiert().getLaufendeNummer());
            strukturiert.setJahr(contentAkte.getFachspezifischeDatenAkte().getAktenzeichenAuswahlAktenzeichenAktenzeichenStrukturiert().getJahr());
            auswahlAktenzeichen.setAktenzeichenStrukturiert(strukturiert);

            strukturiert.setRegister((CodeGDSRegisterzeichenTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_REGISTERZEICHEN,
                    XoevCodeGDSRegisterzeichen.BUSSGELDVERFAHREN.getDescriptor()));
            auswahlAktenzeichen.setAktenzeichenStrukturiert(strukturiert);
            aktenzeichen.setAuswahlAktenzeichen(auswahlAktenzeichen);

            fachspezifischeDaten.getAktenzeichens().add(aktenzeichen);

            fachspezifischeDaten.setHybridakte(true);
            dossier.setXjustizFachspezifischeDaten(fachspezifischeDaten);

            dossiers.add(dossier);

        });
        return dossiers;
    }

    private static TypeGDSXdomeaIdentifikationObjektType createIdentifikation(Identifikation contentIdentifikation) {
        TypeGDSXdomeaIdentifikationObjektType identifikation = new TypeGDSXdomeaIdentifikationObjektType();
        identifikation.setId(contentIdentifikation.getId());
        identifikation.setNummerImUebergeordnetenContainer(contentIdentifikation.getNummerImUebergeornetenenContainer());
        return identifikation;
    }

}
