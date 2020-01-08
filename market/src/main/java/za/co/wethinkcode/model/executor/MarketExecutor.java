package za.co.wethinkcode.model.executor;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;

import java.util.NoSuchElementException;
import java.util.Scanner;

import za.co.wethinkcode.core.App;

import za.co.wethinkcode.model.product.Product;
import za.co.wethinkcode.model.ConsoleDecorator;

public class MarketExecutor {


	private static MarketExecutor _market = new MarketExecutor();

	private String _serverAddress = "127.0.0.1";
	private int _PORT = 5001;

	private Socket _socket;
	private Scanner _in;
	private PrintWriter _out;
	private int _marketId;
	
	public MarketExecutor() throws NullPointerException {
		return ;
	}
	
	public static MarketExecutor getMarket() throws NullPointerException {
		return _market;
	}

	public ConsoleDecorator _td = new ConsoleDecorator();

	private void getMarketId() throws NullPointerException {
		String line = _in.nextLine();
		System.out.println(line);
		this._marketId = Integer.parseInt(line.substring((line.length() - 6),line.length()));
		return ;
	}

	private void marketStock() throws NullPointerException {

		String stock = "[" + "co:blue" + "MARKET" + "co:reset" + " : " + _marketId + "] Available Stock";

		for (Product product: App.products) {
			stock += ("|" + product );
		}

		stock += "|";
		_out.println(stock);

		return ;
	}



	public void run() throws IOException, NoSuchElementException, NullPointerException {
		try {
			this._socket = new Socket(_serverAddress, _PORT);
			this._in = new Scanner(_socket.getInputStream());
			this._out = new PrintWriter(_socket.getOutputStream(), true);
			String input;
			String responce;
			getMarketId();
			// send market products to brokers for trading.
			marketStock();
			while (true) {
				while (_in.hasNextLine()) {
					input = _in.nextLine();
					_td.displayFixMessage(input);
					responce = App.tower.updatedProducts(input);
					_out.println(responce);
					_td.displayFixMessage(responce);
					marketStock();
				}
			}
		}
		finally {}
	}
}
