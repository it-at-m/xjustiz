package de.muenchen.xjustiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.xjustiz.config.DynamicJsonUnmarshaller;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

@AutoConfiguration
@ConditionalOnProperty(
        prefix = "xjustiz",
        name = { "version", "interface.document.processor", "interface.document.json-adapter" }
)
public class XJustizAutoConfiguration {

    @Bean
    @DependsOn("dynamicJsonUnmarshaller")
    public XJustizDocumentRouteBuilder xJustizDocumentRouteBuilder() {
        return new XJustizDocumentRouteBuilder();
    }

    @Bean
    public DynamicJsonUnmarshaller dynamicJsonUnmarshaller(final ObjectMapper objectMapper) {
        return new DynamicJsonUnmarshaller(objectMapper);
    }

}
