package se.rampoon.example5;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.ExchangeBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import se.rampoon.example5.SpringBootCamelArtemisMQ;
import se.rampoon.example5.beans.HeaderDisplayer;
import se.rampoon.example5.beans.HeaderUtils;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("artemismq")
public class SpringBootTestApplicationTest{
    private final String HEADER1_KEY = "Header1";
    private final String HEADER1_VALUE = "ThisIsHeader1";
    private final String LOVERCASE_TEXT = "example of lowercase text";

    @Autowired
    CamelContext camelContext;

    @Autowired
    SpringBootCamelArtemisMQ springBootCamelArtemisMQ;

    @Autowired
    HeaderDisplayer headerDisplayer;

    @Autowired
    HeaderUtils headerUtils;

    /**
     * Test of DisplayHeaders
     */
    @Test
    public void testDisplayHeaders() {
        System.out.println("*********** Start UnitTest displayHeaders **********");
        headerDisplayer.displayHeaders(getExchange());
        assertTrue( true );
    }

    /**
     * Test of HeaderUtils.addHeader
     */
    @Test
    public void testAddHeader() {
        System.out.println("*********** Start UnitTest testAddHeader **********");
        Exchange exChange = getExchange();
        headerUtils.addHeader(exChange);
        assertNotNull(exChange.getIn().getHeader(HEADER1_KEY));
    }

    private Exchange getExchange() {
        return ExchangeBuilder.anExchange(camelContext).build();
    }
}

