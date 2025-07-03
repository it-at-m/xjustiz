package de.muenchen.xjustiz;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ConverterRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

    from("{{xjustiz.route.converter.from}}").routeId("xjustiz-document-converter").description("Insert incoming values into xjustiz xml document.")
            .to("log:de.muenchen.xjustiz.xjustiz-document-converter?level=DEBUG")
            .process("builderConnector")
            .marshal().jaxb()
            .to("validator:xsd/XJustiz-3.6.2-XSD/xjustiz_0500_straf_3_6.xsd")
            .to("log:de.muenchen.xjustiz.xjustiz-document-converter?level=DEBUG");

    }
}
