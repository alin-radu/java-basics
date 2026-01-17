package section22WorkingWithDatabases.jdbcChallenges;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@SuppressWarnings("DuplicatedCode")
public class MainPreparedStatementBasics {
    private static final String MUSIC_PROPERTIES = "music.properties";

    private static final String ARTIST_INSERT = "INSERT INTO music.artists (artist_name) VALUES (?)";
    private static final String ALBUM_INSERT = "INSERT INTO music.albums (artist_id, album_name) VALUES (?, ?)";
    private static final String SONG_INSERT = "INSERT INTO music.songs (album_id, track_number, song_title) VALUES (?, ?, ?)";

    public static void main(String[] args) {
        Properties props = loadProperties(MUSIC_PROPERTIES);

        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));
        dataSource.setUser(System.getenv("MYSQL_USER"));
        dataSource.setPassword(System.getenv("MYSQL_PASS"));

        try {
            dataSource.setContinueBatchOnError(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (
                Connection connection = dataSource.getConnection();
        ) {
            addDataFromFile(connection);

            String sql = "SELECT * FROM music.albumview WHERE artist_name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "Bob Dylan");
            ResultSet resultSet = ps.executeQuery();

            printRecords(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // loadProperties
    public static Properties loadProperties(String fileName) {
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(Path.of(fileName), StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + fileName, e);
        }
        return props;
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

    // addArtist
    private static int addArtist(PreparedStatement ps, String artistName) throws SQLException {
        int artistId = -1;

        ps.setString(1, artistName);
        int insertedCount = ps.executeUpdate();

        if (insertedCount > 0) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                artistId = generatedKeys.getInt(1);
                System.out.println("Auto-increment ID: " + artistId);
            }
        }

        return artistId;
    }

    // addAlbum
    private static int addAlbum(PreparedStatement ps, int artistId, String albumName) throws SQLException {
        int albumId = -1;

        ps.setInt(1, artistId);
        ps.setString(2, albumName);

        int insertedCount = ps.executeUpdate();

        if (insertedCount > 0) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                albumId = generatedKeys.getInt(1);
                System.out.println("Auto-increment ID: " + albumId);
            }
        }

        return albumId;
    }

    // addSong
    private static int addSong(PreparedStatement ps, int albumId, int trackNo, String songTitle) throws SQLException {
        int songId = -1;

        ps.setInt(1, albumId);
        ps.setInt(2, trackNo);
        ps.setString(3, songTitle);

        int insertedCount = ps.executeUpdate();

        if (insertedCount > 0) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                songId = generatedKeys.getInt(1);
                System.out.println("Auto-increment ID: " + songId);
            }

        }

        return songId;
    }

    // addSongWithBach
    private static void addSongWithBach(PreparedStatement ps, int albumId, int trackNo, String songTitle) throws SQLException {

        ps.setInt(1, albumId);
        ps.setInt(2, trackNo);
        ps.setString(3, songTitle);

        ps.addBatch();
    }

    // readFile
    public static List<String> readFile(String fileName) {
        try {
            return Files.readAllLines(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + fileName, e);
        }
    }

    // addDataFromFile
    private static void addDataFromFile(Connection conn) throws SQLException {

        List<String> records = readFile("NewAlbums.csv");

        String lastAlbum = null;
        String lastArtist = null;
        int artistId = -1;
        int albumId = -1;

        try (
                PreparedStatement psArtist = conn.prepareStatement(ARTIST_INSERT, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement psAlbum = conn.prepareStatement(ALBUM_INSERT, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement psSong = conn.prepareStatement(SONG_INSERT, Statement.RETURN_GENERATED_KEYS);
        ) {
            conn.setAutoCommit(false);

            for (String record : records) {
                String[] columns = record.split(",");

                // add artist
                if (lastArtist == null || !lastArtist.equals(columns[0])) {
                    lastArtist = columns[0];
                    artistId = addArtist(psArtist, lastArtist);
                }

                // add album
                if (lastAlbum == null || !lastAlbum.equals(columns[1])) {
                    lastAlbum = columns[1];
                    albumId = addAlbum(psAlbum, artistId, lastAlbum);
                }

                // add song
                int trackNo = Integer.parseInt(columns[2]);
                addSongWithBach(psSong, albumId, trackNo, columns[3]);
            }
            int[] inserts = psSong.executeBatch();

            int totalInserts = Arrays.stream(inserts).sum();

            System.out.printf("%d song records added %n", totalInserts);

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException(e);
        } finally {
            conn.setAutoCommit(true);
        }
    }

}
