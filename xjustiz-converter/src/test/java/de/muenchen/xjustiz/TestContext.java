package de.muenchen.xjustiz;

import de.muenchen.xjustiz.config.DynamicJsonUnmarshaller;
import de.muenchen.xjustiz.config.DynamicSchemaLocation;
import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestContext {

    @Bean
    public DynamicJsonUnmarshaller dynamicJsonUnmarshaller() {
        return new DynamicJsonUnmarshaller();
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
