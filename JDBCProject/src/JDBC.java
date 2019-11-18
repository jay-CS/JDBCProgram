

import java.net.PortUnreachableException;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class JDBC {

    static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    //REPLACE WITH YOUR URL
    static final String DB_URL = "jdbc:derby:/Users/samantharain/Documents/CECS323/Databases/JDBC;";


    //  Database credentials
    static final String USER = "newuser";
    static final String PASS = "newpass";

    static Connection con = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;

    public static void main(String[] args) throws SQLException {

        try {
            // register JDBC driver
            Class.forName(JDBC_DRIVER);
            // open a connection
            con = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            System.out.println("Error establishing connecting. Exiting...");
            System.exit(-1);
        }

        Scanner in = new Scanner(System.in);
        boolean quit = false;
        int choice;

        while (!quit) {
            DisplayMenu();
            choice = enterChoice(in);
            System.out.println();

            switch (choice) {
                case 1:
                    //List all writing groups
                    ListGroupNames();
                    break;
                case 2:
                    //List all data for a group
                    ListGroupData();
                    break;
                case 3:
                    //List all publishers
                    ListPublisherNames();
                    break;
                case 4:
                    //List all data for a publisher
                    ListPublisherData();
                    break;
                case 5:
                    //List all book titles
                    ListBookTitles();
                    break;
                case 6:
                    //List all data for a book
                    ListBookData();
                    break;
                case 7:
                    //Insert new publisher
                    InsertPublisher();
                    System.out.println("Updated PublisherList: --------------------\n");
                    ListPublisherNames();
                    break;
                case 8:
                    //update books publisher
                    ChangeBookPublisher();
                    System.out.println("Updated BookList: --------------------\n");
                    System.out.println("Enter the new publisher name again");
                    ListPublisherData();
                    break;
                case 9:
                    //Insert a book
                    InsertBook();
                    System.out.println("Updated BookList: --------------------\n");
                    ListBookTitles();
                    break;
                case 10:
                    //Remove a book
                    RemoveBook();
                    System.out.println("Updated BookList: --------------------\n");
                    ListBookTitles();
                    break;
                case 11:
                    //Quit
                    quit = true;
                    break;
            }

        } // end of while loop

        try {
            //Closing all open connections
            if(rs != null){rs.close(); }
            if(ps != null){ps.close(); }
            con.close();
            in.close();
            System.out.println("\nGoodbye!");
        } catch (Exception e) {
            System.out.println("Error occured. Program exiting...");
        }

    } // end of main

    public static void DisplayMenu() {
        System.out.println("\n==================== MENU ========================");
        System.out.println("1. List all writing groups ");
        System.out.println("2. List all data for a group");
        System.out.println("3. List all publishers");
        System.out.println("4. List all data for a publisher");
        System.out.println("5. List all book titles");
        System.out.println("6. List all data for a book");
        System.out.println("7. Insert new publisher and update books publisher");
        System.out.println("8. Update books publisher");
        System.out.println("9. Insert a new Book Title");
        System.out.println("10. Remove a book");
        System.out.println("11. Quit");
        System.out.println("==================================================");
    }

    public static void ListGroupNames() throws SQLException {

        String query = "SELECT GROUPNAME FROM WRITINGGROUPS";
        ps = con.prepareStatement(query);
        rs = ps.executeQuery();
        PrintNames(rs);
    }

    public static void ListGroupData() throws SQLException {

        String groupName;
        String query = "SELECT * FROM WritingGroups NATURAL JOIN Books NATURAL JOIN Publishers WHERE GroupName = ?";

        Scanner in = new Scanner(System.in);
        System.out.print("Enter the group name: ");
        groupName = in.nextLine();
        System.out.println();

        rs = GetResults(query, groupName);

        PrintGroupData(rs);
    }

    public static void ListPublisherNames() throws SQLException {
        String query = "SELECT * FROM Publishers";
        rs = GetResults(query);
        PrintNames(rs);
    }

    public static void ListPublisherData() throws SQLException {

        String publisherName;
        String query = "SELECT * FROM Publishers NATURAL JOIN Books NATURAL JOIN WritingGroups WHERE PublisherName = ?";

        Scanner in = new Scanner(System.in);
        System.out.print("Enter the publisher name: ");
        publisherName = in.nextLine();
        System.out.println();

        rs = GetResults(query, publisherName);
        PrintPublisherData(rs);
    }

    public static void PrintNames(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

    public static void PrintGroupData(ResultSet rs) throws SQLException {
        System.out.printf("%-35s %-20s %-12s %-15s %-40s %-15s %-12s %-25s %-45s %-16s %-35s \n", "GroupName", "HeadWriter", "YearFormed", "Subject",
                "BookTitle", "YearPublished", "NumberPages", "PublisherName", "PublisherAddress", "PublisherPhone", "PublisherEmail");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------"
                                   + "----------------------------------------------------------------------------------------------------------------------------------"
                                   + "-----------------------------");

        if (rs.next()) {

            do {
                //Display values
                System.out.printf("%-35s %-20s %-12s %-15s %-40s %-15s %-12s %-25s %-45s %-16s %-35s \n", rs.getString("GroupName"),
                        rs.getString("HeadWriter"), rs.getString("YearFormed"), rs.getString("Subject"), rs.getString("BookTitle"), rs.getString("YearPublished"),
                        rs.getString("NumberPages"), rs.getString("PublisherName"), rs.getString("PublisherAddress"), rs.getString("PublisherPhone"), rs.getString("PublisherEmail"));
            } while (rs.next());
        } else {
            System.out.println("DATA NOT IN DB");
        }

    }

    public static void PrintPublisherData(ResultSet rs) throws SQLException {
        System.out.printf("%-25s %-45s %-16s %-35s %-40s %-15s %-12s %-35s %-20s %-12s %-15s \n", "PublisherName", "PublisherAddress", "PublisherPhone", "PublisherEmail",
                "BookTitle", "YearPublished", "NumberPages", "GroupName", "HeadWriter", "YearFormed", "Subject");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------"
                                   + "----------------------------------------------------------------------------------------------------------------------------------"
                                   + "-----------------------------");
        if (rs.next()) {

            do {
                //Display values
                System.out.printf("%-25s %-45s %-16s %-35s %-40s %-15s %-12s %-35s %-20s %-12s %-15s \n", rs.getString("PublisherName"), rs.getString("PublisherAddress"),
                        rs.getString("PublisherPhone"), rs.getString("PublisherEmail"), rs.getString("BookTitle"), rs.getString("YearPublished"), rs.getString("NumberPages"),
                        rs.getString("GroupName"), rs.getString("HeadWriter"), rs.getString("YearFormed"), rs.getString("Subject"));
            } while (rs.next());
        } else {
            System.out.println("DATA NOT IN DB");
        }
    }

    public static void PrintBookData(ResultSet rs) throws SQLException {
        System.out.printf("%-40s %-15s %-12s %-25s %-45s %-16s %-35s %-35s %-20s %-12s %-15s \n", "BookTitle", "YearPublished", "NumberPages", "PublisherName",
                "PublisherAddress", "PublisherPhone", "PublisherEmail", "GroupName", "HeadWriter", "YearFormed", "Subject");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------"
                                   + "----------------------------------------------------------------------------------------------------------------------------------"
                                   + "-----------------------------");
        if (rs.next()) {

            do {
                //Display values
                System.out.printf("%-40s %-15s %-12s %-25s %-45s %-16s %-35s %-35s %-20s %-12s %-15s \n", rs.getString("BookTitle"), rs.getString("YearPublished"), rs.getString("NumberPages"),
                        rs.getString("PublisherName"), rs.getString("PublisherAddress"), rs.getString("PublisherPhone"), rs.getString("PublisherEmail"), rs.getString("GroupName"),
                        rs.getString("HeadWriter"), rs.getString("YearFormed"), rs.getString("Subject"));
            } while (rs.next());
        } else {
            System.out.println("DATA NOT IN DB");
        }
    }

    public static void ListBookTitles() throws SQLException {
        String query = "SELECT BookTitle FROM Books";
        rs = GetResults(query);
        PrintNames(rs);
    }

    public static void ListBookData() throws SQLException {

        String BookTitle, groupname;
        String query = "SELECT * FROM Books NATURAL JOIN Publishers NATURAL JOIN WritingGroups WHERE BookTitle = ? and GROUPNAME = ?";

        Scanner in = new Scanner(System.in);
        System.out.print("Enter the book title: ");
        BookTitle = in.nextLine();
        System.out.print("Enter the Group Name: ");
        groupname = in.nextLine();
        System.out.println();
        ps = con.prepareStatement(query);
        ps.setString(1, BookTitle);
        ps.setString(2, groupname);
        rs = ps.executeQuery();
        PrintBookData(rs);
    }

    public static ResultSet GetResults(String query) {
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

        } catch (SQLException ex) {
            System.out.println("Error has occured. Returning to menu.");
        }
        return rs;
    }

    public static ResultSet GetResults(String query, String param) {
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, param);
            rs = ps.executeQuery();

        } catch (SQLException ex) {
            System.out.println("Error has occured. Returning to menu.");
        }
        return rs;
    }

    public static void InsertPublisher() {

        String publisherName, publisherAddress, publisherPhone, publisherEmail;
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the new publisher name: ");
        publisherName = in.nextLine();
        System.out.print("Enter the new publisher address: ");
        publisherAddress = in.nextLine();
        System.out.print("Enter the new publisher phone: ");
        publisherPhone = in.nextLine();
        System.out.print("Enter the new publisher email: ");
        publisherEmail = in.nextLine();

        System.out.println();

        String query = "INSERT INTO Publishers (PublisherName, PublisherAddress, PublisherPhone, PublisherEmail) "
                               + "VALUES  (?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, publisherName);
            ps.setString(2, publisherAddress);
            ps.setString(3, publisherPhone);
            ps.setString(4, publisherEmail);
            ps.executeUpdate();
            System.out.println();

        } catch (SQLException ex) {
            System.out.println("ERROR, make sure publisher does not already exist in database");
        }

    }

    public static void InsertBook() {
        String GroupName, BookTitle, publisherName, yearPublished, NumberofPages;
        Scanner in = new Scanner(System.in);
        boolean isValid = false;
        System.out.print("Enter the Group name: ");
        GroupName = in.nextLine();
        GroupName = GroupName.replace("\"", "");
        System.out.print("Enter the booktitle: ");
        BookTitle = in.nextLine();
        BookTitle = BookTitle.replace("\"", "");
        System.out.print("Enter the publisher name: ");
        publisherName = in.nextLine();
        System.out.print("Enter the yearPublished: ");
        yearPublished = in.nextLine();
        System.out.print("Enter the Number of Pages: ");
        NumberofPages = in.nextLine();

        System.out.println();

        String query = "INSERT INTO BOOKS (GroupName, BookTitle, publisherName , yearPublished, NumberPages) "
                               + "VALUES  (?, ?, ?, ?,?)";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, GroupName);
            ps.setString(2, BookTitle);
            ps.setString(3, publisherName);
            ps.setString(4, yearPublished);
            ps.setString(5, NumberofPages);
            ps.executeUpdate();

//            ChangeBookPublisher();
        } catch (SQLException ex) {
            System.out.println("Book could not be inserted, make sure the \"GroupName\" and \"PublisherName\" are correct and exist");
            System.out.println("Make sure the book does not already exist in the database");
        }
    }

    public static void ChangeBookPublisher() {
        String oldPublisher, newPublisher;
        Scanner in = new Scanner(System.in);

        System.out.print("\nChange books publisher.\n");
        System.out.print("Enter the old publisher name: ");
        oldPublisher = in.nextLine();
        System.out.print("Enter the new publisher name: ");
        newPublisher = in.nextLine();

        System.out.println();

        String query = "UPDATE Books SET PublisherName = ? WHERE PublisherName = ?";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, newPublisher);
            ps.setString(2, oldPublisher);
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("ERROR, make sure publisher name is valid");
        }
    }

    public static void RemoveBook() {
        String bookTitle;
        Scanner in = new Scanner(System.in);
        System.out.print("Enter the Writing group the book belongs to: ");
        String groupName = in.nextLine();
        System.out.print("Enter book title to remove: ");
        bookTitle = in.nextLine();
        bookTitle = bookTitle.replace("\"", "");
        System.out.println();

        String query = "DELETE FROM Books WHERE BookTitle = ? and GROUPNAME = ?";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, bookTitle);
            ps.setString(2, groupName);
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error has occured. Returning to menu.");
        }
    }

    public static int enterChoice(Scanner s) {
        System.out.print("Select an option: ");
        int choice;
        do {
            while (!s.hasNextInt()) {
                String input = s.next();
                System.out.printf("\"%s\" is not a valid number", input);
            }
            choice = s.nextInt();
        } while (choice < 1 || choice > 11);
        return choice;
    }

} // end of JDBC class
