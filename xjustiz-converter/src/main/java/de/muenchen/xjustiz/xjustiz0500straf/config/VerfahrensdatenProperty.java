package de.muenchen.xjustiz.xjustiz0500straf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "xjustiz.xjustiz0500straf.grunddaten.verfahrensdaten")
public class VerfahrensdatenProperty {

    private InstanzdatenProperty instanzdaten;
    private BeteiligungProperty beteiligung;

    private Map<String, CodelistenProperty> codelisten;

    public boolean isOrganisationConfiguredInApplicationProperties() {
        return getBeteiligung() != null && getBeteiligung().getOrganisation() != null;
    }

}
