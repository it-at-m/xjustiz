package de.muenchen.xjustiz.config;

import de.muenchen.xjustiz.XJustizException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import java.util.Optional;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DynamicXmlMarshaller implements Processor {

    public static final String SCHEMA_PATH = "XJustiz_Schema_Path";
    public static final String SCHEMA_NAME = "XJustiz_Schema_Name";

    @Value(value = "${xjustiz.xsd.generated-package-base}")
    private String generatedPackageBase;

    @Override
    public void process(Exchange exchange) throws Exception {

        String schemaName = exchange.getIn().getHeader(SCHEMA_NAME, String.class);
        String schemaLocation = "http://www.xjustiz.de " + schemaName;

        Optional<String> normalizedSchemaName = normalizeFilename(schemaName);

        if (normalizedSchemaName.isPresent()) {
            JAXBContext context = JAXBContext.newInstance(generatedPackageBase.concat("." + normalizedSchemaName.get()));
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);

            exchange.getIn().setHeader("CamelJaxbMarshaller", marshaller);
        } else {
            exchange.setException(new XJustizException(String.format("Invalid schema name '%s'.", schemaName)));
        }

    }

    /**
     * Normalizes a filename by removing underscores and the file extension.
     * Example: "xjustiz_0500_straf_3_5.xsd" becomes "xjustiz0500straf35"
     *
     * @param input The input string to normalize
     * @return The normalized string
     */
    public Optional<String> normalizeFilename(String input) {

        if (input == null || input.isEmpty()) {
            return Optional.empty();
        }

        String withoutExtension = FilenameUtils.removeExtension(input);
        return Optional.of(withoutExtension.replace("_", ""));

    }

}
