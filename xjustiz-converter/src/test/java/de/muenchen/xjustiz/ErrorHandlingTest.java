package de.muenchen.xjustiz;

import de.muenchen.xjustiz.xjustiz0500straf.content.ContentContainer;
import de.muenchen.xjustiz.xjustiz0500straf.content.FachdatenContent;
import de.muenchen.xjustiz.xjustiz0500straf.content.GrunddatenContent;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootApplication(scanBasePackages = "de.muenchen.xjustiz")
@CamelSpringBootTest
@SpringBootTest(classes = {XJustizDocumentRouteBuilder.class})
@ActiveProfiles({"default", "organisation"})
public class ErrorHandlingTest extends ExternAnJustiz0500010TestEnvironment {

    @Produce()
    private ProducerTemplate startxJustiz0500strafBuilderTest;

    @Value("${xjustiz.interface.document.processor}")
    private String testRoute;

    @Autowired
    private CamelContext camelContext;

    @Test
    void test_builderError() {

       /*
           List.of() creates an immutable list. GrunddatenBuilder is not able to add a new configured organisation.
        */
       Exchange request = ExchangeBuilder.anExchange(camelContext).withBody(new ContentContainer(new FachdatenContent(), new GrunddatenContent(List.of()))).build();
        var response  = startxJustiz0500strafBuilderTest.send(testRoute, request);
        assertNotNull(response.getException());
        var exception = response.getException();
        assertEquals("java.lang.UnsupportedOperationException", exception.getClass().getName());
     }

    @Test
    void test_xmlValidationError() {

        Exchange request = ExchangeBuilder.anExchange(camelContext).withBody(new ContentContainer(createFachdaten(), new GrunddatenContent(xmlValidationErrorMissingNachname()))).build();
        var response  = startxJustiz0500strafBuilderTest.send(testRoute, request);
        assertNotNull(response.getException());
        var exception = response.getException();
        assertEquals("org.apache.camel.support.processor.validation.SchemaValidationException", exception.getClass().getName());
    }


}
