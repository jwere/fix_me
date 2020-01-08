package za.co.wethinkcode.model.market;

import za.co.wethinkcode.model.instruments.details.InstrumentDetails;
import za.co.wethinkcode.model.Decoder;
import za.co.wethinkcode.model.FixMessages;

public class MarketTower extends Tower {

	// rejecting or accepting an order
	public InstrumentDetails processOrder() throws NullPointerException {
		Decoder decode = new Decoder(this._order);
		InstrumentDetails dets = new InstrumentDetails(Integer.parseInt(decode.getPrice()), Integer.parseInt(decode.getQuantity()));
		if (this._itemExists(decode.getProduct().toLowerCase())) {
			dets.setName(decode.getProduct());
			dets.setType(decode.getBuyorSell());
		}
		else {
			dets.setName("");
			dets.setType("");
		}
		return dets;
	}

	// return fixedmsg
	public String updatedProducts(String order) throws NullPointerException {
		String status = "8";
		this._order = order;
		_detailsChanged();
		FixMessages fix = new FixMessages();
		if (this._transactionDone()) status = "2";
		return fix.responceFromMarket(order, status);
	}
}
