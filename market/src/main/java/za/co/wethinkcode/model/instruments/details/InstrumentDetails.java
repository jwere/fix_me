package za.co.wethinkcode.model.instruments.details;

public class InstrumentDetails {
	private int _price;
	private int _qty;
	private String _name;
	private String _type;

	public InstrumentDetails(int price, int qty) {
		this._price = price > 1 ? price : 1; // can not have a price less than R1. please change type to float
		this._qty = qty >= 0 ? qty : 0;
		return ;
	}

	public int getPrice() { return this._price; }

	public int getQty() { return this._qty; }

	public void setName(String name) {
		this._name = name.toLowerCase();
	}

	public void setType(String type) {
		this._type = type.toLowerCase();
	}

	public String getName() { return this._name; }

	public String getType() { return this._type; }
}
