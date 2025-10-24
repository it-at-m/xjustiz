package de.muenchen.xjustiz;

import de.muenchen.xjustiz.xjustiz0500straf.builder.NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director;
import de.muenchen.xjustiz.xjustiz0500straf.content.ContentContainer;
import lombok.AllArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component(value = "builderConnector")
@AllArgsConstructor
public class BuilderConnector implements Processor {

    private NachrichtStrafOwiVerfahrensmitteilungExternAnJustiz0500010Director externAnJustiz0500010Builder;

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody(externAnJustiz0500010Builder.build(exchange.getIn().getBody(ContentContainer.class)));
    }

}
