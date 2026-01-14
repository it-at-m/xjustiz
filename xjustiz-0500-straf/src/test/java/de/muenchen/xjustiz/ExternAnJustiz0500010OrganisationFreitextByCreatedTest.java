package de.muenchen.xjustiz;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import de.muenchen.xjustiz.config.DynamicJsonUnmarshaller;
import de.muenchen.xjustiz.config.DynamicXmlMarshaller;
import de.muenchen.xjustiz.generated.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010;
import de.muenchen.xjustiz.generated.TypeGDSSchriftgutobjekte;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.builder.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.ContentContainer;
import de.muenchen.xjustiz.xjustiz0500straf.nachricht.straf.owi.verfahrensmitteilung.extern.an.justiz0500010.content.GrunddatenContent;

@SpringBootApplication(scanBasePackages = "de.muenchen.xjustiz")
@CamelSpringBootTest
@SpringBootTest(classes = { XJustizDocumentRouteBuilder.class })
@ActiveProfiles({ "default" })
class ExternAnJustiz0500010OrganisationFreitextByCreatedTest extends ExternAnJustiz0500010TestEnvironment {

    @Produce()
    private ProducerTemplate startxJustiz0500strafBuilderTest;

    @Value("${xjustiz.interface.document.processor}")
    private String testRoute;

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director nachrichtDirector;

    private NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010 externAnJustiz0500010;
    
    @Autowired
    private ObjectMapper objectMapper;
 
    @BeforeEach
    public void init() throws Exception {

        final Exchange request = ExchangeBuilder.anExchange(camelContext)
                .withHeader(DynamicXmlMarshaller.SCHEMA_NAME, "xjustiz_0500_straf_3_5.xsd")
                .withHeader(DynamicJsonUnmarshaller.UNMARSHAL_CLASS_TYPE, NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010.class)
                .withBody(objectMapper.writeValueAsString(nachrichtDirector.build(new ContentContainer(createNachrichtenkopfContent(), createFachdaten(),
                        new GrunddatenContent(new ArrayList<>(List.of(createPersonSubjectToCoerceiveDetention(), createApplicant())), Map.of()),
                        createSchriftgutFreitext())))).build();

        final Exchange response = startxJustiz0500strafBuilderTest.send(testRoute, request);
        assertNull(response.getException(), "Error during XML creation.");
        final String xml = response.getMessage().getBody(String.class);
        this.externAnJustiz0500010 = parseXML(xml);
    }

    @Test
    void test_schriftgutobjekte() {

        final TypeGDSSchriftgutobjekte schriftgutobjekte = this.externAnJustiz0500010.getSchriftgutobjekte();
        assertEquals("CEEF2150-F915-1F1F-1176-906D00000000", schriftgutobjekte.getAnschreiben().getRefSgo());

        assertEquals("CEEF2150-F915-1F1F-1177-906D00000000", schriftgutobjekte.getDokument().getFirst().getIdentifikation().getId());
        assertEquals(BigInteger.ONE, schriftgutobjekte.getDokument().getFirst().getIdentifikation().getNummerImUebergeordnetenContainer());
        assertEquals("016", schriftgutobjekte.getDokument().getFirst().getXjustizFachspezifischeDaten().getDokumentklasse().getCode());
        assertEquals("1.4", schriftgutobjekte.getDokument().getFirst().getXjustizFachspezifischeDaten().getDokumentklasse().getListVersionID());
        assertEquals("CEEF2150-F915-1F1F-1177-906D00000000_1000809085_5793341761427_20240807_EH.pdf",
                schriftgutobjekte.getDokument().getFirst().getXjustizFachspezifischeDaten().getAnzeigename());
        assertEquals("1000809085_5793341761427_20240807_EH.pdf",
                schriftgutobjekte.getDokument().getFirst().getXjustizFachspezifischeDaten().getDatei().getFirst().getDateiname());
        assertEquals(BigInteger.ONE, schriftgutobjekte.getDokument().getFirst().getXjustizFachspezifischeDaten().getDatei().getFirst().getVersionsnummer());
        assertEquals("001", schriftgutobjekte.getDokument().getFirst().getXjustizFachspezifischeDaten().getDatei().getFirst().getBestandteil().getCode());

        assertEquals("CEEF2150-F915-1F1F-1178-906D00000000", schriftgutobjekte.getDokument().getLast().getIdentifikation().getId());
        assertEquals(BigInteger.ONE, schriftgutobjekte.getDokument().getLast().getIdentifikation().getNummerImUebergeordnetenContainer());
        assertEquals("017", schriftgutobjekte.getDokument().getLast().getXjustizFachspezifischeDaten().getDokumentklasse().getCode());
        assertEquals("CEEF2150-F915-1F1F-1178-906D00000000_1000809085_5793341761427_20240807_URB.pdf",
                schriftgutobjekte.getDokument().getLast().getXjustizFachspezifischeDaten().getAnzeigename());
        assertEquals("1000809085_5793341761427_20240807_URB.pdf",
                schriftgutobjekte.getDokument().getLast().getXjustizFachspezifischeDaten().getDatei().getFirst().getDateiname());
        assertEquals(BigInteger.ONE, schriftgutobjekte.getDokument().getLast().getXjustizFachspezifischeDaten().getDatei().getLast().getVersionsnummer());
        assertEquals("002", schriftgutobjekte.getDokument().getLast().getXjustizFachspezifischeDaten().getDatei().getLast().getBestandteil().getCode());

        assertEquals("CEEF2150-F915-1F1F-1180-906D00000000", schriftgutobjekte.getAkte().getFirst().getIdentifikation().getId());
        assertEquals(BigInteger.ONE, schriftgutobjekte.getAkte().getFirst().getIdentifikation().getNummerImUebergeordnetenContainer());

        assertEquals("XDOMEA-BY", schriftgutobjekte.getAkte().getFirst().getAnwendungsspezifischeErweiterung().getKennung());
        assertEquals("XDOMEA-Erweiterung", schriftgutobjekte.getAkte().getFirst().getAnwendungsspezifischeErweiterung().getName());

        assertEquals("freitext", schriftgutobjekte.getAkte().getFirst().getXjustizFachspezifischeDaten().getAktenzeichen().getFirst()
                .getAuswahlAktenzeichen().getAktenzeichenFreitext());

    }

}
