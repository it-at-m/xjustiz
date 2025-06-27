package de.muenchen.xjustiz;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ConverterRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

    from("{{xjustiz.route.converter.from}}").routeId("xjustiz-document-converter").description("Insert incoming meta values into xjustiz document.")
            .log("${body}");

    }
}
