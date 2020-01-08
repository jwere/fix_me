package za.co.wethinkcode.core;

import java.io.IOException;
import java.net.ConnectException;

import za.co.wethinkcode.model.Broker;

import javax.swing.*;
import javax.swing.text.BadLocationException;

/**
 *  Broker App!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                final Broker broker = new Broker();
            }
        });
    }
}
