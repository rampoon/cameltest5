package se.rampoon.example5.beans;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import se.rampoon.cameltest.customerorder.CustomerType;
import se.rampoon.cameltest.customerorder.CustomerorderType;
import se.rampoon.cameltest.customerorder.Customerorders;
import se.rampoon.cameltest.customerorder.Order;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLEventReader;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component("myStrangeBean")
public class MarshallBean {

	private JAXBContext contextCustomerOrder;
	private Marshaller marshallerCustomerOrder;
	private Unmarshaller unmarshallerCustomerOrder;
	private final String contextPathCustomerOrder = "se.rampoon.cameltest.customerorder";
	private static final Logger LOG = LoggerFactory.getLogger(MarshallBean.class);

	/**
	 * Marshalls body of incoming Exchange object to a XML-representation in a String object.
	 * @param customerorders
	 * @return
	 * @throws JAXBException
	 */
	public String marshal(Exchange exchange) throws JAXBException {

		contextCustomerOrder = JAXBContext.newInstance(contextPathCustomerOrder);
		marshallerCustomerOrder = contextCustomerOrder.createMarshaller();
		marshallerCustomerOrder.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshallerCustomerOrder.setProperty( "jaxb.encoding", "UTF-8");
		Customerorders customerorders = exchange.getIn().getBody(Customerorders.class);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter( byteArrayOutputStream, StandardCharsets.UTF_8);
		marshallerCustomerOrder.marshal(customerorders, outputStreamWriter);
		return byteArrayOutputStream.toString();
	}

	/**
	 * Marshalls the incoming Customerorders object structure to a XML-representation in a String object.
	 * @param customerorders
	 * @return
	 * @throws JAXBException
	 */
	public String marshalCustomerOrder(Customerorders  customerorders) throws JAXBException {
		contextCustomerOrder = JAXBContext.newInstance(contextPathCustomerOrder);
		marshallerCustomerOrder = contextCustomerOrder.createMarshaller();
		marshallerCustomerOrder.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshallerCustomerOrder.setProperty( "jaxb.encoding", "UTF-8");
		//foreach()
		//LOG.info("marshallCustomerOrder: customerorders (customerorders.getCustomerorder().getCustomer().getName())=" + customerorders.getCustomerorder());

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter( byteArrayOutputStream, StandardCharsets.UTF_8);
		marshallerCustomerOrder.marshal(customerorders, outputStreamWriter);
		return byteArrayOutputStream.toString();
	}

	public String marshalSingleCustomerOrder(Customerorders  customerorders) throws JAXBException {
		contextCustomerOrder = JAXBContext.newInstance(contextPathCustomerOrder);
		marshallerCustomerOrder = contextCustomerOrder.createMarshaller();
		marshallerCustomerOrder.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		marshallerCustomerOrder.setProperty( "jaxb.encoding", "UTF-8");
		List<CustomerorderType> customerOrderList = customerorders.getCustomerorder();
		customerOrderList.forEach(customerOrder-> {
			CustomerType customerType = customerOrder.getCustomer();
				// todo:   add some clever stuff...
			List<Order> orderList = customerOrder.getOrders().getOrder();
				// todo:   add some clever stuff...
			});

		//LOG.info("marshallCustomerOrder: customerorders (customerorders.getCustomerorder().getCustomer().getName())=" + customerorders.getCustomerorder());

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter( byteArrayOutputStream, StandardCharsets.UTF_8);
		marshallerCustomerOrder.marshal(customerorders, outputStreamWriter);
		return byteArrayOutputStream.toString();
	}

	/**
	 * Unmarshalls the incoming message to a Customerorders object structure
	 * @param exchange
	 * @return
	 * @throws JAXBException
	 */
	public Customerorders unmarshal(Exchange exchange) throws JAXBException {
		contextCustomerOrder = JAXBContext.newInstance(contextPathCustomerOrder);
		unmarshallerCustomerOrder = contextCustomerOrder.createUnmarshaller();
		String message = exchange.getIn().getBody(String.class);
		LOG.info("unmarshall: message=" + message);
		Customerorders customerorders = (Customerorders)unmarshallerCustomerOrder.unmarshal(new StringReader(message));
		//LOG.info("unmarshall: customerorders=" + customerorders.getCustomerorder().toString() + " customerorders.getCustomer().getName()=" + customerorders.getCustomerorder().getCustomer().getName() );
		return customerorders;
	}
}
