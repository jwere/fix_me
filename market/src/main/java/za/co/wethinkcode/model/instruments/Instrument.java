package za.co.wethinkcode.model.instruments;

import za.co.wethinkcode.model.instruments.details.InstrumentDetails;

public abstract class Instrument {
	protected long _id;
	protected String _name;
	protected InstrumentDetails _details;
	private static long _idGenerated = 0;
	protected boolean _bought = false;

	protected Instrument(String name, InstrumentDetails details) throws NullPointerException {
		this._name = name;
		this._details = details;
		this._id = _nextId();
		return ;
	}
	
	protected long _nextId() {
		return Instrument._idGenerated += 1;
	}
}
