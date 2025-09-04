package de.muenchen.xjustiz;

import static org.junit.jupiter.api.Assertions.*;

import de.muenchen.xjustiz.xjustiz0500straf.content.ContentContainer;
import de.muenchen.xjustiz.xjustiz0500straf.content.GrunddatenContent;
import java.util.ArrayList;
import java.util.List;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication(scanBasePackages = "de.muenchen.xjustiz")
@CamelSpringBootTest
@SpringBootTest(classes = { XJustizDocumentRouteBuilder.class })
@ActiveProfiles({ "default" })
public class ExternAnJustiz0500010ConfigurationTest extends ExternAnJustiz0500010TestEnvironment {

    @Produce()
    private ProducerTemplate startTest;

    @Value("${xjustiz.interface.document.processor}")
    private String testRoute;

    /*
     * Read default spring profile application.yml without organisation configuration.
     */
    @Test
    void test_organisationNotConfigured() throws Exception {

        var xml = startTest.requestBody(testRoute, new ContentContainer(createFachdaten(),
                new GrunddatenContent(new ArrayList<>(List.of(createPersonSubjectToCoerceiveDetention()))), createSchriftgut()), String.class);

        var externAnJustiz0500010 = parseXML(xml);

        assertEquals("1", externAnJustiz0500010.getGrunddaten().getVerfahrensdaten().getBeteiligungs().getLast().getBeteiligter().getBeteiligtennummer(),
                "All participants are numbered incrementally. Only one beteiligter in whole document expected.");
        assertNotNull(externAnJustiz0500010.getGrunddaten().getVerfahrensdaten().getBeteiligungs().getLast().getBeteiligter().getAuswahlBeteiligter()
                .getNatuerlichePerson(), "Natuerliche Person expected.");
        assertNull(externAnJustiz0500010.getGrunddaten().getVerfahrensdaten().getBeteiligungs().getLast().getBeteiligter().getAuswahlBeteiligter()
                .getOrganisation(), "Organisation not expected");
    }

}
