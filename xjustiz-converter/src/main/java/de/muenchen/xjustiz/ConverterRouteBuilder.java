package de.muenchen.xjustiz;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

@Component
public class ConverterRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {



    from("{{xjustiz.route.converter.from}}").routeId("xjustiz-document-converter").description("Insert incoming meta values into xjustiz document.")
            .to("log:de.muenchen.xjustiz.xjustiz-document-converter?level=DEBUG")
            .process("justizMessageBuilder")
            .marshal().jaxb()
            .to("validator:xsd/XJustiz-3.6.2-XSD/xjustiz_0500_straf_3_6.xsd")
            .to("log:de.muenchen.xjustiz.xjustiz-document-converter?level=DEBUG");

    }
}
