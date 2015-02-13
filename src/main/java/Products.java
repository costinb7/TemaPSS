import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class Products {
	@XmlElement(name = "product")
	private ArrayList<OutputProduct> productList = new ArrayList<OutputProduct>();

	public Products() {
		super();
	}
	
	public void add(OutputProduct product){
		productList.add(product);
	}

	public ArrayList<OutputProduct> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<OutputProduct> productList) {
		this.productList = productList;
	}

	@Override
	public String toString() {
		return "Products [productList=\n" + productList + "]";
	}
}
