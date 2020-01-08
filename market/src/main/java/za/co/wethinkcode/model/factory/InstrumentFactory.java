package za.co.wethinkcode.model.factory;

import za.co.wethinkcode.model.product.Product;
import za.co.wethinkcode.model.instruments.Item;
import za.co.wethinkcode.model.instruments.details.InstrumentDetails;

// Product factory
public abstract class InstrumentFactory {
	public static Product newInstrument(String name, int price, int qty) {
		return new Item(name, new InstrumentDetails(price, qty));
	}
}
