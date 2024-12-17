package section22WorkingWithDatabases.jdbcBasics;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

@SuppressWarnings("DuplicatedCode")
public class MainDataSource {
    private final static String CONN_STRING = "jdbc:mysql://localhost:3306/music";

    public static void main(String[] args) {
        String username = JOptionPane.showInputDialog(null, "Enter DB Username");
        JPasswordField pf = new JPasswordField();

        int okCxl = JOptionPane.showConfirmDialog(
                null, pf, "Enter DB Password", JOptionPane.OK_CANCEL_OPTION);

        final char[] password = (okCxl == JOptionPane.OK_OPTION) ? pf.getPassword() : null;

        var dataSource = new MysqlDataSource();
//        dataSource.setURL(CONN_STRING);
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("music");

        try (
                Connection connection = dataSource.getConnection(
                        username, String.valueOf(password))
        ) {
            System.out.println("Success!!! Connection made to the music DB.");
            System.out.println("DB type: " + connection.getMetaData().getDatabaseProductName());
            Arrays.fill(password, ' ');

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
