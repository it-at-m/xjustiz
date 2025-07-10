package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010;
import de.muenchen.xjustiz.xjustiz0500straf.content.ContentContainer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director {

  private GrunddatenBuilder grunddatenBuilder;
  private SchriftgutobjektBuilder schriftgutobjektBuilder;
  private NachrichtenkopfBuilder nachrichtenkopfBuilder;
  private FachdatenBuilder fachdatenBuilder;

  public NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 build(ContentContainer contentContainer) {

    NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 message0500010 = new NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010();

    message0500010.setNachrichtenkopf(nachrichtenkopfBuilder.build());
    contentContainer.getGrunddatenContent().ifPresentOrElse(g ->message0500010.setGrunddaten(grunddatenBuilder.build(g)), () -> {throw new IllegalArgumentException("Grunddaten expected");});
    contentContainer.getFachdatenContent().ifPresentOrElse(f -> message0500010.setFachdaten(fachdatenBuilder.build(f)), () -> {throw new IllegalArgumentException("Fachdaten expected");});

    // TODO
    message0500010.setSchriftgutobjekte(schriftgutobjektBuilder.build());


    return message0500010;

  }

}
