package de.muenchen.xjustiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import de.muenchen.xjustiz.generated.*;
import de.muenchen.xjustiz.xjustiz0500straf.content.ContentContainer;
import de.muenchen.xjustiz.xjustiz0500straf.content.GrunddatenContent;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.BeforeEach;
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
public class ExternAnJustiz0500010OrganisationFreitextByCreatedTest extends ExternAnJustiz0500010TestEnvironment {

    @Produce()
    private ProducerTemplate startxJustiz0500strafBuilderTest;

    @Value("${xjustiz.interface.document.processor}")
    private String testRoute;

    @Autowired
    private CamelContext camelContext;

    private NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 externAnJustiz0500010;

    @BeforeEach
    public void init() throws Exception {

        Exchange request = ExchangeBuilder.anExchange(camelContext)
                .withBody(new ContentContainer(createNachrichtenkopfContent(), createFachdaten(),
                        new GrunddatenContent(new ArrayList<>(List.of(createPersonSubjectToCoerceiveDetention(), createApplicant())), Map.of()),
                        createSchriftgutFreitext()))
                .build();
        var response = startxJustiz0500strafBuilderTest.send(testRoute, request);
        assertNull(response.getException(), "Error during XML creation.");
        var xml = response.getMessage().getBody(String.class);
        this.externAnJustiz0500010 = parseXML(xml);
    }

    @Test
    void test_schriftgutobjekte() {

        TypeGDSSchriftgutobjekte schriftgutobjekte = this.externAnJustiz0500010.getSchriftgutobjekte();
        assertEquals("CEEF2150-F915-1F1F-1176-906D00000000", schriftgutobjekte.getAnschreiben().getRefSgo());

        assertEquals("CEEF2150-F915-1F1F-1177-906D00000000", schriftgutobjekte.getDokuments().getFirst().getIdentifikation().getId());
        assertEquals(BigInteger.ONE, schriftgutobjekte.getDokuments().getFirst().getIdentifikation().getNummerImUebergeordnetenContainer());
        assertEquals("016", schriftgutobjekte.getDokuments().getFirst().getXjustizFachspezifischeDaten().getDokumentklasse().getCode());
        assertEquals("1.4", schriftgutobjekte.getDokuments().getFirst().getXjustizFachspezifischeDaten().getDokumentklasse().getListVersionID());
        assertEquals("CEEF2150-F915-1F1F-1177-906D00000000_1000809085_5793341761427_20240807_EH.pdf",
                schriftgutobjekte.getDokuments().getFirst().getXjustizFachspezifischeDaten().getAnzeigename());
        assertEquals("1000809085_5793341761427_20240807_EH.pdf",
                schriftgutobjekte.getDokuments().getFirst().getXjustizFachspezifischeDaten().getDateis().getFirst().getDateiname());
        assertEquals(BigInteger.ONE, schriftgutobjekte.getDokuments().getFirst().getXjustizFachspezifischeDaten().getDateis().getFirst().getVersionsnummer());
        assertEquals("001", schriftgutobjekte.getDokuments().getFirst().getXjustizFachspezifischeDaten().getDateis().getFirst().getBestandteil().getCode());

        assertEquals("CEEF2150-F915-1F1F-1178-906D00000000", schriftgutobjekte.getDokuments().getLast().getIdentifikation().getId());
        assertEquals(BigInteger.ONE, schriftgutobjekte.getDokuments().getLast().getIdentifikation().getNummerImUebergeordnetenContainer());
        assertEquals("017", schriftgutobjekte.getDokuments().getLast().getXjustizFachspezifischeDaten().getDokumentklasse().getCode());
        assertEquals("CEEF2150-F915-1F1F-1178-906D00000000_1000809085_5793341761427_20240807_URB.pdf",
                schriftgutobjekte.getDokuments().getLast().getXjustizFachspezifischeDaten().getAnzeigename());
        assertEquals("1000809085_5793341761427_20240807_URB.pdf",
                schriftgutobjekte.getDokuments().getLast().getXjustizFachspezifischeDaten().getDateis().getFirst().getDateiname());
        assertEquals(BigInteger.ONE, schriftgutobjekte.getDokuments().getLast().getXjustizFachspezifischeDaten().getDateis().getLast().getVersionsnummer());
        assertEquals("002", schriftgutobjekte.getDokuments().getLast().getXjustizFachspezifischeDaten().getDateis().getLast().getBestandteil().getCode());

        assertEquals("CEEF2150-F915-1F1F-1180-906D00000000", schriftgutobjekte.getAktes().getFirst().getIdentifikation().getId());
        assertEquals(BigInteger.ONE, schriftgutobjekte.getAktes().getFirst().getIdentifikation().getNummerImUebergeordnetenContainer());

        assertEquals("XDOMEA-BY", schriftgutobjekte.getAktes().getFirst().getAnwendungsspezifischeErweiterung().getKennung());
        assertEquals("XDOMEA-Erweiterung", schriftgutobjekte.getAktes().getFirst().getAnwendungsspezifischeErweiterung().getName());

        assertEquals("freitext", schriftgutobjekte.getAktes().getFirst().getXjustizFachspezifischeDaten().getAktenzeichens().getFirst()
                .getAuswahlAktenzeichen().getAktenzeichenFreitext());

    }

}
