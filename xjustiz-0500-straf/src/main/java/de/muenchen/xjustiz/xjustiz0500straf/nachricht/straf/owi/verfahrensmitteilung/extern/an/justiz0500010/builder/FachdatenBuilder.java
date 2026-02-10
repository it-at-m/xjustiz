package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder;

import de.muenchen.xjustiz.generated.xjustiz0500straf35.CodeGDSAnschriftstyp;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeSTRAFOWIBussgeldbescheid;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeSTRAFOWITat;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeSTRAFRechtskraft;
import de.muenchen.xjustiz.generated.xjustiz0500straf35.TypeSTRAFTatort;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.config.NachrichtenProperty;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.FachdatenContent;
import de.muenchen.xjustizlib.xoev.XJustizProperty;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDS;
import de.muenchen.xjustizlib.xoev.codelisten.XoevCodeGDSAnschriftstypen;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class FachdatenBuilder extends Builder {

    public FachdatenBuilder(final XJustizProperty xjustizProperty, final NachrichtenProperty nachrichtenProperty) {
        super(xjustizProperty, nachrichtenProperty);
    }

    public NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.Fachdaten build(
            final FachdatenContent fachdatenContent) {

        /**
         * Fachdaten
         */
        final NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.Fachdaten fachdaten = new NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.Fachdaten();

        final TypeSTRAFOWIBussgeldbescheid bussgeldbescheid = new TypeSTRAFOWIBussgeldbescheid();

        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        fachdatenContent.getErlassdatum().ifPresent(date -> bussgeldbescheid.setErlassdatum(date.format(dateFormatter)));

        final TypeSTRAFRechtskraft rechtskraft = new TypeSTRAFRechtskraft();
        rechtskraft.setRechtskraftdatum(fachdatenContent.getRechtskraftdatum());
        bussgeldbescheid.setRechtskraft(rechtskraft);

        bussgeldbescheid.setGeldbusse(fachdatenContent.getGeldbusse());
        bussgeldbescheid.setAuslagen(fachdatenContent.getAuslagen());

        final TypeSTRAFOWITat tat = new TypeSTRAFOWITat();

        fachdatenContent.getAnfangDatum().ifPresent(date -> tat.setAnfangsdatum(date));
        fachdatenContent.getAnfangUhrzeit().ifPresent(time -> tat.setAnfangsuhrzeit(time));
        fachdatenContent.getEndeDatum().ifPresent(date -> tat.setEndedatum(date));
        fachdatenContent.getEndeUhrzeit().ifPresent(time -> tat.setEndeuhrzeit(time));

        fachdatenContent.getTatorte().forEach(t -> {
            final TypeSTRAFTatort tatort = new TypeSTRAFTatort();
            tatort.setOrtsbeschreibung(t.getOrtsbeschreibung());

            t.getStrasseHausnummer().forEach(sh -> {

                final TypeSTRAFTatort.Anschrift anschrift = new TypeSTRAFTatort.Anschrift();
                anschrift.setAnschriftstyp((CodeGDSAnschriftstyp) createCodeGDSClass(XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP,
                        XoevCodeGDSAnschriftstypen.TATORTANSCHRIFT.getDescriptor()));

                anschrift.setStrasse(sh.getStrasse());
                anschrift.setHausnummer(sh.getHausnummer());
                anschrift.setOrt(t.getOrt());

                tatort.getAnschrift().add(anschrift);
            });
            tat.getTatort().add(tatort);
        });

        bussgeldbescheid.setTat(tat);
        fachdaten.setBussgeldbescheid(bussgeldbescheid);

        return fachdaten;
    }

}
