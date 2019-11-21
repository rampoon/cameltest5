package se.rampoon.example5.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RouteFromArtemisMQ extends RouteBuilder {

    @Value("${target.uri}")
    String targetUri;

    private static final Logger LOG = LoggerFactory.getLogger(RouteFromArtemisMQ.class);
    private final static String ROUTE_ID = "FromArtemisMQToFileRoute";

    @Override
    public void configure() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("KEY", "VALUE");
        from("{{target.uri.activemq}}")
                .routeId(ROUTE_ID)
                .log(LoggingLevel.INFO, LOG, "RouteFromArtemisMQ validation starts..")
                .to("properties:validation.uri").id("validate-input")
                .log(LoggingLevel.INFO, LOG, "received valid message with body : ${in.body}")
                .bean("myStrangeBean","unmarshal")
                .bean("databaseUtilBean","saveCustomerOrder")
                .bean("myStrangeBean","marshalCustomerOrder")
                .split().tokenizeXML("customerorder").streaming()
                .to(targetUri + "?fileName=artemismq_orderparts_${bean:staxBean.getCustomerorder.getCustomer.getId}.xml");
    }
}

