package se.rampoon.example5.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FileNameUtils implements Processor {
    @Override
    public void process(Exchange exchange) {
        Object customerOrderXML = exchange.getIn().getBody();

    }
}
