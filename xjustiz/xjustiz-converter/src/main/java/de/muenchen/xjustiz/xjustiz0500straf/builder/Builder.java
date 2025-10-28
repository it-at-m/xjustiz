package de.muenchen.xjustiz.xjustiz0500straf.builder;

import de.muenchen.xjustiz.xjustiz0500straf.config.NachrichtenProperty;
import de.muenchen.xjustiz.xoev.XJustizProperty;

public class Builder extends XJustizBuilder {

    protected final NachrichtenProperty nachrichtenProperty;

    public Builder(XJustizProperty xjustizProperty, NachrichtenProperty nachrichtenProperty) {
        super(xjustizProperty);
        this.nachrichtenProperty = nachrichtenProperty;
    }

}
