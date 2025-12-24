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
                .description("Insert values into xjustiz document and marshal to xml.")
                .log(LoggingLevel.DEBUG, "de.muenchen.xjustiz", "${body}")
                .process("dynamicJsonUnmarshaller")
                .process(new DynamicXmlMarshaller())
                .marshal().jaxb()
                .process(new DynamicSchemaLocation())
                .to("log:de.muenchen.xjustiz.xjustiz-document-processor?level=DEBUG")
                .toD("validator:xsd/XJustiz-3.5.1-XSD/${header." + DynamicXmlMarshaller.SCHEMA_NAME + "}");

    }
}
