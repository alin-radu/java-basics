package section22WorkingWithDatabases.jdbcChallenges;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

@SuppressWarnings("DuplicatedCode")
public class MainStoreFront {

    private static final String USE_SCHEMA = "USE storefront";
    private static final int MYSQL_DB_NOT_FOUND = 1049;

    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(Path.of("storefront.properties"), StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setUser(System.getenv("MYSQL_USER"));
        dataSource.setPassword(System.getenv("MYSQL_PASS"));

        try (Connection conn = dataSource.getConnection()) {
            var metaData = conn.getMetaData();
            System.out.println("---> main | " + metaData.getSQLStateType());

            if (!checkSchema(conn)) {
                System.out.println("---> storefront schema does not exist");

                setUpSchema(conn);
            }

//            int newOrderId = addOrder(conn, new String[]{"shoes", "shirt", "socks"});
//            System.out.println("---> newOrderId: " + newOrderId);

            deleteOrder(conn, 5);
        } catch (SQLException e) {
            System.out.println("---> main | exception");
            throw new RuntimeException(e);
        }
    }

    private static boolean checkSchema(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute(USE_SCHEMA);
        } catch (SQLException e) {
            e.printStackTrace();

            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getMessage());

            if (conn.getMetaData().getDatabaseProductName().equals("MySQL") &&
                    e.getErrorCode() == MYSQL_DB_NOT_FOUND
            ) {
                return false;
            } else throw e;
        }

        return true;
    }

    private static void setUpSchema(Connection conn) {
        String createSchema = "CREATE SCHEMA storefront";
        String createOrderTable = """
                CREATE TABLE storefront.order(
                order_id int NOT NULL AUTO_INCREMENT,
                order_date DATETIME NOT NULL,
                PRIMARY KEY (order_id)
                )""";
        String createOrderDetailsTable = """
                CREATE TABLE storefront.order_details(
                order_detail_id int NOT NULL AUTO_INCREMENT,
                item_description text,
                order_id int DEFAULT NULL,
                PRIMARY KEY (order_detail_id),
                KEY FK_ORDER_ID (order_id),
                CONSTRAINT FK_ORDER_ID FOREIGN KEY (order_id)
                    REFERENCES storefront.order (order_id) ON DELETE CASCADE
                )""";

        try (Statement statement = conn.createStatement()) {
            System.out.println("Creating storefront DB ...");
            statement.execute(createSchema);

            if (checkSchema(conn)) {
                statement.execute(createOrderTable);
                System.out.println("Successfully Created the Order Table.");

                statement.execute(createOrderDetailsTable);
                System.out.println("Successfully Created the OrderDetails Table.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static int addOrder(Connection conn, String[] items) throws SQLException {

        int orderId = -1;
        String insertOrderQuery = "INSERT INTO storefront.order (order_date) VALUES ('%s')";
        String insertDetailQuery = "INSERT INTO storefront.order_details " +
                "(order_id, item_description) values(%d, %s)";

        DateTimeFormatter dtf =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String orderDateTime = LocalDateTime.now().format(dtf);
        System.out.println(orderDateTime);
        String formattedString = insertOrderQuery.formatted(orderDateTime);
        System.out.println(formattedString);

//        String insertOrderAlternative = "INSERT INTO storefront.order (order_date) " +
//                "VALUES ('%1$tF %1$tT')";
//        System.out.println(insertOrderAlternative.formatted(LocalDateTime.now()));

        try (Statement statement = conn.createStatement()) {

            // enable rollback
            conn.setAutoCommit(false);

            // add order
            int inserts = statement.executeUpdate(formattedString,
                    Statement.RETURN_GENERATED_KEYS);

            if (inserts == 1) {
                var rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }
            }

            // add order details, every item in items will be an order_details
            int count = 0;
            for (var item : items) {
                formattedString = insertDetailQuery.formatted(orderId,
                        statement.enquoteLiteral(item));
                inserts = statement.executeUpdate(formattedString);
                count += inserts;
            }

            if (count != items.length) {
                orderId = -1;
                System.out.println("Number of records inserted doesn't equal items received");
                conn.rollback();
            } else {
                System.out.println("---> Successfully added the order and the order_details.");
                conn.commit();
            }

        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException(e);
        }

        conn.setAutoCommit(true);

        return orderId;
    }

    private static int countOrderDetails(Statement statement, int orderId) throws SQLException {
        String countQuery = "SELECT COUNT(*) FROM storefront.order_details WHERE order_id =%d".formatted(orderId);

        try (ResultSet resultSet = statement.executeQuery(countQuery)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }

    private static void deleteOrder(Connection conn, int orderId) throws SQLException {

        String deleteOrderQuery = "DELETE FROM %s where order_id=%d";
        String parentQuery = deleteOrderQuery.formatted("storefront.order", orderId);
        String childQuery = deleteOrderQuery.formatted("storefront.order_details", orderId);

        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);

            int orderDetailsCount = countOrderDetails(statement, orderId);

            System.out.println("---> orderDetailsCountQuery: " + orderDetailsCount);

            int deletedOrderDetailsRecords = statement.executeUpdate(childQuery);

            if (deletedOrderDetailsRecords == orderDetailsCount) {
                int deletedOrderRecords = statement.executeUpdate(parentQuery);
                System.out.printf("%d child records deleted%n", deletedOrderDetailsRecords);

                if (deletedOrderRecords == 1) {
                    conn.commit();
                    System.out.printf("Order %d was successfully deleted%n", orderId);
                } else {
                    System.out.println("Something went wrong with deleting the order no. " + orderId);
                    conn.rollback();
                }
            } else {
                System.out.println("Something went wrong with deleting the order no. " + orderId);
                conn.rollback();
            }

        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException(e);
        } finally {
            conn.setAutoCommit(true);
        }
    }

}
