import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {
	private String description;
	private String gtin;
	public Product(String description, String gtin, String price,
			String supplier) {
		super();
		this.description = description;
		this.gtin = gtin;
		this.price = price;
		this.supplier = supplier;
	}
	public Product() {
		super();
	}
	private String price;
	private String supplier;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Product [description=" + description + ", gtin=" + gtin
				+ ", price=" + price + ", supplier=" + supplier + "]" + "\n";
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
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

}
