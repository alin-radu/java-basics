package basics;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String connectionString = "jdbc:sqlite:/home/alin/alinSpinjitzu" + "/courses/Java_general" + "/1_Java Programming Masterclass covering Java 11 & " + "17_Tim/testDB/testjava.db";
        try (Connection conn = DriverManager.getConnection(connectionString); Statement statement = conn.createStatement();) {
            String sqlString = "CREATE TABLE IF NOT EXISTS contacts" +
                    " (name TEXT, phone INTEGER, email TEXT)";
            statement.execute(sqlString);

//            sqlString = "INSERT INTO contacts(name, phone, email)" +
//                    "VALUES('Joe', 234567, 'joe@email.com')";

//            sqlString = "UPDATE contacts SET phone=222222 WHERE name='Tim'";

//            sqlString="DELETE FROM contacts WHERE name='Jane'";

            sqlString = "SELECT * FROM contacts";

//            statement.execute(sqlString);
//            ResultSet results = statement.getResultSet();

            ResultSet results = statement.executeQuery(sqlString);
            while(results.next()){
                String name = results.getString("name");
                int phone = results.getInt("phone");
                String email = results.getString("email");

                System.out.println("Name " + name);
                System.out.println("Phone " + phone);
                System.out.println("Email " + email);
                System.out.println("---");
            }
            results.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong " + e.getMessage());
        }
    }
}