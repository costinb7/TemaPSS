import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;


@XmlRootElement(name = "price")
@XmlAccessorType(XmlAccessType.FIELD)
public class Price {
	@XmlAttribute
	private String currency;
	@XmlValue
	private String price;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPrice() {
		return price;
	}
	public Price(String currency, String price) {
		super();
		this.currency = currency;
		this.price = price;
	}
	public Price() {
		super();
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Price [currency=" + currency + ", price=" + price + "]";
	}
	
}
