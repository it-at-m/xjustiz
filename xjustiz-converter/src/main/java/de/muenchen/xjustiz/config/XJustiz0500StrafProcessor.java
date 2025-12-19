package de.muenchen.xjustiz.config;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XJustiz0500StrafProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String xml = exchange.getIn().getBody(String.class);

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setNamespaceAware(true);
        Document document = documentFactory.newDocumentBuilder()
                .parse(new org.xml.sax.InputSource(new StringReader(xml)));

        Element root = document.getDocumentElement();

        root.setAttribute(
                "xsi:schemaLocation",
                "http://www.xjustiz.de xjustiz_0500_straf_3_5.xsd");

        root.setAttribute(
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
        exchange.getIn().setBody(result);
    }
}
