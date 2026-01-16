package de.muenchen.xjustiz;

import de.muenchen.xjustiz.xjustiz0500straf.nachricht.ExternAnJustiz0500010DocumentStart;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder.FachdatenBuilder;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder.GrunddatenBuilder;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder.NachrichtenkopfBuilder;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder.SchriftgutobjektBuilder;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.config.NachrichtenProperty;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.NachrichtenkopfContent;
import de.muenchen.xjustiz.xoev.XJustizProperty;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnProperty(
        prefix = "xjustiz",
        name = "xjustiz0500straf.xsd.name"
)
public class NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz05000010AutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director nachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director(
            final GrunddatenBuilder grunddatenBuilder, final SchriftgutobjektBuilder schriftgutobjektBuilder,
            final NachrichtenkopfBuilder nachrichtenkopfBuilder,
            final FachdatenBuilder fachdatenBuilder) {
        return new NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director(grunddatenBuilder, schriftgutobjektBuilder, nachrichtenkopfBuilder,
                fachdatenBuilder);
    }

    @Bean
    @ConditionalOnMissingBean
    public GrunddatenBuilder grunddatenBuilder(final XJustizProperty xJustizProperty, final NachrichtenProperty nachrichtenProperty) {
        return new GrunddatenBuilder(xJustizProperty, nachrichtenProperty);
    }

    @Bean
    @ConditionalOnMissingBean
    public NachrichtenkopfBuilder nachrichtenkopfBuilder(final XJustizProperty xJustizProperty, final NachrichtenProperty nachrichtenProperty) {
        return new NachrichtenkopfBuilder(xJustizProperty, nachrichtenProperty);
    }

    @Bean
    @ConditionalOnMissingBean
    public FachdatenBuilder fachdatenBuilder(final XJustizProperty xJustizProperty, final NachrichtenProperty nachrichtenProperty) {
        return new FachdatenBuilder(xJustizProperty, nachrichtenProperty);
    }

    @Bean
    @ConditionalOnMissingBean
    public SchriftgutobjektBuilder schriftgutobjektBuilder(final XJustizProperty xJustizProperty) {
        return new SchriftgutobjektBuilder(xJustizProperty);
    }

    @Bean
    @ConditionalOnMissingBean
    public XJustizProperty xJustizProperty() {
        return new XJustizProperty();
    }

    @Bean
    @ConditionalOnMissingBean
    public NachrichtenProperty nachrichtenProperty() {
        return new NachrichtenProperty();
    }

    @Bean
    @ConditionalOnMissingBean
    public NachrichtenkopfContent nachrichtenkopfContent() {
        return new NachrichtenkopfContent();
    }

    @Bean
    public ExternAnJustiz0500010DocumentStart externAnJustiz0500010DocumentStart(
            final NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director builderDirector) {
        return new ExternAnJustiz0500010DocumentStart(builderDirector);
    }

}
