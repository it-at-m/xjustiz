package de.muenchen.xjustiz.xoev;

import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "xjustiz")
public class XJustizProperty {

    private Map<String, CodelistenProperty> codelisten;

}
