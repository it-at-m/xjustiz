package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder;

import de.muenchen.xjustiz.generated.xjustiz0500straf36.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.ContentContainer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director {

    private GrunddatenBuilder grunddatenBuilder;
    private SchriftgutobjektBuilder schriftgutobjektBuilder;
    private NachrichtenkopfBuilder nachrichtenkopfBuilder;
    private FachdatenBuilder fachdatenBuilder;

    public NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 build(final ContentContainer contentContainer) {

        final NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 message0500010 = new NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010();

        contentContainer.getNachrichtenkopfContent().ifPresentOrElse(n -> message0500010.setNachrichtenkopf(nachrichtenkopfBuilder.build(n)), () -> {
            throw new IllegalArgumentException("Grunddaten expected");
        });

        contentContainer.getGrunddatenContent().ifPresentOrElse(g -> message0500010.setGrunddaten(grunddatenBuilder.build(g)), () -> {
            throw new IllegalArgumentException("Grunddaten expected");
        });
        contentContainer.getFachdatenContent().ifPresentOrElse(f -> message0500010.setFachdaten(fachdatenBuilder.build(f)), () -> {
            throw new IllegalArgumentException("Fachdaten expected");
        });
        contentContainer.getSchriftgutContent().ifPresentOrElse(s -> message0500010.setSchriftgutobjekte(schriftgutobjektBuilder.build(s)), () -> {
            throw new IllegalArgumentException("Schriftgut expected");
        });

        return message0500010;

    }

}
