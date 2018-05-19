import java.sql.*;   // Needed for JDBC classes

/**
 * This program creates the ContactDB database.
 */
public class CreateContactDB
{
    public static void main(String[] args)
    {
        // Create a named constant for the URL.
        // NOTE: This value is specific for Java DB.
        final String DB_URL = "jdbc:derby:ContactDB;create=true";

        try
        {
            // Create a connection to the database.
            Connection conn =
                    DriverManager.getConnection(DB_URL);

            // If the DB already exists, drop the tables.
            dropTables(conn);

            // Build the Contact table.
            buildContactTable(conn);

            // Close the connection.
            conn.close();
        }
        catch (Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    /**
     * The dropTables method drops any existing
     * in case the database already exists.
     */
    public static void dropTables(Connection conn)
    {
        System.out.println("Checking for existing tables.");

        try
        {
            // Get a Statement object.
            Statement stmt  = conn.createStatement();;

            try
            {
                // Drop the contact table.
                stmt.execute("DROP TABLE Contact");
                System.out.println("Contact table dropped.");
            }
            catch(SQLException ex)
            {
                // No need to report an error.
                // The table simply did not exist.
            }
        }
        catch(SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * The buildContactTable method creates the
     * Contact table and adds some rows to it.
     */
    public static void buildContactTable(Connection conn)
    {
        try
        {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE Contact (" +
                    "FirstName VARCHAR(25), " +
                    "LastName VARCHAR(25), " +
                    "Email VARCHAR(100) NOT NULL PRIMARY KEY, " +
                    "Phone VARCHAR(25), " +
                    "Address VARCHAR(200), " +
                    "Company VARCHAR(100), " +
                    "Notes VARCHAR(200)" +
                    ")");

            // Insert row #1.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Rick', " +
                    "'Sanchez', " +
                    "'rick@pickles.com', " +
                    "'123-432-3948', " +
                    "'1 Pelican Way', " +
                    "'Morty Corp', " +
                    "'')" );

            // Insert row #1.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Morty', " +
                    "'Sanchez', " +
                    "'morty@sanchez.com', " +
                    "'123-432-3999', " +
                    "'1 Pelican Way', " +
                    "'Morty Corp', " +
                    "'')");

            // Insert row #2.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Clark', " +
                    "'Kent', " +
                    "'notsuperman@superman.com', " +
                    "'123-432-3948', " +
                    "'1 Space Way', " +
                    "'Justice League', " +
                    "'')");

            // Insert row #3.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Charde', " +
                    "'Fox', " +
                    "'chardefox@aol.com', " +
                    "'385-432-4957', " +
                    "'2172 Donec Avenue', " +
                    "'Condimentum Incorporated', " +
                    "'')");

            // Insert row #4.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Cassidy', " +
                    "'Haynes', " +
                    "'chaynes@gmail.com', " +
                    "'255-577-5857', " +
                    "'9257 Duis Avenue', " +
                    "'Sem Corporation', " +
                    "'')");

            // Insert row #5.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Nicole', " +
                    "'Rich', " +
                    "'nrich@gmail.com', " +
                    "'425-482-4857', " +
                    "'3367 Commodo Avenue', " +
                    "'Commodo Corporation', " +
                    "'')");

            // Insert row #6.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Timothy', " +
                    "'Ellison', " +
                    "'tellis@gmail.com', " +
                    "'152-525-2588', " +
                    "'9554 Venenatis St.', " +
                    "'Malesuada LLP', " +
                    "'')");

            // Insert row #1.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Damon', " +
                    "'Chambers', " +
                    "'dchambers33@gmail.com', " +
                    "'148-252-5252', " +
                    "'1073 Egestas Rd.', " +
                    "'Non Luctus Consulting', " +
                    "'')");

            // Insert row #7.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Mira', " +
                    "'Eaton', " +
                    "'meaton40@gmail.com', " +
                    "'250-492-8574', " +
                    "'1863 Per Avenue', " +
                    "'Quam Corporation', " +
                    "'')");

            // Insert row #8.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'George', " +
                    "'Finch', " +
                    "'gfinchbiz@gmail.com', " +
                    "'484-484-4222', " +
                    "'1 Almost Way', " +
                    "'Quam Corporation', " +
                    "'')");

            // Insert row #9.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Brianna', " +
                    "'Bowman', " +
                    "'b24bowman@gmail.com', " +
                    "'122-526-2522', " +
                    "'295 Daisy Blvd', " +
                    "'Bowman Construction', " +
                    "'')");

            // Insert row #10.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Seth', " +
                    "'Delaney', " +
                    "'sethgamer2004@gmail.com', " +
                    "'936-252-5959', " +
                    "'554 Sunflower Blvd', " +
                    "'Moseley Plumbing', " +
                    "'')");

            // Insert row #11.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Omar', " +
                    "'Salazar', " +
                    "'ferriswheel14@gmail.com', " +
                    "'959-522-2224', " +
                    "'492 Tulip Rd', " +
                    "'A1 Carwash', " +
                    "'')");

            // Insert row #12.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Jenna', " +
                    "'Odom', " +
                    "'jennabean88@gmail.com', " +
                    "'829-298-2521', " +
                    "'5115 Sunview Ln', " +
                    "'Joes Italiano', " +
                    "'')");

            // Insert row #13.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Devin', " +
                    "'Steele', " +
                    "'dsteele1@gmail.com', " +
                    "'988-558-1511', " +
                    "'52525 Tidwell Dr', " +
                    "'Steele Academy', " +
                    "'')");

            // Insert row #14.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Robert', " +
                    "'Hewitt', " +
                    "'robertdh@gmail.com', " +
                    "'958-999-5111', " +
                    "'244 Marigold St', " +
                    "'Holland Sports', " +
                    "'')");

            // Insert row #15.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Maya', " +
                    "'McMillan', " +
                    "'marymillan2@gmail.com', " +
                    "'445-839-2563', " +
                    "'492 Rose Ln', " +
                    "'China Star', " +
                    "'')");

            // Insert row #16.
            stmt.execute("INSERT INTO Contact VALUES ( " +
                    "'Gail', " +
                    "'Moore', " +
                    "'gmoore2@gmail.com', " +
                    "'144-855-2626', " +
                    "'25525 Washington St', " +
                    "'Moore Designs', " +
                    "'')");

            System.out.println("Contact table created.");
        }
        catch (SQLException ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}