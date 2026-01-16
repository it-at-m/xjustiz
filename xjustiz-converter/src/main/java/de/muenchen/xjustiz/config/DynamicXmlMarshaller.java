package de.muenchen.xjustiz.config;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class DynamicXmlMarshaller implements Processor {

    public static final String SCHEMA_PATH = "XJustiz_Schema_Path";
    public static final String SCHEMA_NAME = "XJustiz_Schema_Name";

    @Override
    public void process(Exchange exchange) throws Exception {

        String schemaName = exchange.getIn().getHeader(SCHEMA_NAME, String.class);
        String schemaLocation = "http://www.xjustiz.de " + schemaName;

        JAXBContext context = JAXBContext.newInstance(Context.CONTEXT_PATH);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);

        exchange.getIn().setHeader("CamelJaxbMarshaller", marshaller);

    }

}
