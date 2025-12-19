package de.muenchen.xjustiz.config;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaxbConfig {

    public static final String CONTEXT_PATH = "de.muenchen.xjustiz.generated";

    @Bean
    public Marshaller jaxbMarshaller() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CONTEXT_PATH);
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.setProperty(
                Marshaller.JAXB_SCHEMA_LOCATION,
                "http://www.xjustiz.de xjustiz_0500_straf_3_6.xsd");

        return marshaller;
    }
}
