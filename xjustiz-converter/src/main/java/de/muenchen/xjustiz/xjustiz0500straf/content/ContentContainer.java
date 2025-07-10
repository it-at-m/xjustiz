package de.muenchen.xjustiz.xjustiz0500straf.content;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ContentContainer {

    private final FachdatenContent fachdatenContent;
    private final GrunddatenContent grunddatenContent;

    public Optional<FachdatenContent> getFachdatenContent() {
        return Optional.ofNullable(fachdatenContent);
    }
    public Optional<GrunddatenContent> getGrunddatenContent() {
        return Optional.ofNullable(grunddatenContent);
    }

}
