package se.rampoon.example5.beans;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.rampoon.cameltest.customerorder.*;
import se.rampoon.example5.utils.DateUtils;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.XMLEventReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class StaxBean {

    private static final Logger LOG = LoggerFactory.getLogger(StaxBean.class);

    private static final String CUSTOMERORDERS_ELEMENT = "customerorders";
    private static final String CUSTOMERORDER_ELEMENT = "customerorder";
    private static final String CUSTOMER_ELEMENT = "customer";
    private static final String ID_ELEMENT = "id";
    private static final String NAME_ELEMENT  = "name";
    private static final String ORDERS_ELEMENT = "orders";
    private static final String ORDER_ELEMENT = "order";
    private static final String DATE_ELEMENT = "date";
    private static final String PRODUCTTYPE_ELEMENT = "productName";
    private static final String QUANTITY_ELEMENT = "quantity";

    public CustomerorderType getCustomerorder(Exchange exchange) {
        CustomerorderType customerorderType = null;
        CustomerType customerType = null;
        OrderList orderList = null;
        Order order = null;
        String message = exchange.getIn().getBody(String.class);

        LOG.info("StaxBean.getCustomerorder: message=" + message);
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {

            boolean inCustomerOrderElement = false;
            boolean inCustomerElement = false;
            boolean inOrdersElement = false;
            boolean inOrderElement = false;
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new ByteArrayInputStream(message.getBytes()));
            while(xmlEventReader.hasNext()){
                LOG.info("StaxBean.getCustomerType: new Event");
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if(startElement.getName().getLocalPart().equals(CUSTOMERORDER_ELEMENT)) {
                        inCustomerOrderElement = true;
                        customerorderType = new CustomerorderType();
                    }
                    else if(startElement.getName().getLocalPart().equals(CUSTOMER_ELEMENT)) {
                        inCustomerElement = true;
                        customerType = new CustomerType();
                    }
                    else if(startElement.getName().getLocalPart().equals(ORDERS_ELEMENT)) {
                        inOrderElement = true;
                        orderList = new OrderList();
                    }
                    else if(startElement.getName().getLocalPart().equals(ORDER_ELEMENT)) {
                        inOrderElement = true;
                        order = new Order();
                    }
                    else if (startElement.getName().getLocalPart().equals(ID_ELEMENT)) {
                        xmlEvent = xmlEventReader.nextEvent();
                        String id = xmlEvent.asCharacters().getData();
                        if(inCustomerElement) {
                            LOG.info("StaxBean.getCustomerType: new Event id=" + id);
                            customerType.setId(new BigInteger(id));
                        }
                        else if(inOrderElement) {
                            LOG.info("StaxBean.getCustomerType: new Event id=" + id);
                            order.setId(new BigInteger(id));
                        }
                    }
                    else if (startElement.getName().getLocalPart().equals(NAME_ELEMENT)) {
                        xmlEvent = xmlEventReader.nextEvent();
                        if(inCustomerElement) {
                            customerType.setName(xmlEvent.asCharacters().getData());
                        }
                    }
                    else if (startElement.getName().getLocalPart().equals(DATE_ELEMENT)) {
                        xmlEvent = xmlEventReader.nextEvent();
                        if(inOrderElement) {
                            order.setDate(DateUtils.stringToXMLGregorianCalendar(xmlEvent.asCharacters().getData()));
                        }
                    }
                }
                if(xmlEvent.isEndElement()){
                    EndElement endElement = xmlEvent.asEndElement();
                    if(endElement.getName().getLocalPart().equals(ORDER_ELEMENT)) {
                        inOrderElement = false;
                        orderList.getOrder().add(order);
                    }
                    if(endElement.getName().getLocalPart().equals(ORDERS_ELEMENT)) {
                        inOrdersElement = false;
                        customerorderType.setOrders(orderList);
                    }
                    else if(endElement.getName().getLocalPart().equals(CUSTOMER_ELEMENT)){
                        inCustomerElement = false;
                        customerorderType.setCustomer(customerType);
                    }
                    else if(endElement.getName().getLocalPart().equals(CUSTOMERORDER_ELEMENT)){
                        inCustomerOrderElement = false;
                    }
                }
            }
        }
        catch (XMLStreamException | DatatypeConfigurationException e ) {
            LOG.error("StaxBean.", e);
        }
        return customerorderType;
    }

     public List<CustomerorderType> getCustomerorderList(Exchange exchange) {
        String customerId = "NOTSET";
        List<CustomerorderType> customerOrderList = null;
        CustomerorderType customerorderType = null;
        CustomerType customerType = null;
        OrderList orderList = null;
        Order order = null;
        String message = exchange.getIn().getBody(String.class);

        LOG.info("StaxBean.getCustomerorderList: message=" + message);
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            boolean inCustomerOrders = false;
            boolean inCustomerOrderElement = false;
            boolean inCustomerElement = false;
            boolean inOrdersElement = false;
            boolean inOrderElement = false;
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new ByteArrayInputStream(message.getBytes()));
            while(xmlEventReader.hasNext()){
                LOG.info("StaxBean.getCustomerType: new Event");
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals(CUSTOMERORDERS_ELEMENT)) {
                        inCustomerOrders = true;
                    }
                    else if (startElement.getName().getLocalPart().equals(CUSTOMERORDER_ELEMENT)) {
                        inCustomerOrderElement = true;
                        if(customerOrderList == null) {
                            customerOrderList = new ArrayList<>();
                        }
                        customerorderType = new CustomerorderType();
                    }
                    else if(startElement.getName().getLocalPart().equals(CUSTOMER_ELEMENT)) {
                        inCustomerElement = true;
                        customerType = new CustomerType();
                    }
                    else if(startElement.getName().getLocalPart().equals(ORDERS_ELEMENT)) {
                        inOrderElement = true;
                        orderList = new OrderList();
                    }
                    else if(startElement.getName().getLocalPart().equals(ORDER_ELEMENT)) {
                        inOrderElement = true;
                        order = new Order();
                    }
                    else if (startElement.getName().getLocalPart().equals(ID_ELEMENT)) {
                        xmlEvent = xmlEventReader.nextEvent();
                        String id = xmlEvent.asCharacters().getData();
                        if(inCustomerElement) {
                            LOG.info("StaxBean.getCustomerType: new Event id=" + id);
                            customerType.setId(new BigInteger(id));
                        }
                        else if(inOrderElement) {
                            LOG.info("StaxBean.getCustomerType: new Event id=" + id);
                            order.setId(new BigInteger(id));
                        }
                    }
                    else if (startElement.getName().getLocalPart().equals(NAME_ELEMENT)) {
                        xmlEvent = xmlEventReader.nextEvent();
                        if(inCustomerElement) {
                            customerType.setName(xmlEvent.asCharacters().getData());
                        }
                    }
                    else if (startElement.getName().getLocalPart().equals(DATE_ELEMENT)) {
                        xmlEvent = xmlEventReader.nextEvent();
                        if(inOrderElement) {
                            order.setDate(DateUtils.stringToXMLGregorianCalendar(xmlEvent.asCharacters().getData()));
                        }
                    }
                }
                if(xmlEvent.isEndElement()){
                    EndElement endElement = xmlEvent.asEndElement();
                    if(endElement.getName().getLocalPart().equals(ORDER_ELEMENT)) {
                        inOrderElement = false;
                        orderList.getOrder().add(order);
                    }
                    if(endElement.getName().getLocalPart().equals(ORDERS_ELEMENT)) {
                        inOrdersElement = false;
                        customerorderType.setOrders(orderList);
                    }
                    else if(endElement.getName().getLocalPart().equals(CUSTOMER_ELEMENT)){
                        inCustomerElement = false;
                        customerorderType.setCustomer(customerType);
                    }
                    else if(endElement.getName().getLocalPart().equals(CUSTOMERORDER_ELEMENT)){
                        inCustomerOrderElement = false;
                        customerOrderList.add(customerorderType);
                    }
                    else if(endElement.getName().getLocalPart().equals(CUSTOMERORDERS_ELEMENT)){
                        inCustomerOrders = false;
                    }
                }
            }
        }
        catch (XMLStreamException | DatatypeConfigurationException e ) {
            LOG.error("StaxBean.", e);
        }
        return customerOrderList;
    }
}
