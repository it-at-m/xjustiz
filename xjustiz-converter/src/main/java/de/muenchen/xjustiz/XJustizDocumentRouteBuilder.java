package de.muenchen.xjustiz;

import de.muenchen.xjustiz.config.DynamicSchemaLocation;
import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class XJustizDocumentRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(RuntimeException.class, Exception.class)
                .handled(false);

        from("{{xjustiz.interface.document.processor}}").routeId("xjustiz-document-processor")
                .description("Marshal xjustiz document to xml and add required namespaces.")
                .log(LoggingLevel.DEBUG, "de.muenchen.xjustiz", "${body}")
                .process(new DynamicXmlMarshaller())
                .marshal().jaxb()
                .process(new DynamicSchemaLocation())
                .to("log:de.muenchen.xjustiz.xjustiz-document-processor?level=DEBUG")
                .toD(String.format("validator:${header.%s}${header.%s}", DynamicXmlMarshaller.SCHEMA_PATH, DynamicXmlMarshaller.SCHEMA_NAME));

        from("{{xjustiz.interface.document.json-adapter}}").routeId("xjustiz-document-json-adapter")
                .description("Unmarshall JSON to xjustiz document.")
                .log(LoggingLevel.DEBUG, "de.muenchen.xjustiz", "${body}")
                .process("dynamicJsonUnmarshaller")
                .to("{{xjustiz.interface.document.processor}}");

    }
}
