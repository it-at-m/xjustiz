package de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AnwendungspezifischeErweiterung {

    @NonNull
    private final String kennung;
    @NonNull
    private final String name;

}
