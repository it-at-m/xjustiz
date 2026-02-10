package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder;

import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSAktentyp;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSAktenzeichenart;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSBestandteiltyp;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSDokumentklasseTyp3;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSGerichteTyp3;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSRegisterzeichenTyp3;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSAkte;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSAktenzeichen;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSBehoerde;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSDokument;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSRefSGO;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSSchriftgutobjekte;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSXdomeaAnwendungsspezifischeErweiterungType;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSXdomeaIdentifikationObjektType;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeGDSXdomeaZeitraumType;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.SchriftgutContent;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.schriftgutobjekte.Akte;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.schriftgutobjekte.Dokument;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.schriftgutobjekte.Identifikation;
import de.muenchen.xjustizlib.xoev.XJustizProperty;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDS;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDSAktentyp;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDSAktenzeichenart;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDSBestandteiltyp;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDSDokumentklasse;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDSGerichteTyp3;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDSRegisterzeichen;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SchriftgutobjektBuilder extends XJustizBuilder {

    public SchriftgutobjektBuilder(final XJustizProperty xjustizProperty) {
        super(xjustizProperty);
    }

    public TypeGDSSchriftgutobjekte build(final SchriftgutContent schriftgutContent) {

        final TypeGDSSchriftgutobjekte schriftobjekte = new TypeGDSSchriftgutobjekte();

        schriftgutContent.getAnschreiben().ifPresent(refsgo -> {
            final TypeGDSRefSGO anschreiben = new TypeGDSRefSGO();
            anschreiben.setRefSgo(refsgo);
            schriftobjekte.setAnschreiben(anschreiben);
        });

        schriftgutContent.getDokumente().ifPresent(contentDokumente -> {
            createDocuments(contentDokumente).forEach(d -> schriftobjekte.getDokument().add(d));
        });

        schriftgutContent.getAkten().ifPresent(contentAkten -> {
            createDossier(contentAkten).forEach(a -> schriftobjekte.getAkte().add(a));
        });

        return schriftobjekte;
    }

    private List<TypeGDSDokument> createDocuments(final List<Dokument> contentDokumente) {

        final List<TypeGDSDokument> documents = new ArrayList<>();

        contentDokumente.forEach(contentDokument -> {

            final TypeGDSDokument document = new TypeGDSDokument();

            document.setIdentifikation(createIdentifikation(contentDokument.getIdentifikation()));

            final TypeGDSDokument.XjustizFachspezifischeDaten fachspezifischeDaten = new TypeGDSDokument.XjustizFachspezifischeDaten();

            fachspezifischeDaten.setDokumentklasse((CodeGDSDokumentklasseTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_DOKUMENTKLASSE,
                    contentDokument.getFachspezifischeDatenDokument().getDokumentklasse().getDescriptor()));
            fachspezifischeDaten.setAnzeigename(contentDokument.getFachspezifischeDatenDokument().getAnzeigename());

            final List<TypeGDSDokument.XjustizFachspezifischeDaten.Datei> dateien = new ArrayList<>();

            contentDokument.getFachspezifischeDatenDokument().getDateien().forEach(d -> {
                final TypeGDSDokument.XjustizFachspezifischeDaten.Datei datei = new TypeGDSDokument.XjustizFachspezifischeDaten.Datei();
                datei.setDateiname(d.getDateiname());

                if (contentDokument.getFachspezifischeDatenDokument().getDokumentklasse() == XoevCodeGDSDokumentklasse.ANTRAG) {
                    datei.setBestandteil((CodeGDSBestandteiltyp) createCodeGDSClass(XoevCodeGDS.CODE_GDS_BESTANDTEILTYP,
                            XoevCodeGDSBestandteiltyp.ORIGINAL.getDescriptor()));
                } else {
                    datei.setBestandteil((CodeGDSBestandteiltyp) createCodeGDSClass(XoevCodeGDS.CODE_GDS_BESTANDTEILTYP,
                            XoevCodeGDSBestandteiltyp.REPRAESENTANT.getDescriptor()));
                }

                datei.setVersionsnummer(d.getVersionsnummer());
                dateien.add(datei);
            });
            dateien.forEach(d -> fachspezifischeDaten.getDatei().add(d));

            document.setXjustizFachspezifischeDaten(fachspezifischeDaten);

            documents.add(document);

        });
        return documents;
    }

    private List<TypeGDSAkte> createDossier(final List<Akte> contentAkten) {

        final List<TypeGDSAkte> dossiers = new ArrayList<>();
        contentAkten.forEach(contentAkte -> {

            final TypeGDSAkte dossier = new TypeGDSAkte();

            contentAkte.getIdentifikation().ifPresent(identifikation -> {
                dossier.setIdentifikation(createIdentifikation(identifikation));
            });

            contentAkte.getLaufzeit().ifPresent(laufzeit -> {
                final TypeGDSXdomeaZeitraumType zeitraum = new TypeGDSXdomeaZeitraumType();
                laufzeit.getBeginn().ifPresent(zeitraum::setBeginn);
                laufzeit.getEnde().ifPresent(zeitraum::setEnde);
                dossier.setLaufzeit(zeitraum);
            });

            contentAkte.getAnwendungspezifischeErweiterung().ifPresent(anwendungsspezifischeErweiterung -> {
                final TypeGDSXdomeaAnwendungsspezifischeErweiterungType erweiterung = new TypeGDSXdomeaAnwendungsspezifischeErweiterungType();
                erweiterung.setKennung(anwendungsspezifischeErweiterung.getKennung());
                erweiterung.setName(anwendungsspezifischeErweiterung.getName());
                dossier.setAnwendungsspezifischeErweiterung(erweiterung);
            });

            final TypeGDSAkte.XjustizFachspezifischeDaten fachspezifischeDaten = new TypeGDSAkte.XjustizFachspezifischeDaten();
            fachspezifischeDaten
                    .setAktentyp((CodeGDSAktentyp) createCodeGDSClass(XoevCodeGDS.CODE_GDS_AKTENTYP, XoevCodeGDSAktentyp.BUSSGELDAKTE.getDescriptor()));

            final TypeGDSAktenzeichen aktenzeichen = new TypeGDSAktenzeichen();

            final TypeGDSBehoerde behoerde = new TypeGDSBehoerde();
            behoerde.setGericht((CodeGDSGerichteTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_GERICHTE_TYP_3,
                    XoevCodeGDSGerichteTyp3.AMTSGERICHT_MUENCHEN.getDescriptor()));
            aktenzeichen.setAuswahlAzVergebendeStation(behoerde);

            fachspezifischeDaten.getAktenzeichen().add(aktenzeichen);

            contentAkte.getFachspezifischeDatenAkte().ifPresent(fachspezifischeDatenAkte -> {

                if (fachspezifischeDatenAkte.isAktenzeichenArt()) {
                    aktenzeichen.setAzArt((CodeGDSAktenzeichenart) createCodeGDSClass(XoevCodeGDS.CODE_GDS_AKTENZEICHENART,
                            XoevCodeGDSAktenzeichenart.AKTUELL.getDescriptor()));
                }

                final TypeGDSAktenzeichen.AuswahlAktenzeichen auswahlAktenzeichen = new TypeGDSAktenzeichen.AuswahlAktenzeichen();
                fachspezifischeDatenAkte.getFreitext().ifPresent(freitext -> {
                    auswahlAktenzeichen.setAktenzeichenFreitext(freitext);
                });

                fachspezifischeDatenAkte.getAktenzeichenAuswahlAktenzeichenAktenzeichenStrukturiert().ifPresent(auswahlAktenzeichenStrukturiert -> {
                    final TypeGDSAktenzeichen.AuswahlAktenzeichen.AktenzeichenStrukturiert strukturiert = new TypeGDSAktenzeichen.AuswahlAktenzeichen.AktenzeichenStrukturiert();
                    strukturiert.setSachgebietsschluessel(
                            auswahlAktenzeichenStrukturiert.getSachgebietsschluessel());
                    strukturiert.setZusatzkennung(
                            auswahlAktenzeichenStrukturiert.getZusatzkennung());
                    strukturiert.setAbteilung(auswahlAktenzeichenStrukturiert.getAbteilung());
                    strukturiert.setLaufendeNummer(
                            auswahlAktenzeichenStrukturiert.getLaufendeNummer());
                    strukturiert.setJahr(auswahlAktenzeichenStrukturiert.getJahr());

                    auswahlAktenzeichen.setAktenzeichenStrukturiert(strukturiert);

                    strukturiert.setRegister((CodeGDSRegisterzeichenTyp3) createCodeGDSClass(XoevCodeGDS.CODE_GDS_REGISTERZEICHEN,
                            XoevCodeGDSRegisterzeichen.BUSSGELDVERFAHREN.getDescriptor()));
                    auswahlAktenzeichen.setAktenzeichenStrukturiert(strukturiert);

                    aktenzeichen.setAuswahlAktenzeichen(auswahlAktenzeichen);
                });

                aktenzeichen.setAuswahlAktenzeichen(auswahlAktenzeichen);

            });

            dossier.setXjustizFachspezifischeDaten(fachspezifischeDaten);
            dossiers.add(dossier);

        });
        return dossiers;
    }

    private static TypeGDSXdomeaIdentifikationObjektType createIdentifikation(final Identifikation contentIdentifikation) {
        final TypeGDSXdomeaIdentifikationObjektType identifikation = new TypeGDSXdomeaIdentifikationObjektType();
        identifikation.setId(contentIdentifikation.getId());
        identifikation.setNummerImUebergeordnetenContainer(contentIdentifikation.getNummerImUebergeordnetenContainer());
        return identifikation;
    }

}
