package za.co.wethinkcode.core;

public class StockException extends Exception {
	public StockException(String reason, String statement) {
		super(reason + ": " + statement);
		return ;
	}

	public StockException(String reason, String statement, Throwable cause) {
		super(reason + ": " + statement, cause);
		return ;
	}
}
