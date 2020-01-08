package za.co.wethinkcode.core;

import za.co.wethinkcode.model.FixMessageDatabase;
import za.co.wethinkcode.model.Router;

import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * Router App
 *
 */
public class App {
//  public FixMessageDatabase fixMessageDatabase;
  static Router router;

  public static void main(String[] args) {
    try {
      router = new Router();
    }
    catch (IOException | SQLException e)
    {
      System.out.println(router._td.viewMessage("Something went wrong...","error"));
    }
    catch (NoSuchElementException e)
    {
      System.out.println(router._td.viewMessage("Broker | Market Disconnection Detected!","error"));
    }
    catch (NullPointerException e) {
			System.out.println("");
    }
  }
}
