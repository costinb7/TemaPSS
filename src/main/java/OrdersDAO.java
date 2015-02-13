import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class OrdersDAO {
	private static final Logger log = Logger.getLogger(MainApp.class.getName());
	private final String ORDERS_XML;
	private Orders orders;

	public OrdersDAO(String oRDERS_XML) {
		super();
		ORDERS_XML = oRDERS_XML;
	}

	/**
	A test method that creates some orders
	*/
	public void testCreateOrders() throws JAXBException{
		Product product1 =  new Product("a", "b", "c", "d");
		Product product2 =  new Product("as", "fb", "cf", "dg");
		ArrayList<Product> productList = new ArrayList<Product>();
		productList.add(product1);
		productList.add(product2);
		Order order1 = new Order();
		order1.setProductList(productList);
		order1.setCreated("Aaaa");
		order1.setID("ID1");
		ArrayList<Order> orderList = new ArrayList<Order>();
		orderList.add(order1);
		Orders orders1 = new Orders();
		orders1.setOrderList(orderList);

		// create JAXB context and instantiate marshaller
	    JAXBContext context = JAXBContext.newInstance(Orders.class);
	    Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

	    // Write to System.out
	    m.marshal(orders1, System.out);
	    m.marshal(orders1, new File(ORDERS_XML));

	}

	/**
	Read orders from the xml file
	*/
	public void readOrders() throws JAXBException, FileNotFoundException{
		JAXBContext context = JAXBContext.newInstance(Orders.class);
		Unmarshaller um = context.createUnmarshaller();
	    orders = (Orders) um.unmarshal(new FileReader(ORDERS_XML));
	    log.fine("Information red from file " + ORDERS_XML + " :\n" + orders.toString());
	}
}
