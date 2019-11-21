package se.rampoon.example5.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Split file per Customer and set CustomerId as part of name
 */
public class SplitAndCreateFileWithNameBasedOnContent implements Processor {

    Logger logger = LoggerFactory.getLogger(SplitAndCreateFileWithNameBasedOnContent.class);


    @Override
    public void process(Exchange e) throws XMLStreamException, IOException {

        File incomingFile = e.getIn().getBody(File.class);
        try (FileInputStream fis = new FileInputStream(incomingFile)) {


            logger.debug("Attachment size: {}", 100);



            Files.copy(fis, Paths.get("/" + "Test"), StandardCopyOption.REPLACE_EXISTING);


        } catch (Exception e2) {
                    e2.printStackTrace();
        }

    }
}
