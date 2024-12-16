package section22WorkingWithDatabases.queryMusic;

import java.sql.*;

@SuppressWarnings("DuplicatedCode")
public class MusicDML {

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306",
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASS"));
             Statement statement = connection.createStatement();
        ) {
            String tableName = "music.artists";
            String columnName = "artist_name";
            String columnValue = "Bobby L Dylan";

            boolean existsData = executeSelect(statement, tableName, columnName, columnValue);

            if (!existsData) {
                System.out.println("Maybe we should add this record.");
                insertRecord(statement, tableName, new String[]{columnName}, new String[]{columnValue});
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
}
