package de.muenchen.xjustiz.xjustiz0500straf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
public class NachrichtenkopfProperty {

    private String auswahlAbsenderSonstige;
    private String auswahlHerstellerinformationProdukt;
    private String auswahlHerstellerinformationProduktName;
    private String auswahlHerstellerinformationProduktVersion;

}
