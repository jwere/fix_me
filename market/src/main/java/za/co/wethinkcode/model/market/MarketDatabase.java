package za.co.wethinkcode.model.market;

import za.co.wethinkcode.model.instruments.Item;
import za.co.wethinkcode.model.product.Product;

import java.sql.*;

public class MarketDatabase
{
    private Connection connection   = null;
    private Statement statement     = null;
    private ResultSet resultSet     = null;

    // this should be placed in a folder where all components can access
    public void connectDataBase() throws NullPointerException,  SQLException
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

    // Table containing all Markets with their products
    //      Note - Let's make it that two markets cannot sell the same product?
    public void createMarketTable() throws NullPointerException, SQLException
    {
        this.connectDataBase();

        // //STEP 3: Execute a query
        this.statement = this.connection.createStatement();
        // Raw string literals are not supported on level 8
        String sql =    "CREATE TABLE IF NOT EXISTS markets"
                        + "(ProductName varchar(255) UNIQUE NOT NULL,"
                        + "ProductCode varchar(255) UNIQUE NOT NULL,"
                        + "Price int NOT NULL, "
                        + "Quantity int NOT NULL, "
                        + "MarketName varchar(255) NOT NULL)";

        this.statement.execute(sql);
        this.closeDataBase();
    }

    public void saveToDataBase(Product product) throws NullPointerException, SQLException
    {

        this.connectDataBase();
        this.connection.setAutoCommit(false);

        this.statement = connection.createStatement();

//        String sql =  "REPLACE INTO markets (ProductName, ProductCode, Price, Quantity, MarketName)"  +
//                "VALUES( '" + ProductName + "' ,  '" + ProductCode + "' , " + Price + "," + Quantity + ", '" + MarketName + "' )";
//

//        this.statement.execute(sql);
        this.connection.commit();

        this.closeDataBase();
    }
//
//    public Hero fetchFromDataBase(String savedName) throws SQLException
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
////                "WHERE CharacterName = '"+ savedName +"'" +
////                "COLLATE NOCASE";
////
////        this.resultSet = statement.executeQuery(sql);
////
////        Hero result = null;
////
////        while (resultSet.next())
////        {
////            String characterName    = resultSet.getString("CharacterName");
////            int characterPosX       = resultSet.getInt("PositionX");
////            int characterPosY       = resultSet.getInt("PositionY");
////            int characterXP         = resultSet.getInt("XP");
////            int characterHP         = resultSet.getInt("HP");
////            int characterAP         = resultSet.getInt("AP");
////            int characterDP         = resultSet.getInt("DP");
////
////            // System.out.println("characterName : " +  characterName);
////            // System.out.println("characterPosX : " +  characterPosX);
////            // System.out.println("characterPosY : " +  characterPosY);
////            // System.out.println("characterXP : " +  characterXP);
////            // System.out.println("characterHP : " +  characterHP);
////            // System.out.println("characterAP: " +  characterAP);
////            // System.out.println("characterDP: " +  characterDP);
////
////            result = new Rogue(characterName, characterPosX, characterPosY, characterXP, characterHP, characterAP, characterDP);
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
//        //String sql = "SELECT * FROM savedProfiles";
//        String sql = "DELETE FROM savedProfiles WHERE CharacterName = '" + savedName + "'";
//
//        this.statement.executeUpdate(sql);
//        //statement.execute(sql);
//        this.connection.commit();
//
//        this.closeDataBase();
//
//    }
//
//    public List<Hero> showAllFromDataBase() throws SQLException
//    {
//        List<Hero> saves = new ArrayList<Hero>();
//
//        this.connectDataBase();
//
//        this.statement = connection.createStatement();
//
//        String sql = "SELECT * FROM savedProfiles";
//
//        this.resultSet = statement.executeQuery(sql);
//
//        while ( resultSet.next() )
//        {
//            String characterName    = resultSet.getString("CharacterName");
//            int characterPosX       = resultSet.getInt("PositionX");
//            int characterPosY       = resultSet.getInt("PositionY");
//            int characterXP         = resultSet.getInt("XP");
//            int characterHP         = resultSet.getInt("HP");
//            int characterAP         = resultSet.getInt("AP");
//            int characterDP         = resultSet.getInt("DP");
//
//            saves.add(new Rogue(characterName, characterPosX, characterPosY, characterXP, characterHP, characterAP, characterDP));
//
//
//            // System.out.println("characterName : " +  characterName);
//            // System.out.println("characterPosX : " +  characterPosX);
//            // System.out.println("characterPosY : " +  characterPosY);
//            // System.out.println("characterXP : " +  characterXP);
//            // System.out.println("characterHP : " +  characterHP);
//            // System.out.println("characterAP: " +  characterAP);
//            // System.out.println("characterDP: " +  characterDP);
//        }
//
//        this.resultSet.close();
//        this.closeDataBase();
//        return saves;
//    }
}
