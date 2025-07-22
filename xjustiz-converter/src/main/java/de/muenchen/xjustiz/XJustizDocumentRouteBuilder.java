package de.muenchen.xjustiz;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class XJustizDocumentRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

       onException(RuntimeException.class, Exception.class)
                .handled(false);

       from("{{xjustiz.document.processor}}").routeId("xjustiz-document-processor").description("Insert values into xjustiz document and marshal to xml.")
                .to("log:de.muenchen.xjustiz.xjustiz-document-processor?level=DEBUG")
                .process("builderConnector")
                .marshal().jaxb()
                .to("validator:xsd/XJustiz-3.6.2-XSD/xjustiz_0500_straf_3_6.xsd")
                .to("log:de.muenchen.xjustiz.xjustiz-document-processor?level=DEBUG");

    }
}
