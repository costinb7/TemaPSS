import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {
	@Override
	public String toString() {
		return "Order [productList=" + productList + ", created=" + created
				+ ", ID=" + ID + "]" + "\n";
	}

	@XmlElement(name = "product")
	private ArrayList<Product> productList;

	@XmlAttribute
	private String created;

	@XmlAttribute(name = "ID")
	private String ID;

	public Order() {
		super();
	}

	public ArrayList<Product> getProductList() {
		return productList;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void setProductList(ArrayList<Product> productList) {
		this.productList = productList;
	}
}
