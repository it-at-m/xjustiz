package de.muenchen.xjustiz;

import de.muenchen.xjustiz.xjustiz0500straf.builder.*;
import de.muenchen.xjustiz.xjustiz0500straf.config.NachrichtenProperty;
import de.muenchen.xjustiz.xjustiz0500straf.content.NachrichtenkopfContent;
import de.muenchen.xjustiz.xoev.XJustizProperty;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;


@AutoConfiguration
public class XJustizAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "xjustiz", name={"version", "document.processor"})
    public XJustizDocumentRouteBuilder xJustizDocumentRouteBuilder() {
        return new XJustizDocumentRouteBuilder();
    }

    @Bean
    @ConditionalOnMissingBean
    public BuilderConnector builderConnector(NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director nachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director) {
        return new BuilderConnector(nachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director);
    }

    @Bean
    @ConditionalOnMissingBean
    public NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director nachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director(GrunddatenBuilder grunddatenBuilder, SchriftgutobjektBuilder schriftgutobjektBuilder, NachrichtenkopfBuilder nachrichtenkopfBuilder, FachdatenBuilder fachdatenBuilder) {
        return new NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director(grunddatenBuilder, schriftgutobjektBuilder, nachrichtenkopfBuilder, fachdatenBuilder );
    }

    @Bean
    @ConditionalOnMissingBean
    public GrunddatenBuilder grunddatenBuilder(XJustizProperty xJustizProperty, NachrichtenProperty nachrichtenProperty) {
        return new GrunddatenBuilder(xJustizProperty, nachrichtenProperty);
    };

    @Bean
    @ConditionalOnMissingBean
    public NachrichtenkopfBuilder nachrichtenkopfBuilder(XJustizProperty xJustizProperty, NachrichtenProperty nachrichtenProperty, NachrichtenkopfContent nachrichtenkopfContent) {
        return new NachrichtenkopfBuilder(xJustizProperty, nachrichtenProperty, nachrichtenkopfContent);
    };

    @Bean
    @ConditionalOnMissingBean
    public FachdatenBuilder fachdatenBuilder(XJustizProperty xJustizProperty, NachrichtenProperty nachrichtenProperty) {
        return new FachdatenBuilder(xJustizProperty, nachrichtenProperty);
    };

    @Bean
    @ConditionalOnMissingBean
    public SchriftgutobjektBuilder schriftgutobjektBuilder(XJustizProperty xJustizProperty, NachrichtenProperty nachrichtenProperty) {
        return new SchriftgutobjektBuilder();
    };

    @Bean
    @ConditionalOnMissingBean
    public XJustizProperty xJustizProperty() {
        return new XJustizProperty();
    };

    @Bean
    @ConditionalOnMissingBean
    public NachrichtenProperty nachrichtenProperty() {
        return new NachrichtenProperty();
    };

    @Bean
    @ConditionalOnMissingBean
    public NachrichtenkopfContent nachrichtenkopfContent() {
        return new NachrichtenkopfContent();
    };


}
