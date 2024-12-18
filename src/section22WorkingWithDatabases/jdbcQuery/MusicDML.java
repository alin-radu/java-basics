package section22WorkingWithDatabases.jdbcQuery;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

@SuppressWarnings("DuplicatedCode")
public class MusicDML {

    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(Path.of("music.properties"), StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));
        try {
            dataSource.setAllowMultiQueries(true);
            dataSource.setContinueBatchOnError(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (
                Connection connection = dataSource.getConnection(
                        System.getenv("MYSQL_USER"), System.getenv("MYSQL_PASS"));
                Statement statement = connection.createStatement();
        ) {
            String tableName = "music.artists";
            String columnName = "artist_name";
            String columnValue = "Alin Second";

            boolean existsData = executeSelect(statement, tableName, columnName, columnValue);

            if (!existsData) {
                insertArtistAlbum(statement, columnValue, columnValue);

//                System.out.println("Maybe we should add this record.");
//
//                boolean addRecord = insertRecord(statement, tableName, new String[]{columnName}, new String[]{columnValue});
//
//                if (addRecord) {
//                    System.out.println("Record added to the DB.");
//                } else {
//                    System.out.println("Something went wrong!");
//                }
            } else {
                try {
                    deleteArtistAlbum(connection, statement, columnValue, columnValue);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                executeSelect(statement, "music.albumview", "album_name", columnValue);
                executeSelect(statement, "music.albums", "album_name", columnValue);

//                boolean deleteRecord = deleteRecord(statement, tableName, columnName, columnValue);
//
//                if (deleteRecord) {
//                    System.out.println("Record deleted from the DB.");
//                } else {
//                    System.out.println("Something went wrong!");
//                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean printRecords(ResultSet resultSet) throws SQLException {
        boolean foundData = false;

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

            foundData = true;
        }

        return foundData;
    }

    private static boolean executeSelect(Statement statement, String table, String columnName, String columnValue) throws SQLException {
        String query = "SELECT * FROM %s WHERE %s='%s'".formatted(table, columnName, columnValue);

        System.out.println("---> executeSelect: " + query);

        var resultSet = statement.executeQuery(query);

        if (resultSet.isBeforeFirst()) {
            System.out.println("Item found | executeSelect");
            return printRecords(resultSet);
        } else {
            System.out.println("Item NOT found | executeSelect");
        }

        return false;
    }

    private static boolean insertRecord(Statement statement, String table,
                                        String[] columnNames, String[] columnValues) throws SQLException {
        String colNames = String.join(",", columnNames);
        String colValues = String.join(",", columnValues);
        String query = "INSERT INTO %s (%s) VALUES('%s')".formatted(table, colNames, colValues);

        System.out.println("---> insertRecord: " + query);

        statement.execute(query);
        int recordsInserted = statement.getUpdateCount();

        if (recordsInserted > 0) {
            executeSelect(statement, table, columnNames[0], columnValues[0]);
        }

        return recordsInserted > 0;

    }

    private static boolean deleteRecord(Statement statement, String table, String columnName, String columnValue) throws SQLException {
        String query = "DELETE FROM %s WHERE %s='%s'".formatted(table, columnName, columnValue);
        System.out.println("---> deleteRecord: " + query);

        statement.execute(query);
        int recordsDeleted = statement.getUpdateCount();

        if (recordsDeleted > 0) {
            executeSelect(statement, table, columnName, columnValue);
        }

        return recordsDeleted > 0;
    }

    private static boolean updateRecord(Statement statement, String table, String matchedColumn, String matchedValue, String updatedColumnName, String updatedColumnValue) throws SQLException {
        String query = "UPDATE %s SET %s = '%s' WHERE %s='%s'".formatted(table, updatedColumnName, updatedColumnValue, matchedColumn, matchedValue);
        System.out.println("---> insertRecord: " + query);

        statement.execute(query);
        int recordsUpdated = statement.getUpdateCount();

        if (recordsUpdated > 0) {
            executeSelect(statement, table, updatedColumnName, updatedColumnValue);
        }

        return recordsUpdated > 0;
    }

    private static void insertArtistAlbum(Statement statement, String artistName, String albumName) throws SQLException {
        String artistInsert = "INSERT INTO music.artists (artist_name) VALUES (%s)".formatted(statement.enquoteLiteral(artistName));
        System.out.println("---> insertRecord | add artist: " + artistInsert);

        statement.execute(artistInsert, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = statement.getGeneratedKeys();
        int artistId = (rs != null && rs.next()) ? rs.getInt(1) : -1;

        String albumInsert = "INSERT INTO music.albums (album_name, artist_id) VALUES (%s, %d)".formatted(statement.enquoteLiteral(albumName), artistId);
        System.out.println("---> insertRecord | add album:  " + albumInsert);
        statement.execute(albumInsert, Statement.RETURN_GENERATED_KEYS);
        rs = statement.getGeneratedKeys();
        int albumId = (rs != null && rs.next()) ? rs.getInt(1) : -1;

        String[] songs = new String[]{
                "You're No Good",
                "Talkin' New York",
                "In My Time of Dyin'",
                "Man of Constant Sorrow",
                "Fixin' to Die",
                "Pretty Peggy-O",
                "Highway 51 Blues"
        };

        String songInsert = "INSERT INTO music.songs (track_number, song_title, album_id) VALUES (%d, %s, %d)";

        for (int i = 0; i < songs.length; i++) {
            String songQuery = songInsert.formatted(i + 1,
                    statement.enquoteLiteral(songs[i]), albumId);

            System.out.println("---> insertRecord | add song: " + songQuery);

            statement.execute(songQuery);
        }

        executeSelect(statement, "music.albumview", "album_name",
                albumName);
    }

    private static void deleteArtistAlbum(Connection conn, Statement statement, String artistName, String albumName) throws SQLException {
        try {
            System.out.println("AUTOCOMMIT = " + conn.getAutoCommit());
            conn.setAutoCommit(false);

            String deleteSongsQuery = """
                    DELETE FROM music.songs WHERE album_id =
                    (SELECT ALBUM_ID from music.albums WHERE album_name = '%s')"""
                    .formatted(albumName);
            String deleteAlbumsQuery = "DELETE FROM music.albums WHERE album_name='%s"
                    .formatted(albumName);
            String deleteArtistQuery = "DELETE FROM music.artists WHERE artist_name='%s'"
                    .formatted(artistName);

            statement.addBatch(deleteSongsQuery);
            statement.addBatch(deleteAlbumsQuery);
            statement.addBatch(deleteArtistQuery);

            int[] results = statement.executeBatch();
            System.out.println(Arrays.toString(results));

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        }
        conn.setAutoCommit(true);
        System.out.println("---> deleteArtistAlbum: Transaction completed.");

    }
}
