package section22WorkingWithDatabases.jdbcBasics;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;


@SuppressWarnings("DuplicatedCode")
public class MainDriverManager {
    private final static String CONN_STRING = "jdbc:mysql://localhost:3306/music";

    public static void main(String[] args) {
        String username = JOptionPane.showInputDialog(null, "Enter DB Username");
        JPasswordField pf = new JPasswordField();

        int okCxl = JOptionPane.showConfirmDialog(
                null, pf, "Enter DB Password", JOptionPane.OK_CANCEL_OPTION);

        final char[] password = (okCxl == JOptionPane.OK_OPTION) ? pf.getPassword() : null;

        try (
                Connection connection = DriverManager.getConnection(
                        CONN_STRING, username, String.valueOf(password))
        ) {
            connection.getMetaData().getDatabaseProductName();
            System.out.println("Success!!! Connection made to the music DB. ");
            System.out.println("DB type: " + connection.getMetaData().getDatabaseProductName());
            Arrays.fill(password, ' ');

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
