package de.muenchen.xjustiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.xjustiz.config.DynamicJsonUnmarshaller;
import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import de.muenchen.xjustiz.generated.xjustiz0500straf36.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.ContentContainer;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.GrunddatenContent;
import de.muenchen.xjustizlib.xoev.XJustizProperty;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication(scanBasePackages = "de.muenchen.xjustiz")
@CamelSpringBootTest
@SpringBootTest(classes = { XJustizDocumentRouteBuilder.class })
@ActiveProfiles({ "default", "organisation" })
@EnableConfigurationProperties(XJustizProperty.class)
class ErrorHandlingTest extends ExternAnJustiz0500010TestEnvironment {

    @Produce()
    private ProducerTemplate startxJustiz0500strafBuilderTest;

    @Value("${xjustiz.interface.document.json-adapter}")
    private String testRoute;

    @Value("${xjustiz.xjustiz0500straf.xsd.name}")
    private String schemaName;

    @Value("${xjustiz.xsd.path}")
    private String schemaPath;

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director nachrichtDirector;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_xmlValidationError() throws DatatypeConfigurationException, JsonProcessingException {

        final Exchange request = ExchangeBuilder.anExchange(camelContext)
                .withHeader(DynamicXmlMarshaller.SCHEMA_PATH, schemaPath)
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, schemaName)
                .withHeader(DynamicJsonUnmarshaller.UNMARSHAL_CLASS_TYPE, NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.class)
                .withBody(objectMapper.writeValueAsString(nachrichtDirector.build(new ContentContainer(createNachrichtenkopfContent(), createFachdaten(),
                        new GrunddatenContent(xmlValidationErrorMissingNachname(), Map.of()), createSchriftgutAktenzeichenStrukuriert()))))
                .build();

        final Exchange response = startxJustiz0500strafBuilderTest.send(testRoute, request);
        assertNotNull(response.getException());
        final Exception exception = response.getException();
        assertEquals("org.apache.camel.support.processor.validation.SchemaValidationException", exception.getClass().getName());
    }

}
