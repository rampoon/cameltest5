package se.rampoon.example5.beans;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HeaderUtils {
    private static final Logger LOG = LoggerFactory.getLogger(HeaderUtils.class);

    public void addHeader(Exchange exchange) {
        exchange.getIn().getHeaders().putIfAbsent("Header1","ThisIsHeader1");
    }
}
