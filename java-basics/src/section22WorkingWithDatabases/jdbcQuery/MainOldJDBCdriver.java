package section22WorkingWithDatabases.jdbcQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MainOldJDBCdriver {
    private final static String CONN_STRING = "jdbc:mysql://localhost:3306/music";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (
                Connection connection = DriverManager.getConnection(
                        CONN_STRING, System.getenv("MYSQL_USER"),
                        System.getenv("MYSQL_PASS"));
                Statement statement = connection.createStatement();
        ) {
            System.out.println("Successfully connected to the music DB ...");

            String artistName = "Elf";
            String query = "Select * FROM music.artists WHERE artist_name='%s'".formatted(artistName);

            boolean resultSet = statement.execute(query);
            System.out.println("resultSet= " + resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
