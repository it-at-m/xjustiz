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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootApplication( scanBasePackages = "de.muenchen.xjustiz" )
@CamelSpringBootTest
@SpringBootTest(classes = {ConverterRouteBuilder.class})
public class ConverterTest {

    @Autowired
    private CamelContext camelContext;

    @Produce("direct:start")
    private ProducerTemplate startTest;

    @Value("${xjustiz.route.converter.from}")
    private String testRoute;

    @Test
    void test_HelloWorld() throws Exception {

        var response  = startTest.requestBody(testRoute,"Hello World", String.class);
        assertEquals("Hello World", response);

    }


}
