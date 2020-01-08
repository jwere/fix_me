package za.co.wethinkcode.model.instruments;

import za.co.wethinkcode.model.market.MarketTower;
import za.co.wethinkcode.model.product.Product;
import za.co.wethinkcode.model.instruments.details.InstrumentDetails;

public class Item extends Instrument implements Product {
	private MarketTower _marketTower;

	public Item(String name, InstrumentDetails details) throws NullPointerException {
		super(name, details);
		return ;
	}

	public void updateInstrumentDetails() throws NullPointerException {
		InstrumentDetails dets = _marketTower.processOrder();
		if (!(this._name.toLowerCase().equals(dets.getName())))
			return ;
		if (dets.getType().equals("1")) {
			if ((dets.getPrice() >= this._details.getPrice()) && (dets.getQty() <= this._details.getQty())) {
					this._details = new InstrumentDetails(this._details.getPrice(), this._details.getQty() - dets.getQty());
					this._bought = true;
					return ;
			}
		} else {
			if ((dets.getPrice() >= this._details.getPrice()) && (dets.getQty() >= 1)) {
				this._details = new InstrumentDetails(this._details.getPrice(), this._details.getQty() + dets.getQty());
				this._bought = true;
				return ;
		}
		}
	}

	@Override
	public void registerMarket(MarketTower marketTower) throws NullPointerException {
		this._marketTower = marketTower;
		this._marketTower.register(this);
		return ;
	}

	@Override
	public String toString() throws NullPointerException {
		return this._name + "/" + _details.getPrice() + "/" + this._details.getQty();
	}

	@Override
	// If iteam was bought set back to false 
	public boolean itemWasBought() throws NullPointerException {
		if (this._bought) {
			this._bought = false;
			return true;
		}
		return false;
	}
	@Override
	public String instrumentName() throws NullPointerException { return this._name; }

}


