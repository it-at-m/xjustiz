package de.muenchen.xjustiz.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.xjustiz.XJustizException;
import java.util.Optional;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jackson.JacksonDataFormat;

//@Component()
public class DynamicJsonUnmarshaller implements Processor {

    public static final String UNMARSHAL_CLASS_TYPE = "Unmarshal_Class_Type";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void process(Exchange exchange) throws Exception {

        Class<?> clazz = exchange.getIn().getHeader(UNMARSHAL_CLASS_TYPE, Class.class);

        Optional<String> bodyContent = Optional.ofNullable(exchange.getIn().getBody(String.class));
        bodyContent.ifPresentOrElse(body -> {

            if (isValidJSON(body)) {

                JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
                jacksonDataFormat.setUnmarshalType(clazz);
                jacksonDataFormat.setObjectMapper(objectMapper);

                String jsonBody = exchange.getIn().getBody(String.class);
                Object unmarshalledObject = null;
                try {
                    unmarshalledObject = jacksonDataFormat.unmarshal(exchange, jsonBody);
                    exchange.getIn().setBody(unmarshalledObject);
                    jacksonDataFormat.close();
                } catch (Exception e) {
                    exchange.setException(new XJustizException("Unable to unmarshal object: " + body, e));
                }

            } else {
                exchange.setException(new XJustizException(String.format("Json Body content has an invalid format (%s).", body)));
            }

        }, () -> {
            exchange.setException(new XJustizException("Json Body content is empty."));
        });

    }

    public static boolean isValidJSON(String jsonString) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
