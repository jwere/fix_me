package za.co.wethinkcode.model.market;

import za.co.wethinkcode.model.product.Product;

import java.util.ArrayList;

public abstract class Tower {
	private ArrayList<Product> _products = new ArrayList<Product>();
	protected String _order;

	public void register(Product product) throws NullPointerException {
		this._products.add(product);
		return ;
	}

	public void unregister(Product product) throws NullPointerException {
		this._products.remove(product);
	}

	protected boolean _itemExists(String item) throws NullPointerException {
		for (Product _item: _products) {
			if (_item.instrumentName().toLowerCase().equals(item)) return true;
		}
		return false;
	}

	protected boolean _transactionDone() throws NullPointerException {
		for (Product _item: _products) {
			if (_item.itemWasBought()) return true;
		}
		return false;
	}

	protected void _detailsChanged() throws NullPointerException {
		for (int i = 0; i < _products.size(); i += 1)
		_products.get(i).updateInstrumentDetails();
		return ;
	}
}
