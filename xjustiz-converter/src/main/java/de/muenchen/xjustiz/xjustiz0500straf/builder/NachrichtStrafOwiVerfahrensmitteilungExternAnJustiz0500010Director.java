package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010;
import de.muenchen.xjustiz.xjustiz0500straf.content.grunddaten.verfahrensdaten.beteiligung.Beteiligung;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director {

  private GrunddatenBuilder grunddatenBuilder;
  private SchriftgutobjektBuilder schriftgutobjektBuilder;
  private NachrichtenkopfBuilder nachrichtenkopfBuilder;
  private FachdatenBuilder fachdatenBuilder;

  public NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 build(List<Beteiligung> beteiligungen) {

    NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 message0500010 = new NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010();

    message0500010.setNachrichtenkopf(nachrichtenkopfBuilder.build());
    message0500010.setGrunddaten(grunddatenBuilder.build(beteiligungen));
    message0500010.setSchriftgutobjekte(schriftgutobjektBuilder.build());
    message0500010.setFachdaten(fachdatenBuilder.build());

    return message0500010;

  }

}
