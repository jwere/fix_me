package za.co.wethinkcode.model;

import java.util.ArrayList;
import java.util.List;

public class Decoder {

    String fixedMessage;
    List<String> subDivder = new ArrayList<String>();

    public Decoder(String msg) throws NullPointerException {
        fixedMessage = msg;
        String[] subFixMessage = msg.split("\\|");
        for (int i = 0; i < subFixMessage.length; i++){
            subDivder.add(subFixMessage[i]);
        }
    }

    // deteremine a buyer/seller method
    public String getSenderID() throws NullPointerException {

        for (int i = 0; i < subDivder.size(); i++)
        {
            String [] res = subDivder.get(i).split("=");
            if (res[0].equals("49"))
                return res[1];
        }
        return null;
    }

    // determine sender and reciever id
    public String getReciverID() throws NullPointerException {
        for (int i = 0; i < subDivder.size(); i++)
        {
            String[] res = subDivder.get(i).split("=");
            if (res[0].equals("56"))
                return res[1];
        }
        return null;
    }

    // determine whether we are selling or buying
    public String getBuyorSell() throws NullPointerException {
        for (int i = 0; i < subDivder.size(); i++)
        {
            String[] res = subDivder.get(i).split("=");
            if (res[0].equals("54"))
                return res[1];
        }
        return null;
    }


    // gives the value of the tag 460, that is the product
    public String getProduct() throws NullPointerException {
        for (int i = 0; i < subDivder.size(); i++)
        {
            String[] res = subDivder.get(i).split("=");
            if (res[0].equals("460"))
                return res[1];
        }
        return null;
    }

    // gives the value of the tag 53, that is the value of the number of products
    public String getQuantity() throws NullPointerException {
        for (int i = 0; i < subDivder.size(); i++)
        {
            String[] res = subDivder.get(i).split("=");
            if (res[0].equals("53"))
                return res[1];
        }
        return null;
    }

    // determine afordable price 
    public String getPrice() throws NullPointerException {
        for (int i = 0; i < subDivder.size(); i++) {
            String[] res = subDivder.get(i).split("=");
            if (res[0].equals("44"))
                return res[1];
        }
        return null;
    }
    
    // determine the market
    // determine success status
}
