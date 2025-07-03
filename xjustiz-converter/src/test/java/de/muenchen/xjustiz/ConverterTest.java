package de.muenchen.xjustiz;

import org.apache.camel.CamelContext;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootApplication( scanBasePackages = "de.muenchen.xjustiz" )
@CamelSpringBootTest
@SpringBootTest(classes = {ConverterRouteBuilder.class})
public class ConverterTest {

    @Produce("direct:start")
    private ProducerTemplate startTest;

    @Value("${xjustiz.route.converter.from}")
    private String testRoute;

    @Test
    void test_externAnJustiz_0500010() throws Exception {

        var response  = startTest.requestBody(testRoute,"Hello World", String.class);
        assertTrue(response.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<ns2:nachricht.straf.owi.verfahrensmitteilung.externAnJustiz.0500010 xmlns:ns2=\"http://www.xjustiz.de\">"));

    }

}
