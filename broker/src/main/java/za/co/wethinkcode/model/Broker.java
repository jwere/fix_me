package za.co.wethinkcode.model;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.IOException;

import java.net.ConnectException;
import java.net.Socket;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLDocument;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class Broker implements Runnable {
	private String _serverAddress = "127.0.0.1";
	private int _PORT = 5000;
	private Socket _socket;
	private Scanner _in;
	private PrintWriter _out;
	private int _brokerId;

	// broker GUI
	private JFrame _frame; // = new JFrame("Broker");
	private JTextField _textField; // = new JTextField(50);
	private JTextPane _messageArea; // = new JTextArea(16, 50);

	// Text Decoration
	public String blue = "co:blue";
	public String green = "co:green";
	public String grey = "co:grey";
	public String purple = "co:purple";
	public String red = "co:red";
	public String teal = "co:teal";
	public String orange = "co:orange";
	public String reset = "co:reset";

    public Broker() {
    	try {
			this._frame = new JFrame("Broker");
			this._messageArea = new JTextPane();
			this._textField = new JTextField(50);


			this._messageArea.setSize(500, 250);
			this._messageArea.setEditable(false);
			this._messageArea.setContentType("text/html");

			this._textField.setEditable(false);

			this._frame.getContentPane().add(this._textField, BorderLayout.SOUTH);
			this._frame.getContentPane().add(new JScrollPane(this._messageArea), BorderLayout.CENTER);
			this._frame.pack();

			//		_frame.setContentPane((this._panel));

			// send on enter and clear to prepare for next msg
			this._textField.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					try {
						_out.println(_textField.getText());
						_textField.setText("");
					}
					catch (NullPointerException e) {
						System.out.println("");
					}
				}
			});
			DefaultCaret caret = (DefaultCaret) this._messageArea.getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

			//on ESCAPE
//		_panel.registerKeyboardAction(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				onClose();
//			}
//		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

			//editorPane1.setEditable(false);

			this._frame.addWindowListener(new WindowAdapter() {
				public void windowOpened(WindowEvent e) {
					try {
						_textField.requestFocus();
					}
					catch (NullPointerException ex) {
						System.out.println("");
					}
				}
			});

			//when cross is clicked
//		_frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		_frame.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				onClose();
//			}
//		});

			this._frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this._frame.setSize(850, 400);
			this._frame.setVisible(true);

			new Thread(this).start();
		} catch (HeadlessException e) {
			System.out.println(ConsoleDecorator.viewMessage("Something went wrong ...", "error"));
		}
		catch (NullPointerException e) {
			System.out.println("");
		}

	}

    private void getBrokerId() throws ConnectException, IOException, BadLocationException, NullPointerException {
		String line = _in.nextLine();

		this._brokerId = Integer.parseInt(line.substring(19));
		this.viewMessage("your broker ID is " + this._brokerId, "normal");
		System.out.println(ConsoleDecorator.viewMessage("[" + ConsoleDecorator.purple + "BROKER" + ConsoleDecorator.reset + " : " + this._brokerId + "] your broker ID is " + this._brokerId, "none"));
		return;
    }

	@Override
    public void run() {
	    try {
		    this._socket = new Socket(_serverAddress, _PORT);
		    this._in = new Scanner(_socket.getInputStream());
		    this._out = new PrintWriter(_socket.getOutputStream(), true);

		    getBrokerId();
		    this._frame.setTitle("Broker " + _brokerId);
			this._textField.setEditable(true);
			while (true) {
				while (this._in.hasNextLine()) {
					String line = this._in.nextLine();

					if (line.contains("Invalid"))
						this.viewMessage(line, "error");
					else if (line.contains("8=FIX.4.2"))
						this.displayFixMessage(line);
					else if (line.contains("] Available Stock"))
						this.makeTable(line);
					else
						this.viewMessage(line, "none");
				}
			}
	    }
		catch (NullPointerException e) {
			System.out.println("");
		}
	    catch (BadLocationException e) {
			System.out.println(ConsoleDecorator.viewMessage("Something went wrong ...", "error"));
		}
	    catch (UnknownHostException e) {
			System.out.println(ConsoleDecorator.viewMessage("Something went wrong ...", "error"));
		}
	    catch (ConnectException e) {
	    	System.out.println(ConsoleDecorator.viewMessage("Cannot detect a running Router!", "error"));

		}
	    catch (IOException e) {
			System.out.println(ConsoleDecorator.viewMessage("Something went wrong ...", "error"));
		}
	    finally {
		    this._frame.setVisible(false);
		    this._frame.dispose();
	    }
    }

	private void makeTable(String message) throws IOException, BadLocationException, NullPointerException {

            String[] products = message.split("[|]+");

			this.viewMessage(products[0], "none");

			String formTable = "";
			formTable += "<style> table { width: 100%; border-collapse: collapse; } td, th { padding: 5px 20px 5px 20px;} td { border-collapse: collapse;} th { border-collapse: collapse; background-color: rgb(0, 189, 170); font-weight: bold; font-family: Consolas, monaco, monospace; color: white; } </style>";
            formTable += "<table>" + "<tr>" + "<th>Item</th>" + "<th>Amount (R)</th>" + "<th>Quantity</th>" + "</tr>";

			products = Arrays.copyOfRange(products, 1, products.length);

			for (String product: products) {
				String[] props = product.split("[/]+");

				formTable += "<tr>";
				for (String prop: props)
				{
					formTable += "<td>" + prop + "</td>";
				}

				formTable += "</tr>";
			}

			formTable += "</table>";

			this.viewMessage(formTable, "none");
	}

	private void displayFixMessage(String message) throws IOException, BadLocationException, NullPointerException {
		String receiverValue = message.substring((message.indexOf("56=") + 3), (message.indexOf("56=") + 3 + 6));
		String senderValue = message.substring((message.indexOf("49=") + 3), (message.indexOf("49=") + 3 + 6));

		if (message.contains("39=")) {
			this.viewMessage("[" + purple + "BROKER" + reset + " : " + receiverValue + "] &lt; [" + blue + "MARKET" + reset + " : " + senderValue + "] " + message, "none");
			char fulfilled = message.charAt(message.indexOf("39=") + 3);

			if (fulfilled == '2')
				this.viewMessage("[" + purple + "BROKER" + reset + " : " + receiverValue + "] &lt; [" + blue + "MARKET" + reset + " : " + senderValue + "] request has been " + green + "ACCEPTED" + reset, "none");
			if (fulfilled == '8')
				this.viewMessage("[" + purple + "BROKER" + reset + " : " + receiverValue + "] &lt; [" + blue + "MARKET" + reset + " : " + senderValue + "] request has been " + red + "REJECTED" + reset, "none");
		} else {
			this.viewMessage("[" + purple + "BROKER" + reset + " : " + senderValue + "]  &gt; [" + blue + "MARKET" + reset + " : " + receiverValue + "] " + message, "none");
		}
	}

//____________________________________________________________________________________________TEXT DECORATOR___________________________________________________________________________________________

	public void viewMessage(String message, String type) throws IOException, BadLocationException, NullPointerException {

		this._messageArea.setEditable(true);

		String green = "style=\"color: rgb(161,221,112); font-weight: bold; font-family: Consolas, monaco, monospace;\"";
		String grey = "style=\"color: rgb(120,120,120); font-weight: bold; font-family: Consolas, monaco, monospace;\"";
		String red = "style=\"color: rgb(199,12,58); font-weight: bold; font-family: Consolas, monaco, monospace;\"";
		String teal = "style=\"color: rgb(0, 189, 170); font-weight: bold; font-family: Consolas, monaco, monospace;\"";
		String purple = "style=\"color: rgb(133, 89, 165); font-weight: bold; font-family: Consolas, monaco, monospace;\"";
		String blue = "style=\"color: rgb(40, 150, 150); font-weight: bold; font-family: Consolas, monaco, monospace;\"";
		String orange = "style=\"color: rgb(244, 89, 4); font-weight: bold; font-family: Consolas, monaco, monospace;\"";
		String reset = "style=\"color: rgb(0,0,0); font-weight: bold; font-family: Consolas, monaco, monospace;\"";

		String text;
		String mesType = "";
		String replaceString = message;


		if (type.toLowerCase().contains("error") || type.toLowerCase().contains("normal")) {
			String norm = (this._brokerId == 0) ? ("[<span " + teal + ">" + "FIX-ME" + "</span>" + "] ") : ("[<span " + purple + ">" + "BROKER" + "</span>" + "<span " + reset + "></span> : " + this._brokerId + "] ");
			mesType = (type.toLowerCase().contains("error") ? ("[<span " + red + ">" + "ERROR" + "</span>" + "] ") : norm);
		}

		replaceString = replaceString.replace("co:blue", ("<span " + blue + ">"));
		replaceString = replaceString.replace("co:grey", ("<span " + grey + ">"));
		replaceString = replaceString.replace("co:green", ("<span " + green + ">"));
		replaceString = replaceString.replace("co:purple", ("<span " + purple + ">"));
		replaceString = replaceString.replace("co:red", ("<span " + red + ">"));
		replaceString = replaceString.replace("co:teal", ("<span " + teal + ">"));
		replaceString = replaceString.replace("co:reset", "</span>");


		text = ("<p" + reset + ">" + mesType + replaceString + "</p><br>");

		this.appendToJPane(text);

	}

	private void appendToJPane(String text) throws IOException, BadLocationException, NullPointerException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    HTMLDocument doc = (HTMLDocument) _messageArea.getStyledDocument();
                    doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), text);

                    _messageArea.setEditable(false);

                    return;
                }
                catch (NullPointerException e) {
                    System.out.println("");
                } catch (IOException e) {
					System.out.println(ConsoleDecorator.viewMessage("Something went wrong ...", "error"));
                } catch (BadLocationException e) {
					System.out.println(ConsoleDecorator.viewMessage("Something went wrong ...", "error"));
                }
            }
        });
	}

//____________________________________________________________________________________________SWING COMPONENT___________________________________________________________________________________________

	public void onClose() throws IOException, BadLocationException, NullPointerException {
		this._frame.dispose();
		return;
	}

}
