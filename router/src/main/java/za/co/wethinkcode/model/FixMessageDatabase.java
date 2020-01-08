package za.co.wethinkcode.model;

import java.sql.*;

public class FixMessageDatabase
{
    private Connection connection   = null;
    private Statement statement     = null;
    private ResultSet resultSet     = null;

    // this should be placed in a folder where all components can access
    public void connectDataBase() throws NullPointerException, SQLException
    {

        //JDBC driver name and database URL
        //String JDBC_DRIVER = "org.sqlite.JDBC";
        String DB_URL = "jdbc:sqlite:fixme.db";

        // STEP 1: Register JDBC driver
        //Class.forName(JDBC_DRIVER);

        //STEP 2: Open a connection
        this.connection = DriverManager.getConnection(DB_URL);
    }

    // this should be placed in a folder where all components can access
    public void closeDataBase() throws NullPointerException, SQLException
    {
        // STEP 4: Clean-up environment
        this.statement.close();
        this.connection.close();
    }

    // Table containing transactions between Market and the Broker.
    // keeps record of Fix Messages
    public void createTransactionTable() throws NullPointerException, SQLException
    {
        this.connectDataBase();

        // //STEP 3: Execute a query
        // Let's make it that two markets cannot sell the same product
        this.statement = this.connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS transactions"
                    + "(BrokerID int NOT NULL,"
                    + "BrokerFixedMessage TEXT NOT NULL,"
                    + "MarketID int NOT NULL,"
                    + "MarketFixedMessage TEXT NOT NULL)";


        this.statement.execute(sql);
        this.closeDataBase();
    }
//
    public void saveToDataBase(String fixMessage) throws NullPointerException,  SQLException
    {

        this.connectDataBase();
        this.connection.setAutoCommit(false);

        this.statement = connection.createStatement();

        int _brokerId = Integer.parseInt(fixMessage.substring(fixMessage.indexOf("49=")+3, fixMessage.indexOf("49=")+9));
        int _marketId = Integer.parseInt(fixMessage.substring(fixMessage.indexOf("56=")+3, fixMessage.indexOf("56=")+9));

        String sql = "REPLACE INTO transactions (BrokerID, BrokerFixedMessage, MarketID, MarketFixedMessage)" +
                "VALUES(" + _brokerId + ", '" + fixMessage + "' ," +  _marketId + ", '" + fixMessage + "')";

        this.statement.execute(sql);
        this.connection.commit();

        this.closeDataBase();
    }
//
//    public item fetchFromDataBase(String savedName) throws SQLException
//    {
////        this.connectDataBase();
////
////        @NotEmpty(message = "Name cannot be empty")
////        String name = savedName;
////
////        this.statement = connection.createStatement();
////
////        String sql = "SELECT * " +
////                "FROM savedProfiles " +
////                "WHERE ref = '"+ savedName +"'" +
////                "COLLATE NOCASE";
////
////        this.resultSet = statement.executeQuery(sql);
////
////        Hero result = null;
////
////        while (resultSet.next())
////        {

////        }
////
////        this.resultSet.close();
////        this.closeDataBase();
////        return result;
//    }
//
//    public void deleteFromDataBase(String savedName) throws SQLException
//    {
//        this.connectDataBase();
//
//        this.connection.setAutoCommit(false);
//
//        @NotEmpty(message = "Name cannot be empty")
//        String name = savedName;
//
//        this.statement = connection.createStatement();
//        //String sql = "SELECT * FROM table";
//        String sql = "DELETE FROM table WHERE ref = '" + refname + "'";
//
//        this.statement.executeUpdate(sql);
//        //statement.execute(sql);
//        this.connection.commit();
//
//        this.closeDataBase();
//
//    }
//
//    public List<> showAllFromDataBase() throws SQLException
//    {
//
//        this.connectDataBase();
//
//        this.statement = connection.createStatement();
//
//        String sql = "SELECT * FROM table";
//
//        this.resultSet = statement.executeQuery(sql);
//
//        while ( resultSet.next() )
//        {
//
//            // System.out.println("");
//            // System.out.println("");
//            // System.out.println("");
//            // System.out.println("");
//            // System.out.println("");
//            // System.out.println("");
//            // System.out.println("");
//        }
//
//        this.resultSet.close();
//        this.closeDataBase();
//        return saves;
//    }
}
