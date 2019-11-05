package se.rampoon.example5.routes;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RouteFromArtemisMQ extends RouteBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(RouteFromArtemisMQ.class);
    private final static String ROUTE_ID = "FromArtemisMQToFileRoute";

    @Override
    public void configure() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("KEY", "VALUE");
        from("{{target.uri.activemq}}")
                .routeId(ROUTE_ID)
                .log(LoggingLevel.INFO, LOG, "RouteFromArtemisMQ starting...")
                .log(LoggingLevel.INFO, LOG, "received message with body : ${in.body}")
                .bean("headerUtils", "addHeader")
                .setHeader("Header2", simple("999"))
                .setHeader("Header4", constant("8888"))
                .bean("headerDisplayer", "displayHeaders")
                .split().tokenizeXML("order").streaming()
               // .to("properties:target.uri").id("output");
                .to("file:./output?fileName=artemismq_orderparts_${bean:fileNameBean.getUniqueFileName}.xml");
    }
}

