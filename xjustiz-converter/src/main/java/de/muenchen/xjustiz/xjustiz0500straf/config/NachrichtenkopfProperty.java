package de.muenchen.xjustiz.xjustiz0500straf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "xjustiz.xjustiz0500straf.nachrichtenkopf")
public class NachrichtenkopfProperty {

    private String auswahlAbsenderSonstige;
    private String auswahlHerstellerinformationProdukt;
    private String auswahlHerstellerinformationProduktName;
    private String auswahlHerstellerinformationProduktVersion;
    private Map<String, CodelistenProperty> codelisten;

}
