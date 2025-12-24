package de.muenchen.xjustiz.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DynamicJsonUnmarshaller implements Processor {

    public static final String UNMARSHAL_CLASS_TYPE = "Unmarshal_Class_Type";

    private final ObjectMapper objectMapper;

    @Override
    public void process(Exchange exchange) throws Exception {

        Class<?> clazz = exchange.getIn().getHeader(UNMARSHAL_CLASS_TYPE, Class.class);

        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
        jacksonDataFormat.setUnmarshalType(clazz);
        jacksonDataFormat.setObjectMapper(objectMapper);

        String jsonBody = exchange.getIn().getBody(String.class);
        Object unmarshalledObject = jacksonDataFormat.unmarshal(exchange, jsonBody);

        exchange.getIn().setBody(unmarshalledObject);

        jacksonDataFormat.close();

    }

}
