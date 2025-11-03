package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xjustiz0500straf.config.NachrichtenProperty;
import de.muenchen.xjustiz.xjustiz0500straf.content.FachdatenContent;
import de.muenchen.xjustiz.xoev.XJustizProperty;
import de.muenchen.xjustiz.xoev.codelisten.XoevCodeGDS;
import de.muenchen.xjustiz.xoev.codelisten.XoevCodeGDSAnschriftstypen;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class FachdatenBuilder extends Builder {

    public FachdatenBuilder(XJustizProperty xjustizProperty, NachrichtenProperty nachrichtenProperty) {
        super(xjustizProperty, nachrichtenProperty);
    }

    public NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.Fachdaten build(FachdatenContent fachdatenContent) {

        /**
         * Fachdaten
         */
        NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.Fachdaten fachdaten = new NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.Fachdaten();

        TypeSTRAFOWIBussgeldbescheid bussgeldbescheid = new TypeSTRAFOWIBussgeldbescheid();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        bussgeldbescheid.setErlassdatum(fachdatenContent.getErlassdatum().format(dateFormatter));
        TypeSTRAFRechtskraft rechtskraft = new TypeSTRAFRechtskraft();
        rechtskraft.setRechtskraftdatum(fachdatenContent.getRechtskraftdatum());
        bussgeldbescheid.setRechtskraft(rechtskraft);

        bussgeldbescheid.setGeldbusse(fachdatenContent.getGeldbusse());
        bussgeldbescheid.setAuslagen(fachdatenContent.getAuslagen());

        TypeSTRAFOWITat tat = new TypeSTRAFOWITat();


        tat.setAnfangsdatum(fachdatenContent.getAnfangsDatumUhrzeit().format(dateFormatter));
        tat.setEndedatum(fachdatenContent.getEndeDatumUhrzeit().format(dateFormatter));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        tat.setAnfangsuhrzeit(fachdatenContent.getAnfangsDatumUhrzeit().format(timeFormatter));
        tat.setEndeuhrzeit(fachdatenContent.getEndeDatumUhrzeit().format(timeFormatter));

        fachdatenContent.getTatorte().forEach(t -> {
            TypeSTRAFTatort tatort = new TypeSTRAFTatort();
            tatort.setOrtsbeschreibung(t.getOrtsbeschreibung());

            t.getStrasseHausnummer().forEach(sh -> {

                TypeSTRAFTatort.Anschrift anschrift = new TypeSTRAFTatort.Anschrift();
                anschrift.setAnschriftstyp((CodeGDSAnschriftstyp) createCodeGDSClass(XoevCodeGDS.CODE_GDS_ANSCHRIFTSTYP,
                        XoevCodeGDSAnschriftstypen.TATORTANSCHRIFT.getDescriptor()));

                anschrift.setStrasse(sh.getStrasse());
                anschrift.setHausnummer(sh.getHausnummer());
                anschrift.setOrt(t.getOrt());

                tatort.getAnschrifts().add(anschrift);
            });
            tat.getTatorts().add(tatort);
        });

        bussgeldbescheid.setTat(tat);
        fachdaten.setBussgeldbescheid(bussgeldbescheid);

        return fachdaten;
    }

}
