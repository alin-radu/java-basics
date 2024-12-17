package section22WorkingWithDatabases.jdbcQuery;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MainQueryBasics {
    public static void main(String[] args) {

        // load data from music.properties file
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(Path.of("music.properties"), StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // query
//        String query = "SELECT * FROM music.artists";

        String albumName = "Tapestry";
        String query = "SELECT * FROM music.albumView WHERE album_name='%s'".formatted(albumName);

        // create DataSource
        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));

        try (
                var connection = dataSource.getConnection(
                        props.getProperty("user"), System.getenv("MYSQL_PASS"));
                Statement statement = connection.createStatement();
        ) {
            System.out.println("Connected to the music DB ...");

            ResultSet resultSet = statement.executeQuery(query);

//            while (resultSet.next()) {
//                int artistId = resultSet.getInt(1);
//                String artistName = resultSet.getString("artist_name");
//
//                System.out.printf("%d %s %n", artistId, artistName);
//            }

            while (resultSet.next()) {
                int trackNumber = resultSet.getInt("track_number");
                String artistName = resultSet.getString("artist_name");
                String songTitle = resultSet.getString("song_title");

                System.out.printf("%d %s %s %n", trackNumber, artistName, songTitle);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
