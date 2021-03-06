package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import entities.Order;
import entities.Orders;
import entities.OutputProduct;
import entities.Price;
import entities.Product;
import entities.Products;


public class OrdersDAO {
	public static final String outputFolderName = "results";
	
	private static final Logger log = Logger.getLogger(MainApp.class.getName());
	private final String orders_xml;
	private Orders orders;
	private Map<String, Products> mapOrders = new HashMap<String, Products>();

	public OrdersDAO(String orders_xml) {
		super();
		this.orders_xml = orders_xml;
	}

	/**
	Read orders from the xml file
	*/
	public void readOrders() throws JAXBException, FileNotFoundException{
		JAXBContext context = JAXBContext.newInstance(Orders.class);
		Unmarshaller um = context.createUnmarshaller();
	    orders = (Orders) um.unmarshal(new FileReader(orders_xml));
	    log.fine("Informations red from file " + orders_xml + " :\n" + orders.toString());
	}
	
	/**
	Transform from Products to OutputProducts
	*/
	public void transformFromProductsToOutputProducts(){
		for (Order order: orders.getOrderList()){
			for (Product product: order.getProductList()){
				if (!mapOrders.containsKey(product.getSupplier())) {
					OutputProduct newProduct = new OutputProduct(product.getDescription(),
							product.getGtin(), order.getCreated(), product.getPrice(), order.getID());
					Products products = new Products();
					products.add(newProduct);
					mapOrders.put(product.getSupplier(), products);
				}
				else {
					Products products = mapOrders.get(product.getSupplier());
					OutputProduct newProduct = new OutputProduct(product.getDescription(),
							product.getGtin(), order.getCreated(), product.getPrice(), order.getID());
					products.add(newProduct);
				}
			}
		}
	}
	
	/**
	Creates the output folders if it doesn't exist
	*/
	private void createOuputFolder(){
		File outputFolder = new File(outputFolderName);
		if (!outputFolder.exists()) {
		    log.info("creating directory: " + outputFolderName);
		    outputFolder.mkdir();
		}
	}
	
	/**
	Sort the products
	*/
	private void sort(Products products){
		Collections.sort(products.getProductList(), new Comparator<OutputProduct>() {
	        @Override public int compare(OutputProduct p1, OutputProduct p2) {
	        	if (p1.getTimestamp() !=  p2.getTimestamp())
	        		return p2.getTimestamp().compareTo(p1.getTimestamp());
	        	else 
	        		return  (int) (Float.parseFloat(p2.getPrice().getPrice()) -  Float.parseFloat(p1.getPrice().getPrice()));
	        		
	        }
		});
	}
	
	/**
	Write the result products to the xml files
	*/
	public void writeProducts(String fileIndex) throws JAXBException{
		// create JAXB context and instantiate marshaller
	    JAXBContext context = JAXBContext.newInstance(Products.class);
	    Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

	    createOuputFolder();
	    for (String key: mapOrders.keySet()){
	    	String fileName = key + fileIndex + ".xml";
	    	sort(mapOrders.get(key));
	    	String resultFile = outputFolderName + File.separator + fileName;
	    	log.info("Create result file: " + resultFile);
	    	m.marshal(mapOrders.get(key), new File(resultFile));
	    }
	}
	
	/***********************************************************************
	 * Test methods
	***********************************************************************/
	
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
	A test method that creates some products
	*/
	public void testCreateProducts() throws JAXBException{
		OutputProduct product1 =  new OutputProduct("a", "b", "1", new Price("usd", "160"), "d");
		OutputProduct product2 =  new OutputProduct("as", "fb", "2", new Price("usd", "109"), "dg");
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
}
