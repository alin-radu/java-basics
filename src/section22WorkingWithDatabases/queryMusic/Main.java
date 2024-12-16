package section22WorkingWithDatabases.queryMusic;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(Path.of("music.properties"), StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String query = "SELECT * FROM music.artists";

        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));

        try (
                var connection = dataSource.getConnection(
                        props.getProperty("user"),
                        System.getenv("MYSQL_PASS"));
                Statement statement = connection.createStatement();
        ) {
            System.out.println("Connected to the music DB ...");

            ResultSet resultSet = statement.executeQuery(query);
            var meta = resultSet.getMetaData();

            // solution1
            while (resultSet.next()) {
                System.out.println("---");
                System.out.printf("%d %s %n", resultSet.getInt(1), resultSet.getString("artist_name"));

            }
            System.out.println("---");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
