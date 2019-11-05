package se.rampoon.example5.beans;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Some logging of the headers
 */
@Component
public class HeaderDisplayer {
    private static final Logger LOG = LoggerFactory.getLogger(HeaderDisplayer.class);
    public void displayHeaders(Exchange exchange) {

        LOG.info("MessageProcessor.handleMessage called...MessageiD=" + exchange.getIn().getMessageId());
        // Log headers
        LOG.info("MessageProcessor.handleMessage: Message headers");
        LOG.info("***********************************************");
        exchange.getIn().getHeaders().forEach( (key, value)-> {
            LOG.info("MessageProcessor.handleMessage: Header key=" + key + " value=" + value);
        });
    }
}
