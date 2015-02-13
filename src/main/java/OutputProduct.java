import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class OutputProduct {
	private String description;
	private String gtin;
	public OutputProduct(String description, String gtin, String price,
			String orderid) {
		super();
		this.description = description;
		this.gtin = gtin;
		this.price = price;
		this.orderid = orderid;
	}
	public OutputProduct() {
		super();
	}
	private String price;
	private String orderid;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Product [description=" + description + ", gtin=" + gtin
				+ ", price=" + price + ", orderid=" + orderid + "]" + "\n";
	}
	public String getGtin() {
		return gtin;
	}
	public void setGtin(String gtin) {
		this.gtin = gtin;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

}
