package za.co.wethinkcode.model;

import java.util.List;

public class Encoder {

    private     String  fixMessage = "";
    private     String  beginString = "8=FIX.4.2|";
    private     String  bodyString = "";
    private     int     asciiValue = 0;
    private     Integer totalAsciiValue = 0;
    private     Integer checkSumValue = 0;
    private     String  checkSumString = "";


    public Encoder() throws NullPointerException { return ; }

    public String MessageEncoder(List<String> fixList, String msgType) throws NullPointerException {
        fixMessage ="";
        bodyString = "";
        checkSumString = "";
        FixBodybuilder(fixList,msgType);
        buildFixHead(msgType,bodyString);
        buildCheckSum(bodyString);
        fixMessage = fixMessage + beginString + bodyString + checkSumString;

        
        return fixMessage;
    }

    private void FixBodybuilder(List<String> fixList, String msgType) throws NullPointerException {
        bodyString += ""+ "|49=" + fixList.get(0);
        bodyString += "|56=" + fixList.get(1);
        bodyString += "|44=" + fixList.get(2);
        bodyString += "|54=" + fixList.get(3);
        bodyString += "|460=" + fixList.get(4);
        bodyString += "|53=" + fixList.get(5) + "|";

    }

    public String FixBodyResponse(List<String> fixList, String msgType) throws NullPointerException {
        fixMessage ="";
        bodyString = "";
        checkSumString = "";
        bodyString += "|49=" + fixList.get(0);
        bodyString += "|56=" + fixList.get(1);
        bodyString += "|44=" + fixList.get(2);
        bodyString += "|460=" + fixList.get(3);
        bodyString += "|53=" + fixList.get(4);
        bodyString += "|39=" + fixList.get(5) + "|";
        buildFixHead(msgType,bodyString);
        buildCheckSum(bodyString);
        fixMessage = fixMessage + beginString + bodyString + checkSumString;
        return fixMessage;
    }

    private void buildFixHead(String msgType, String bodyString) throws NullPointerException {
        beginString = beginString + "9=" + Integer.toString(bodyLength(bodyString)) + "|35=" + msgType;
    }

    private int bodyLength(String bodyString) throws NullPointerException {
        return (bodyString.length());
    }

    // You need to sum every byte in the message up to but not including the checksum field.
    // Then take this number modulo 256, and print it as a number of
    // 3 characters with leading zeroes (e.g. checksum=13 would become 013).
    public void buildCheckSum(String bodyString) throws NullPointerException {
        for (int i = 0; i < bodyString.length(); i++){
            char c = bodyString.charAt(i);
            asciiValue = (int)c;
            totalAsciiValue = totalAsciiValue + asciiValue;
        }
        checkSumValue = totalAsciiValue % 256;
        String checkDigits = Integer.toString(checkSumValue);
        while(checkDigits.length() < 3)
        {
            checkDigits = "0" + checkDigits;
        }
        checkSumString = checkSumString + "10="+checkDigits+ "|";
    }
}
