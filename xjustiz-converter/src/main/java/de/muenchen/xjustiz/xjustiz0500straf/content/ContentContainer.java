package de.muenchen.xjustiz.xjustiz0500straf.content;

import java.util.Optional;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ContentContainer {

    private final NachrichtenkopfContent nachrichtenkopfContent;
    private final FachdatenContent fachdatenContent;
    private final GrunddatenContent grunddatenContent;
    private final SchriftgutContent schriftgutContent;

    public Optional<NachrichtenkopfContent> getNachrichtenkopfContent() {
        return Optional.ofNullable(nachrichtenkopfContent);
    }

    public Optional<FachdatenContent> getFachdatenContent() {
        return Optional.ofNullable(fachdatenContent);
    }

    public Optional<GrunddatenContent> getGrunddatenContent() {
        return Optional.ofNullable(grunddatenContent);
    }

    public Optional<SchriftgutContent> getSchriftgutContent() {
        return Optional.ofNullable(schriftgutContent);
    }

}
