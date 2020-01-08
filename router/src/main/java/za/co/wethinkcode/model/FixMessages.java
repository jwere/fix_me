package za.co.wethinkcode.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

import java.io.PrintWriter;

public class FixMessages {

    Encoder FixedMessage1 = new Encoder();
    int id;
    Scanner _in;
    PrintWriter _out;
    
    List<String> fixList;
    String fixed = "";

    public FixMessages() {}

    public FixMessages (int id, Scanner _in, PrintWriter _out) throws NullPointerException {
        this.id = id;
        this._in = _in;
        this._out = _out;
    }

    public String responceFromMarket (String fixedMsg, String status) throws NullPointerException {
        fixList = new ArrayList<String>();
        Decoder decode = new Decoder(fixedMsg);
        fixList.add(decode.getReciverID());
        fixList.add(decode.getSenderID());
        fixList.add(decode.getPrice());
        fixList.add(decode.getProduct());
        fixList.add(decode.getQuantity());
        fixList.add(status);
        
        return FixedMessage1.FixBodyResponse(fixList,"D");
    }

    public String buyOrSell() throws NullPointerException,  IOException {
        fixList = new ArrayList<String>();
        try {
            while (true) {
                _out.println("Do you wish to Buy Or Sell?.");
                String line = _in.nextLine();

                // broker chooses between buy or sell
                while (true) {
                    _out.println(line);
                    if (line.toLowerCase().equals("buy") || line.toLowerCase().equals("sell"))
                        break ;
                    _out.println("Do you wish to Buy Or Sell?.");
                    line = _in.nextLine();
                }

                if (line.toLowerCase().equals("buy")) {
                    fixList.add(String.valueOf(id));
                    while (true) {
                        _out.println("Select market ID ");
                        line = _in.nextLine();
                        _out.println(line);
                        try {
                            Integer.parseInt(line);
                            break ;
                        } catch (NumberFormatException VariableDeclaratorId) {
                            _out.println("Invalid ID format.");
                        }
                    }
                    fixList.add(line);
                    while(true) {
                        _out.println("Amount you are willing to spend: ");
                        line = _in.nextLine();
                        _out.println(line);
                        try {
                            Integer.parseInt(line);
                            break ;
                        } catch (NumberFormatException VariableDeclaratorId) {
                            _out.println("Invalid price format.");
                        }
                        catch (NullPointerException e) {
                            System.out.println("");
                        }
                    }
                    fixList.add(line);
                    // status to buy
                    fixList.add("1");
                    _out.println("Item you would like to purchace.");
                    line =_in.nextLine();
                    _out.println(line);
                    fixList.add(line);
                    while(true) {
                        _out.println("Item quantity ");
                        line = _in.nextLine();
                        _out.println(line);
                        try {
                            Integer.parseInt(line);
                            break;
                        } catch(NumberFormatException VariableDeclaratorId) {
                            _out.println("Invalid unit");
                        }
                        catch (NullPointerException e) {
                            System.out.println("");
                        }
                        
                    }
                    fixList.add(line);
                    for(int i =0; i < fixList.size(); i++) {
                        fixed += ""+fixList.get(i) + "|";
                    }
                }
                else if (line.toLowerCase().equals("sell")) {
                    fixList.add(String.valueOf(id));
                    while (true) {
                        _out.println("Select the market ID ");
                        line = _in.nextLine();
                        _out.println(line);
                        try {
                            Integer.parseInt(line);
                            break ;
                        } catch (NumberFormatException VariableDeclaratorId) {
                            _out.println("Invalid ID format.");
                        }
                        catch (NullPointerException e) {
                            System.out.println("");
                        }
                    }
                    fixList.add(line);
                    while(true) {
                        _out.println("Enter your selling price: ");
                        line = _in.nextLine();
                        _out.println(line);
                        try {
                            Integer.parseInt(line);
                            break;
                        } catch(NumberFormatException VariableDeclaratorId){
                            _out.println("Invalid price format.");
                        }
                        catch (NullPointerException e) {
                            System.out.println("");
                        }
                    }
                    fixList.add(line);
                    fixList.add("2");
                    _out.println("Enter the available item you wish to sell ");
                    line =_in.nextLine();
                    _out.println(line);
                    fixList.add(line);
                    while(true) {
                        _out.println("Enter item quantity desired to sell");
                        line = _in.nextLine();
                        _out.println(line);
                        try {
                            Integer.parseInt(line);
                            break;
                        } catch(NumberFormatException VariableDeclaratorId){
                            System.out.println("Invalid unit");
                        }
                    }
                    fixList.add(line);
                    for(int i =0; i < fixList.size(); i++) {
                        fixed += ""+fixList.get(i) + "^";
                    }
                }
                return FixedMessage1.MessageEncoder(fixList,"D");
            }
        } finally {}
    }
}

//_______________________________________________________________________________BREAKDOWN OF FIX-ME'S USE OF FIX MESSAGE______________________________________________________________________________
//
// The BROKER will make a BUY or SELL request
// From this a New Order Single (35=D) Fix Message is generated.
//
//     eg:
//     [Market ID] buy [Instrument] [Quantity]
//     8=FIX.4.0|9=[bodylength]|35=D|49=[Broker ID]|56=[Market ID]|34=1|52=[TIMESTAMPP]|11=[Client ORDER ID]|21=1|55=[Instrument]|54=1(buy)|38=[Quantity]|40=1|10=[checksum]|
//    |                                    HEADER                                      |                               BODY                                   |   TRAILER   |
//
//     8=FIX.4.0|9=[bodylength]|35=D|49=[Broker ID]|56=[Market ID]|34=1|52=[TIMESTAMPP]|11=[Client ORDER ID]|21=1|55=[Instrument]|54=2(sell)|38=[Quantity]|40=1|10=[checksum]|
//    |                                    HEADER                                      |                               BODY                                    |   TRAILER   |
//
// This Fix message is then sent to the market and "decoded".
// the MARKET then checks the [Instrument] exists and if the [Quantity] is applicable or not for that Instrument.
// From this an Execution Report (35= 8) Fix Message is generated that has the Order Status (39) set to 2 (Filled/Executed) or 8 (rejected)
//
//     eg:
//     Rejected
//      8=FIX.4.0|9=[bodylength]|35=8|49=[Market ID]|56=[Broker ID]|34=1|52=[TIMESTAMPP]|37=[ORDER ID]|17=0|20=3|39=8|55=[Instrument]|54=1(buy)|38=[Quantity]|14=0|6=0|10=[checksum]|
//     |                                    HEADER                                      |                                      BODY                                   |   TRAILER   |
//
//     Accepted
//
//     8=FIX.4.0|9=[bodylength]|35=8|49=[Market ID]|56=[Broker ID]|34=1|52=[TIMESTAMPP]|37=[ORDER ID]|17=0|20=3|39=8|55=[Instrument]|54=1(buy)|38=[Quantity]|14=[Quantity]|6=[Price]|10=[checksum]|
//    |                                    HEADER                                      |                                              BODY                                          |   TRAILER   |
//
//  Finally this Fix Message is sent back to the router/broker where it is decoded again to interpret the final result - Accepted or Rejected
//
//_______________________________________________________________________________________BREAKDOWN OF FIX MESSAGE_______________________________________________________________________________________
//
// Reference - https://www.slideshare.net/aiQUANT/fix-protocol-overview-1697394
//    FIX Message Format
//    • MESSAGE
//    • Message contains 3 parts:
//        – Header
//        – Body
//        – Trailer/Footer
//    • Message is a collection of fields
//    • Each field is a tag-value pair – <tag>=<Value> – Eg: 55=IBM (symbol=IBM)
//
//    • Delimiter
//        - fields are terminated by delimiter character
//        - character is a non-printing ASCII - "SOH" (#001 or ) (in print ‘^’ or '|' is used)
//        - Delimiter cannot be used in the message anywhere else except in DATA field
//
//______________________________________________________________________________________< Standard Message Header >______________________________________________________________________________________
//
// Reference - https://btobits.com/fixopaedia/fixdic40/index.html?block_Standard_Message_Header.html
// Reference - https://en.wikipedia.org/wiki/Financial_Information_eXchange
// Standard Message Header
//
//    • Each message, administrative or application, is preceded by a standard header.
//    • The header is used to identify the message type, length, destination, sequence number, origination point and time.
//
//    • The header contains five mandatory fields and one optional field (marked with '>' below)
//        - 8 (BeginString), 9 (BodyLength), 35 (MsgType), 49 (SenderCompID), 56 (TargetCompID), - optional 1128 (ApplVerID - if present must be in 6th position)
//
//    • BeginString (Tag 8)
//        - All messages start with "8=FIX.x.y"
//        – Indicates the FIX version of the message being transmitted
//        – Useful to support multiple versions
//
//    • Body length (Tag 9)
//        - Body length is the character count starting at tag 35 (included) all the way to tag 10 (excluded). SOH delimiters do count in body length.
//            For Example: (SOH have been replaced by'|')
//                8=FIX.4.2|9=65|35=A|49=SERVER|56=CLIENT|34=177|52=20090107-18:15:16|98=0|108=30|10=062|
//                0   + 0  + 5  +   10    +   10    +  7   +        21          + 5  +  7   +   0    = 65
//                Has a Body length of 65.
//
//    Reference - https://btobits.com/fixopaedia/fixdic40/index.html
//    • Message Type Codes (Tag 35)
//        - The content in the body of the message is specified by (tag 35, MsgType) message type defined in the header.
//            - Session Messages By MsgType
//                - Message Value 0-5 or A
//            - Application Messages By MsgType
//                - Message Value 6-9 or B-S
//                - The message values / body we will be working with are
//                    >	- D = Order - Single (Buy and Sell)
//                    >	- 8 = Execution Report Cancel/Replace Request
//
//    • Additional Information
//    • Resending Messages - The PossDupFlag (Tag 43) and The PossResend (Tag 97)
//        - Two fields are provided for use when resending messages.
//            - The PossDupFlag (43) is set when resending a message as the result of a session level event (i.e. the retransmission of a message reusing a sequence number).
//                - PossDup - if a message with this sequence number has been previously received, ignore message, if not, process normally.
//            - The PossResend (97) is set when reissuing a message with a new sequence number (e.g. resending an order). The receiving application should process these messages as follows:
//                - PossResend - forward message to application and determine if previously received (i.e. verify order id and parameters)
//
//    • Message Format
//        TAG 	FIELD NAME  		FIXML	REQ'D COMMENTS
//    >	    8		BeginString			Y		FIX.4.0 (Always unencrypted, must be first field in message)
//    >	    9		BodyLength			Y		(Always unencrypted, must be second field in message)
//    >	   35		MsgType				Y		(Always unencrypted, must be third field in message)
//    >	   49		SenderCompID		Y		(Always unencrypted) - VENDOR (unique id of the sender firm)
//    >	   56		TargetCompID		Y		(Always unencrypted) - BROKER (value used to identify receiving firm)
//        115		OnBehalfOfCompID	N		Trading partner company ID used when sending messages via a third party (Can be embedded within encrypted data section.)
//        128		DeliverToCompID		N		Trading partner company ID used when sending messages via a third party (Can be embedded within encrypted data section.)
//         90		SecureDataLen		C		Required to identify length of encrypted section of message. (Always unencrypted)
//         91		SecureData			C		Required when message body is encrypted. Always immediately follows SecureDataLen (90) field.
//    >	   34		MsgSeqNum			Y		(Can be embedded within encrypted data section.)
//         50		SenderSubID			N		(Can be embedded within encrypted data section.) - Vendor Sub id like desk etc (Optional)
//         57		TargetSubID			N		'ADMIN' reserved for administrative messages not intended for a specific user. (Can be embedded within encrypted data section.)
//        116		OnBehalfOfSubID		N		Trading partner SubID used when delivering messages via a third party. (Can be embedded within encrypted data section.)
//        129		DeliverToSubID		N		Trading partner SubID used when delivering messages via a third party. (Can be embedded within encrypted data section.)
//         43		PossDupFlag			N		Always required for retransmissions, whether prompted by the sending system or as the result of a resend request. (Can be embedded within encrypted data section.)
//         97		PossResend			N		Required when message may be duplicate of another message sent under a different sequence number. (Can be embedded within encrypted data section.)
//    >	   52		SendingTime			Y		(Can be embedded within encrypted data section.) - Time of message transmission
//        122		OrigSendingTime		N		Required for message resends. If data is not available set to same value as SendingTime (52) (Can be embedded within encrypted data section.)
//
//____________________________________________________________________________________________< Message Body >___________________________________________________________________________________________
//
//-----------------------------------------------------------------------------------< New Order Single (MsgType(35) = D) >------------------------------------------------------------------------------
//
// Reference - https://btobits.com/fixopaedia/fixdic40/index.html?message_New_Order_Single_D.html
// Message Body - New Order Single (MsgType(35) = D)
//
//        • The New Order - Single (Value D) message type is used by institutions wishing to electronically submit securities and forex orders to a broker for execution.
//
//        • Client Order Id (Tag 11)
//            - Messages received with the PossResend (97) flag set in the header should be validated by ClOrdID (11)
//            and order parameters (side, symbol, quantity, etc.) to determine if the order had been previously submitted.
//                - PossResends previously received should be acknowledged back to the client via an Execution - Status message.
//                - PossResends not previously received should be processed as a new order and acknowledged via an Execution - New message.
//
//        • Special Instructions - Handling Instructions (Tag 21) and Execution Instructions (Tag 18)
//            - Orders can be submitted with special handling instructions and execution instructions.
//                - Handling instructions (21) refer to how the broker should handle the order on its trading floor.
//                - Execution instructions (18) contain explicit directions as to how the order should be executed.
//
//        • Additional Information
//        • Forex
//            - To support forex accommodation trades, two fields, ForexReq (121) and SettlCurrency (120) are included in the message.
//                - To request a broker to execute a forex trade in conjunction with the securities trade,
//                    - The institution would set the ForexReq (121) = Y and SettlCurrency (120) = "intended settlement currency".
//                    - The broker would then execute a forex trade from the execution currency to the settlement currency and report the results via the execution message in the NetMonies and SettlCurr fields.
//            - The order message can also be used to request a straight forex trade.
//                - Conventions for identifying a forex transaction are as follows:
//                    - The forex symbol is defined, in ISO codes, as "held-currency.new-currency" (e.g. "USD.GBP" indicates a desire to convert a held amount of USD to GPB)
//                    - Side is defined in terms of whether the OrderQty (38) is currency required or currency held
//                      (e.g. a forex order can be expressed as either "S 3,200,000 USD for GPB" or "B 2,000,000 GBP for USD", at a rate of 1.6 USD/GBP, these trades are equivalent in that the broker is receiving 3,200,000 USD and delivering 2,000,000 GBP)
//
//        • Message Format
//            TAG 	FIELD NAME  		FIXML 	REQ'D COMMENTS
//        >	   11		ClOrdID				Y		- Client Order Id
//            109		ClientID			N		Used for firm identification in third-party transactions.
//             76		ExecBroker			N		Used for firm identification in third-party transactions.
//              1		Account				N		- Account number
//             63		SettlmntTyp			N		Absence of this field is interpreted as Regular.
//             64		FutSettDate			C		Required when SettlmntTyp (63) = 6 (Future) or SettlmntTyp (63) = 8 (Sellers Option)
//        >	   21		HandlInst			Y		- Order Handling Instructions to Broker
//             18		ExecInst			N		Can contain multiple instructions, space delimited.
//            110		MinQty				N
//            111		MaxFloor			N
//            100		ExDestination		N
//             81		ProcessCode			N		Used to identify soft trades at order entry.
//        >	   55		Symbol				Y		- Security Identifier (Ticker)
//             65		SymbolSfx			N
//             48		SecurityID			N		- CUSIP or other alternate security identifier
//             22		IDSource			N		- Identifies class of alternative SecurityID. 1 = CUSIP, 2=SEDOL, 3=QUIK, 4=ISIN number, 5=RIC code
//            106		Issuer				N
//            107		SecurityDesc		N
//            140		PrevClosePx			N		Useful for verifying security identification
//        >	   54		Side				Y		- Buy or Sell - Side of the Order. 1=Buy, 2=Sell, 3=Buy minus, 4=Sell plus, 5=Sell short, 6=Sell short exempt
//            114		LocateReqd			C		Required for short sell orders
//        >	   38		OrderQty			Y		- Order Quantity (eg: Number of shares ordered)
//        >	   40		OrdType				Y		- Order Types 1=Market, 2=Limit, 3=Stop, 4=Stop limit
//             44		Price				C		Required for limit OrdTypes - Price of order if the order is Limit etc
//             99		StopPx				C		Required for stop OrdTypes
//             15		Currency			N		Message without currency field is interpreted as US dollars
//             23		IOIid				C		Required for Previously Indicated Orders ( OrdType (40) =E)
//            117		QuoteID				C		Required for Previously Quoted Orders ( OrdType (40) =D)
//             59		TimeInForce			N		Absence of this field indicates Day order
//            126		ExpireTime			C		Required if TimeInForce (59) = GTD
//             12		Commission			N
//             13		CommType			N
//             47		Rule80A				N
//            121		ForexReq			N		Indicates that broker is requested to execute a Forex accommodation trade in conjunction with the security trade.
//            120		SettlCurrency		C		Required if ForexReq (121) = Y.
//             58		Text				N
//
//------------------------------------------------------------------------------------< Execution Report (MsgType(35) = 8) >-----------------------------------------------------------------------------
//
// Reference - https://btobits.com/fixopaedia/fixdic40/index.html?message_Execution_Report_8.html
// Message Body - Execution Report (MsgType(35) = 8)
//
//        • The Execution Report (Value 8) message is used to:
//            - confirm the receipt of an order
//            - confirm changes to an existing order (i.e. accept cancel and replace requests)
//            - relay order status information
//            - relay fill information as orders are worked
//            - reject orders
//            - report miscellaneous fees calculations associated with a trade
//
//        •  Each execution message will contain information that will describe the current state of the order and execution status as understood by the broker.
//
//        • IDs - Client Order ID (Tag 11) and Order ID (Tag 37)
//            - is provided for institutions to affix an identification number to an order to coincide with internal systems.
//            - The OrderId (37) field is populated with the broker-generated order number.
//
//
//        • Execution Report (Value 8) -> Execution Transfer Type (Tag 20) -> (modify) Execution Reference ID (Tag 19)
//            - Execution Report messages can be transmitted as transaction types ExecTransType (20)
//                - NEW, CANCEL, CORRECT or STATUS.
//                    - Transaction types CANCEL and CORRECT modify the state of the message identified in field ExecRefID (19).
//                        - The CANCEL transaction type applies at the execution level.
//                            - The Cancel transaction will be used to cancel an execution which has been reported in error.
//                             - The canceled execution will be identified in the ExecRefID (19) field.
//                        - The CORRECT transaction type applies at the execution level and is used to modify an incorrectly reported fill.
//                            - The incorrect execution will be identified in the ExecRefID (19) field.
//                              Note: Data reported in the CumQty (14) and AvgPx (6) fields represent the status of the order as of the time of the correction, not as of the time of the originally reported execution.
//                        - Transaction type STATUS indicates that the execution message contains no new information, only summary information regarding order status.
//                        - The NEW transaction type indicates that this message represents a new order, a change in status of the order, or a new fill against an existing order.
//                            - The combination of the ExecTransType (20) and OrdStatus (39) fields will indicate how the message is to be applied to an order.
//
//        • The Order Status (Tag 39)
//            - is used to identify the status of the current order.
//                - The order statuses are as follows:
//                    0 New
//                        - Outstanding order with no executions
//                    1 PartiallyFilled
//                        - Outstanding order with executions and remaining quantity
//                    2 Filled
//                        - Order completely filled, no remaining quantity
//                    3 DoneForDay
//                        - Order not, or partially, filled; no further executions forthcoming
//                    4 Canceled
//                        - Canceled order with or without executions
//                    5 Replaced
//                    6 PendingCancel
//                        - Order with cancel request pending, used to confirm receipt of cancel or replace request.
//                        - DOES NOT INDICATE THAT THE ORDER HAS BEEN CANCELED OR REPLACED.
//                    7 Stopped
//                        - Order has been stopped at the exchange
//                    8 Rejected
//                        - Order has been rejected by broker.
//                        - NOTE: An order can be rejected subsequent to order acknowledgment, i.e. an order can pass from New to Rejected status.
//                    9 Suspended
//                        - Order has been placed in suspended state at the request of the client.
//                    A Pending New
//                        - Order has been received by brokers system but not yet accepted for execution.
//                        - An Execution (8) message with this status will only be sent in response to a Status Request (H) message.
//                    B Calculated
//                        - Order has been completed for the day (either filled or done for day). Miscellaneous fees have been calculated and reported in this execution message
//                    C Expired
//                        - Order has been canceled in broker's system due to time in force instructions.
//                    D AcceptedForBidding
//                    E PendingReplace
//
//
//        • Fill - CumQty (Tag 14) and AvgPx (Tag 6)
//            - should be calculated to reflect the fills on all versions of an order.
//                For example, if partially filled order A were replaced by order B,
//                the CumQty (14) and AvgPx (6) on order B's fills should represent the fills on order A plus those on order B.
//
//        • Message Format
//            TAG		FIELD NAME			FIXML	REQ'D	COMMENTS
//        >	   37		OrderID				Y
//             11		ClOrdID				N		- Client Order, Id Required for executions against electronically submitted orders which were assigned an ID by the institution. Not required for orders manually entered by the broker.
//            109		ClientID			N		Used for firm identification in third-party transactions.
//             76		ExecBroker			N		Used for firm identification in third-party transactions.
//             66		ListID				N		Required for executions against orders which were submitted as part of a list.
//        >	   17		ExecID				Y
//        >	   20		ExecTransType		Y
//             19		ExecRefID			C		Required for Cancel and Correct ExecTransType (20) messages
//        >	   39		OrdStatus			Y
//            103		OrdRejReason		N		For optional use with OrdStatus (39) = 8 (Rejected)
//              1		Account				N		- Account number, Required for executions against electronically submitted orders which were assigned an account by the institution
//             63		SettlmntTyp			N		Absence of this field is interpreted as Regular.
//             64		FutSettDate			C		Required when SettlmntTyp (63) = 6 (Future) or SettlmntTyp (63) = 8 (Sellers Option)
//        >    55		Symbol				Y		- Security Identifier (Ticker)
//             65		SymbolSfx			N
//             48		SecurityID			N
//             22		IDSource			N		- Identifies class of alternative SecurityID. 1 = CUSIP, 2=SEDOL, 3=QUIK, 4=ISIN number, 5=RIC code
//            106		Issuer				N
//            107		SecurityDesc		N
//        >	   54		Side				Y		- Buy or Sell - Side of the Order. 1=Buy, 2=Sell, 3=Buy minus, 4=Sell plus, 5=Sell short, 6=Sell short exempt
//        >	   38		OrderQty			Y		- Order Quantity (eg: Number of shares ordered)
//             40		OrdType				N		- Order Types 1=Market, 2=Limit, 3=Stop, 4=Stop limit
//             44		Price				N		Required for limit OrdTypes - Price of order if the order is Limit etc
//             99		StopPx				C		Required for OrdType (40) = 4 (Stop Limit).
//             15		Currency			N		Message without currency field is interpreted as US dollars
//             59		TimeInForce			N		Absence of this field indicates Day order
//            126     ExpireTime			C		Required if TimeInForce (59) = GTD
//             18		ExecInst			N		Can contain multiple instructions, space delimited.
//             47		Rule80A				N
//             32		LastShares			C		Not required for ExecTransType (20) = 3 (Status)
//             31		LastPx				C		Not required for ExecTransType (20) = 3 (Status)
//             30		LastMkt				N
//             29		LastCapacity		N
//        >    14		CumQty				Y
//        >  	6		AvgPx				Y
//             75		TradeDate			N		Used when reporting other than current day trades.
//             60		TransactTime		N
//            113		ReportToExch		N
//             12		Commission			N
//             13		CommType			N
//            136		NoMiscFees			N		Required if any miscellaneous fees are reported. Indicates number of repeating entries.
//            =>137	    MiscFeeAmt			C		Required if NoMiscFees (136) > 0
//            =>138	    MiscFeeCurr			C		Required if NoMiscFees (136) > 0
//            =>139	    MiscFeeType			C		Required if NoMiscFees (136) > 0
//            118		NetMoney			N		Required if miscellaneous fees are reported, in currency of execution
//            119		SettlCurrAmt		N		Used to report results of forex accommodation trade
//            120		SettlCurrency		N		Used to report results of forex accommodation trade
//             58		Text				N
//
//______________________________________________________________________________________< Standard Message Trailer >______________________________________________________________________________________
//
// Reference - https://btobits.com/fixopaedia/fixdic40/index.html?block_Standard_Message_Trailer.html
// Reference - https://en.wikipedia.org/wiki/Financial_Information_eXchange
// Standard Message Trailer
//
//        • Checksum (Tag 10)
//            - All messages terminate with "10=nnn<SOH>"
//                - i.e The checksum of a FIX message is always the last field in the message.
//                – It is composed of three characters. i.e nnn represents the Checksum of the data
//            – Checksum is the sum of all the binary values in the message
//                - It is given by summing the ASCII value of all characters in the message, except for those of the checksum field itself,
//                  and performing modulo 256 over the resulting summation.
//                    For example, below, the summation of all ASCII values (including the SOH character, which has a value of 1 in the ASCII table) results in 4158.
//                    Performing the modulo operation gives the value 62. Since the checksum is composed of three characters, 062 is used.
//                    8=FIX.4.2|9=65|35=A|49=SERVER|56=CLIENT|34=177|52=20090107-18:15:16|98=0|108=30|10=062|
//                                                                                                        ^
//            – Checksum helps to identify the transmission problems
//
//        • Message Format
//            TAG 	FIELD NAME  		FIXML 	REQ'D COMMENTS
//             93		SignatureLength		C 		Required when trailer contains signature. Note: Not to be included within SecureData (91) field
//             89		Signature			C 		Note: Not to be included within SecureData (91) field
//        >	   10		CheckSum			Y 		(Always unencrypted, always last field in message) - used for data integrity check
//
//________________________________________________________________________________________________________________________________________________________________________________________________________