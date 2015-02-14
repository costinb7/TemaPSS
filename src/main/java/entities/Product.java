package entities;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {
	private String description;
	private String gtin;
	private Price price;
	private String supplier;

	public Product() {
	}
	
	public Product(String description, String gtin, Price price,
			String supplier) {
		this.description = description;
		this.gtin = gtin;
		this.price = price;
		this.supplier = supplier;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getGtin() {
		return gtin;
	}
	
	public void setGtin(String gtin) {
		this.gtin = gtin;
	}
	
	public Price getPrice() {
		return price;
	}
	
	public void setPrice(Price price) {
		this.price = price;
	}
	
	public String getSupplier() {
		return supplier;
	}
	
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	
	@Override
	public String toString() {
		return "Product [description=" + description + ", gtin=" + gtin
				+ ", price=" + price + ", supplier=" + supplier + "]" + "\n";
	}
}
