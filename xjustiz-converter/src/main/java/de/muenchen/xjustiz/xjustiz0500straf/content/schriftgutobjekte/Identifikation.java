package de.muenchen.xjustiz.xjustiz0500straf.content.schriftgutobjekte;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@Getter
@RequiredArgsConstructor
public class Identifikation {

    @NonNull
    private final String id;
    @NonNull
    private final BigInteger nummerImUebergeornetenenContainer;

}
