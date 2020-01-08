package za.co.wethinkcode.model;

import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.util.Arrays;

public class ConsoleDecorator {
    public String blue = "co:blue";
    public String green = "co:green";
    public String grey = "co:grey";
    public String purple = "co:purple";
    public String red = "co:red";
    public String teal = "co:teal";
    public String orange = "co:orange";
    public String reset = "co:reset";

    public String viewMessage(String message, String type) throws NullPointerException {

        green    = "\033[1;38;2;161;221;112m";
        grey     = "\033[1;38;2;120;120;120m";
        red      = "\033[1;38;2;199;12;58m";
        blue     = "\033[1;38;2;40;150;150m";
        purple   = "\033[1;38;2;133;89;165m";
        teal     = "\033[1;38;2;0;189;170m";
        orange   = "\033[1;38;2;244;89;4m";
        reset    = "\u001B[0m";

        String text;
        String mesType = "";
        String replaceString = message;

        if (type.toLowerCase().contains("error") || type.toLowerCase().contains("normal"))
            mesType = (type.toLowerCase().contains("error") ? ("[" + red + "ERROR" + reset + "] ") : ("[" + teal + "FIX-ME" + reset + "] "));

        replaceString = replaceString.replace("co:blue", blue);
        replaceString = replaceString.replace("co:grey", grey);
        replaceString = replaceString.replace("co:green", green);
        replaceString = replaceString.replace("co:purple", purple);
        replaceString = replaceString.replace("co:red", red);
        replaceString = replaceString.replace("co:reset", reset);


        text = mesType + replaceString;

        return text;

    }

    public void displayFixMessage(String message) throws NullPointerException {

        if (message.contains("8=FIX.4.2")) {

            String receiverValue = message.substring((message.indexOf("56=") + 3), (message.indexOf("56=") + 3 + 6));
            String senderValue = message.substring((message.indexOf("49=") + 3), (message.indexOf("49=") + 3 + 6));

            if (message.contains("39=")) {
                System.out.println(this.viewMessage("[" + purple + "BROKER" + reset + " : " + receiverValue + "] < [" + blue + "MARKET" + reset + " : " + senderValue + "] " + message, "none"));
                char fulfilled = message.charAt(message.indexOf("39=") + 3);

                if (fulfilled == '2')
                    System.out.println(this.viewMessage("[" + purple + "BROKER" + reset + " : " + receiverValue + "] < [" + blue + "MARKET" + reset + " : " + senderValue + "] request has been " + green + "ACCEPTED" + reset, "none"));
                if (fulfilled == '8')
                    System.out.println(this.viewMessage("[" + purple + "BROKER" + reset + " : " + receiverValue + "] < [" + blue + "MARKET" + reset + " : " + senderValue + "] request has been " + red + "REJECTED" + reset, "none"));
            } else {
                System.out.println(this.viewMessage("[" + purple + "BROKER" + reset + " : " + senderValue + "] > [" + blue + "MARKET" + reset + " : " + receiverValue + "] " + message, "none"));
            }
        }
        else if (message.contains("] Available Stock"))
            this.makeTable(message);
        else
            System.out.println(this.viewMessage(message, "none"));
    }

    private void makeTable(String message) throws NullPointerException {

        String[] products = message.split("[|]+");

        System.out.println(this.viewMessage(products[0], "none"));

        products = Arrays.copyOfRange(products, 1, products.length);

        String formTable = "";
        String leftAlignFormat = "  %-20s   %-10s   %-10s  %n";
        String background = "\033[1;48;2;40;150;150m";

        formTable += background + "  Item                 " + reset + " " + background + " Amount (R) " + reset + " " + background +  " Quantity    " + reset + "\n";

        for (String product: products) {
            String[] props = product.split("[/]+");

            formTable += String.format(leftAlignFormat, props[0], props[1], props[2] );
        }

        System.out.println(this.viewMessage(formTable, "none"));
    }
}
