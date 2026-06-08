package de.muenchen.xjustiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.xjustiz.config.DynamicJsonUnmarshaller;
import de.muenchen.xjustiz.config.DynamicSchemaLocation;
import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
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
    @DependsOn({ "dynamicJsonUnmarshaller", "dynamicSchemaLocation", "dynamicXmlMarshaller" })
    public XJustizDocumentRouteBuilder xJustizDocumentRouteBuilder() {
        return new XJustizDocumentRouteBuilder();
    }

    @Bean
    public DynamicJsonUnmarshaller dynamicJsonUnmarshaller(ObjectMapper mapper) {
        return new DynamicJsonUnmarshaller(mapper);
    }

    @Bean
    public DynamicXmlMarshaller dynamicXmlMarshaller() {
        return new DynamicXmlMarshaller();
    }

    @Bean
    public DynamicSchemaLocation dynamicSchemaLocation() {
        return new DynamicSchemaLocation();
    }

}
