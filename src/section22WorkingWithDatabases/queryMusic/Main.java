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
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(Path.of("music.properties"), StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String query1 = "SELECT * FROM music.artists";

        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));

//        String albumName = "Tapestry";
        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter an Album Name: ");
//        String albumName = scanner.nextLine();
//        String query2 = "SELECT * FROM music.albumview WHERE album_name='%s'".formatted(albumName);
        System.out.println("Enter an Artist ID: ");
        String artistIdStr = scanner.nextLine();
        int artistIdInt = Integer.parseInt(artistIdStr); // used to validate the input

        String query2 = "SELECT * FROM music.artists WHERE artist_id=%d".formatted(artistIdInt);

        try (
                var connection = dataSource.getConnection(
                        props.getProperty("user"),
                        System.getenv("MYSQL_PASS"));
                Statement statement = connection.createStatement();
        ) {
            System.out.println("Connected to the music DB ...");

            // query1
//            ResultSet resultSet1 = statement.executeQuery(query1);
//            var meta = resultSet1.getMetaData();
//            while (resultSet1.next()) {
//                System.out.println("---");
//                System.out.printf("%d %s %n", resultSet1.getInt(1), resultSet1.getString("artist_name"));
//
//            }
//            System.out.println("---");

            // query2
            ResultSet resultSet2 = statement.executeQuery(query2);
            var meta = resultSet2.getMetaData();
//            for (int i = 1; i <= meta.getColumnCount(); i++) {
//                System.out.printf("%d %s %s%n",
//                        i,
//                        meta.getColumnName(i),
//                        meta.getColumnTypeName(i)
//                );
//            }

            for (int i = 1; i <= meta.getColumnCount(); i++) {
                System.out.printf("%-15s", meta.getColumnName(i).toUpperCase());
            }

            System.out.println();

            while (resultSet2.next()) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    System.out.printf("%-15s", resultSet2.getString(i));
                }
                System.out.println();

            }

            // query3

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
