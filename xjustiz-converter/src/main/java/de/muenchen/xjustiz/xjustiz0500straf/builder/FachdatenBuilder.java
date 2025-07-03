package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xjustiz0500straf.content.FachdatenContent;
import de.muenchen.xjustiz.xjustiz0500straf.content.fachdaten.StrasseHausnummer;
import de.muenchen.xjustiz.xjustiz0500straf.content.fachdaten.Tatort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FachdatenBuilder {


    public NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.Fachdaten build() {


        FachdatenContent fachdatenContent = new FachdatenContent();
        fachdatenContent.setAnfangsDatumUhrzeit(LocalDateTime.of(2024, 10, 1, 12, 0));
        fachdatenContent.setEndeDatumUhrzeit(LocalDateTime.of(2024, 10, 1, 13, 5));
        Tatort tatortContent = new Tatort();
        tatortContent.setAnschriftsTyp("006");
        tatortContent.getStrasseHausnummer().add(new StrasseHausnummer("KVU EH-TATSTR1", "KVU EH-TATHNR1"));
        tatortContent.getStrasseHausnummer().add(new StrasseHausnummer("KVU EH-TATSTR2", "KVU EH-TATHNR2"));
        tatortContent.setOrt("KVU EH-TATORT");
        tatortContent.setOrtsbeschreibung("KVU ???");

        fachdatenContent.getTatorte().add(tatortContent);


        /**
         *  Fachdaten
         */
        NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.Fachdaten fachdaten = new NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.Fachdaten();

        TypeSTRAFOWIBussgeldbescheid bussgeldbescheid = new TypeSTRAFOWIBussgeldbescheid();

        TypeSTRAFOWITat tat = new TypeSTRAFOWITat();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        tat.setAnfangsdatum(fachdatenContent.getAnfangsDatumUhrzeit().format(dateFormatter));
        tat.setEndedatum(fachdatenContent.getEndeDatumUhrzeit().format(dateFormatter));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        tat.setAnfangsuhrzeit(fachdatenContent.getAnfangsDatumUhrzeit().format(timeFormatter));
        tat.setEndeuhrzeit(fachdatenContent.getEndeDatumUhrzeit().format(timeFormatter));

        fachdatenContent.getTatorte().forEach(t-> {
            TypeSTRAFTatort tatort = new TypeSTRAFTatort();
            tatort.setOrtsbeschreibung(t.getOrtsbeschreibung());

            t.getStrasseHausnummer().forEach( sh -> {

                TypeSTRAFTatort.Anschrift anschrift = new TypeSTRAFTatort.Anschrift();
                CodeGDSAnschriftstyp anschriftTyp = new CodeGDSAnschriftstyp();
                anschriftTyp.setCode(t.getAnschriftsTyp());
                anschriftTyp.setListVersionID("3.0");
                anschrift.setAnschriftstyp(anschriftTyp);

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
