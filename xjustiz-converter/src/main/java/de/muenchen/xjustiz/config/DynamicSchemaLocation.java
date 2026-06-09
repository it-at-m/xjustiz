package de.muenchen.xjustiz.config;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Log4j2
public class DynamicSchemaLocation implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String schemaName = exchange.getIn().getHeader(DynamicXmlMarshaller.SCHEMA_NAME, String.class);
        String xml = exchange.getIn().getBody(String.class);

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setNamespaceAware(true);
        Document document = documentFactory.newDocumentBuilder()
                .parse(new org.xml.sax.InputSource(new StringReader(xml)));

        Element root = document.getDocumentElement();

        root.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance",
                "xsi:schemaLocation",
                "http://www.xjustiz.de " + schemaName);

        root.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI,
                "xmlns:xsi",
                "http://www.w3.org/2001/XMLSchema-instance");

        TransformerFactory transformerfactory = TransformerFactory.newInstance();
        Transformer transformer = transformerfactory.newTransformer();

        StringWriter writer = new StringWriter();

        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");

        transformer.transform(
                new DOMSource(document),
                new StreamResult(writer));

        String result = writer.toString();

        log.debug("DynamicSchemaLocation output: {}", result);

        exchange.getIn().setBody(result);
    }
}
