package de.muenchen.xjustiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.xjustiz.config.DynamicJsonUnmarshaller;
import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import de.muenchen.xjustiz.generated.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.ContentContainer;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.GrunddatenContent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

@SpringBootApplication(scanBasePackages = "de.muenchen.xjustiz")
@CamelSpringBootTest
@SpringBootTest(classes = { XJustizDocumentRouteBuilder.class })
@ActiveProfiles({ "default" })
class ExternAnJustiz0500010ConfigurationTest extends ExternAnJustiz0500010TestEnvironment {

    @Produce()
    private ProducerTemplate startTest;

    @Value("${xjustiz.interface.document.processor}")
    private String testRoute;

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director nachrichtDirector;

    @Autowired
    private ObjectMapper objectMapper;

    /*
     * Read default spring profile application.yml without organisation
     * configuration.
     */
    @Test
    void test_organisationNotConfigured() throws Exception {

        final Exchange request = ExchangeBuilder.anExchange(camelContext)
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, "xjustiz_0500_straf_3_5.xsd")
                .withHeader(DynamicJsonUnmarshaller.UNMARSHAL_CLASS_TYPE, NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.class)
                .withBody(objectMapper.writeValueAsString(nachrichtDirector.build(new ContentContainer(createNachrichtenkopfContent(), createFachdaten(),
                        new GrunddatenContent(new ArrayList<>(List.of(createPersonSubjectToCoerceiveDetention())), Map.of()),
                        createSchriftgutAktenzeichenStrukuriert()))))
                .build();

        final Exchange response = startTest.send(testRoute, request);
        assertNull(response.getException(), "Error during XML creation.");
        final String xml = response.getMessage().getBody(String.class);

        final NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 externAnJustiz0500010 = parseXML(xml);

        assertEquals("1",
                externAnJustiz0500010.getGrunddaten().getVerfahrensdaten().getBeteiligung().getLast().getBeteiligter()
                        .getBeteiligtennummer(),
                "All participants are numbered incrementally. Only one beteiligter in whole document expected.");
        assertNotNull(externAnJustiz0500010.getGrunddaten().getVerfahrensdaten().getBeteiligung().getLast()
                .getBeteiligter().getAuswahlBeteiligter().getNatuerlichePerson(), "Natuerliche Person expected.");
        assertNull(externAnJustiz0500010.getGrunddaten().getVerfahrensdaten().getBeteiligung().getLast()
                .getBeteiligter().getAuswahlBeteiligter().getOrganisation(), "Organisation not expected");
    }

}
