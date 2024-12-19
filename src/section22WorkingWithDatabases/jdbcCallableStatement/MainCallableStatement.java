package section22WorkingWithDatabases.jdbcCallableStatement;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Map;
import java.util.stream.Collectors;

//@SuppressWarnings({"DuplicatedCode", "JavaExistingMethodCanBeUsed"})
@SuppressWarnings({"DuplicatedCode"})
public class MainCallableStatement {
    private static final int ARTIST_COLUMN = 0;
    private static final int ALBUM_COLUMN = 1;
    private static final int SONG_COLUMN = 3;

    public static void main(String[] args) {

        Map<String, Map<String, String>> albums = null;

        try (var lines = Files.lines(Path.of("NewAlbums.csv"))) {

            albums = lines.map(s -> s.split(","))
                    .collect(Collectors.groupingBy(s -> s[ARTIST_COLUMN],
                            Collectors.groupingBy(s -> s[ALBUM_COLUMN],
                                    Collectors.mapping(s -> s[SONG_COLUMN],
                                            Collectors.joining(
                                                    "\",\"",
                                                    "[\"",
                                                    "\"]"
                                            )))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        albums.forEach((artist, artistAlbums) -> {
            artistAlbums.forEach((key, value) -> {
                System.out.println(key + " : " + value);
            });
        });

        var dataSource = new MysqlDataSource();

        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("music");
        dataSource.setUser(System.getenv("MYSQL_USER"));
        dataSource.setPassword(System.getenv("MYSQL_PASS"));

        try (
                Connection connection = dataSource.getConnection();
//                CallableStatement cs = connection.prepareCall("CALL music.addAlbumInOutCounts(?,?,?,?)");

        ) {
//            albums.forEach((artist, albumMap) -> {
//                albumMap.forEach((album, songs) -> {
//                    try {
//                        cs.setString(1, artist);
//                        cs.setString(2, album);
//                        cs.setString(3, songs);
//                        cs.setInt(4, 10);
//                        cs.registerOutParameter(4, Types.INTEGER);
//                        cs.execute();
//
//                        System.out.printf("---> addAlbumReturnCounts | result of the %d songs were added for %s%n", cs.getInt(4), album);
//
//                    } catch (SQLException e) {
//                        System.err.println(e.getErrorCode() + " " + e.getMessage());
//                    }
//                });
//            });

            String sql = "SELECT * FROM music.albumview WHERE artist_name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "Bob Dylan");
            ResultSet resultSet = ps.executeQuery();
            printRecords(resultSet);

            CallableStatement csf = connection.prepareCall(
                    "{ ? = CALL music.calcAlbumLength(?) }");
            csf.registerOutParameter(1, Types.DOUBLE);

            albums.forEach((artist, albumMap) -> {
                albumMap.keySet().forEach((albumName) -> {
                    try {
                        csf.setString(2, albumName);
                        csf.execute();
                        double result = csf.getDouble(1);
                        System.out.printf("Length of %s is %.1f%n", albumName, result);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // printRecords
    private static void printRecords(ResultSet resultSet) throws SQLException {
        var meta = resultSet.getMetaData();

        for (int i = 1; i <= meta.getColumnCount(); i++) {
            System.out.printf("%-15s", meta.getColumnName(i).toUpperCase());
        }

        System.out.println();

        while (resultSet.next()) {
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                System.out.printf("%-15s", resultSet.getString(i));
            }
            System.out.println();

        }
    }
}
