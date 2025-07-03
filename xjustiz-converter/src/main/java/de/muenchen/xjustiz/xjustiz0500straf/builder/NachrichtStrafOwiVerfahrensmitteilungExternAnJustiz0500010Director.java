package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.generated.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
@AllArgsConstructor
public class NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director {

  private GrunddatenBuilder grunddatenBuilder;
  private SchriftgutobjektBuilder schriftgutobjektBuilder;
  private NachrichtenkopfBuilder nachrichtenkopfBuilder;
  private FachdatenBuilder fachdatenBuilder;

  public NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 build() {

    var uniformMessageTime = Calendar.getInstance();

    NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 message0500010 = new NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010();

    message0500010.setNachrichtenkopf(nachrichtenkopfBuilder.build(uniformMessageTime));
    message0500010.setGrunddaten(grunddatenBuilder.build());
    message0500010.setSchriftgutobjekte(schriftgutobjektBuilder.build());
    message0500010.setFachdaten(fachdatenBuilder.build());

    return message0500010;

  }

}
