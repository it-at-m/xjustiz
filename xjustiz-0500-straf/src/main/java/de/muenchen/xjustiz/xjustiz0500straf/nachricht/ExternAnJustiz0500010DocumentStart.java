package de.muenchen.xjustiz.xjustiz0500straf.nachricht;

import de.muenchen.xjustiz.generated.xjustiz0500straf35.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.ContentContainer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component()
@AllArgsConstructor
public class ExternAnJustiz0500010DocumentStart {

    private NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director externAnJustiz0500010Builder;

    public NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 start(ContentContainer contentContainer) {
        return externAnJustiz0500010Builder.build(contentContainer);
    }

}
