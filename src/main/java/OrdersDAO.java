import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class OrdersDAO {
	public static final String outputFolderName = "results";
	
	private static final Logger log = Logger.getLogger(MainApp.class.getName());
	private final String ORDERS_XML;
	private Orders orders;
	private Map<String, Products> mapOrders = new HashMap<String, Products>();

	public OrdersDAO(String oRDERS_XML) {
		super();
		ORDERS_XML = oRDERS_XML;
	}

	/**
	A test method that creates some orders
	*/
	public void testCreateOrders() throws JAXBException{
		Product product1 =  new Product("a", "b", new Price("usd", "100"), "d");
		Product product2 =  new Product("as", "fb", new Price("usd", "103"), "dg");
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
	
	/**
	A test method that creates some products
	*/
	public void testCreateProducts() throws JAXBException{
		OutputProduct product1 =  new OutputProduct("a", "b", new Price("usd", "160"), "d");
		OutputProduct product2 =  new OutputProduct("as", "fb", new Price("usd", "109"), "dg");
		ArrayList<OutputProduct> productList = new ArrayList<OutputProduct>();
		productList.add(product1);
		productList.add(product2);
		Products products = new Products();
		products.setProductList(productList);

		// create JAXB context and instantiate marshaller
	    JAXBContext context = JAXBContext.newInstance(Products.class);
	    Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

	    // Write to System.out
	    m.marshal(products, System.out);
	}
	
	public void transformFromProductsToOutputProducts(){
		for (Order order: orders.getOrderList()){
			for (Product product: order.getProductList()){
				if (!mapOrders.containsKey(product.getSupplier())) {
					OutputProduct newProduct = new OutputProduct(product.getDescription(),
							product.getGtin(), product.getPrice(), order.getID());
					Products products = new Products();
					products.add(newProduct);
					mapOrders.put(product.getSupplier(), products);
				}
				else {
					Products products = mapOrders.get(product.getSupplier());
					OutputProduct newProduct = new OutputProduct(product.getDescription(),
							product.getGtin(), product.getPrice(), order.getID());
					products.add(newProduct);
				}
			}
		}
	}
	
	private void createOuputFolder(){
		File outputFolder = new File(outputFolderName);
		if (!outputFolder.exists()) {
		    log.info("creating directory: " + outputFolderName);
		    outputFolder.mkdir();
		}
	}
	
	public void writeProducts(String fileIndex) throws JAXBException{
		// create JAXB context and instantiate marshaller
	    JAXBContext context = JAXBContext.newInstance(Products.class);
	    Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

	    // Write to System.out
	    createOuputFolder();
	    for (String key: mapOrders.keySet()){
	    	String fileName = key + fileIndex + ".xml";
	    	m.marshal(mapOrders.get(key), new File(outputFolderName + File.separator + fileName));
	    }
	    
	}
}
