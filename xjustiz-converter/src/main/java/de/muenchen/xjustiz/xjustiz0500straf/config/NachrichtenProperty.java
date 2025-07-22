package de.muenchen.xjustiz.xjustiz0500straf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "xjustiz.xjustiz0500straf")
public class NachrichtenProperty {

    private NachrichtenkopfProperty nachrichtenkopf;
    private GrunddatenProperty grunddaten;

    public boolean isOrganisationConfiguredInApplicationProperties() {
        return     getGrunddaten() != null
                && getGrunddaten().getVerfahrensdaten() !=null
                && getGrunddaten().getVerfahrensdaten().getBeteiligung().getOrganisation() != null;
    }
}
