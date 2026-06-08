package de.muenchen.xjustiz;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.xjustiz.config.DynamicJsonUnmarshaller;
import de.muenchen.xjustiz.config.DynamicSchemaLocation;
import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestContext {

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

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
