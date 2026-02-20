package de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder;

import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.config.NachrichtenProperty;
import de.muenchen.xjustizlib.xoev.XJustizProperty;

public class Builder extends XJustizBuilder {

    protected final NachrichtenProperty nachrichtenProperty;

    public Builder(final XJustizProperty xjustizProperty, final NachrichtenProperty nachrichtenProperty) {
        super(xjustizProperty);
        this.nachrichtenProperty = nachrichtenProperty;
    }

}
