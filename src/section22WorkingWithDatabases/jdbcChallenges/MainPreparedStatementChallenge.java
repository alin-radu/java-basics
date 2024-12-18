package section22WorkingWithDatabases.jdbcChallenges;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

record OrderDetail(int orderDetailId, String itemDescription, int qty) {

    public OrderDetail(String itemDescription, int qty) {
        this(-1, itemDescription, qty);
    }
}

record Order(int orderId, String dateString, List<OrderDetail> details) {

    public Order(String dateString) {
        this(-1, dateString, new ArrayList<>());
    }

    public void addDetail(String itemDescription, int qty) {
        OrderDetail item = new OrderDetail(itemDescription, qty);
        details.add(item);
    }
}

@SuppressWarnings({"DuplicatedCode", "JavaExistingMethodCanBeUsed"})
public class MainPreparedStatementChallenge {
    private static final String STOREFRONT_PROPERTIES = "storefront.properties";

    private static final String USE_SCHEMA = "USE storefront";
    private static final int MYSQL_DB_NOT_FOUND = 1049;

    public static void main(String[] args) {
        Properties props = loadProperties(STOREFRONT_PROPERTIES);

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

        List<Order> orders = readData();

        try (Connection conn = dataSource.getConnection()) {

//            String alterString =
//                    "ALTER TABLE storefront.order_details ADD COLUMN quantity INT";
//            Statement statement = conn.createStatement();
//            statement.execute(alterString);

            addOrders(conn, orders);

        } catch (SQLException e) {
            System.out.println("---> main | exception");
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

    // readData
    private static List<Order> readData() {

        List<Order> vals = new ArrayList<>();

        try (Scanner scanner = new Scanner(Path.of("Orders.csv"))) {

            scanner.useDelimiter("[,\\n]");
            var list = scanner.tokens().map(String::trim).toList();

            for (int i = 0; i < list.size(); i++) {

                String value = list.get(i);
                if (value.equals("order")) {
                    var date = list.get(++i);
                    vals.add(new Order(date));
                } else if (value.equals("item")) {
                    var qty = Integer.parseInt(list.get(++i));
                    var description = list.get(++i);
                    Order order = vals.get(vals.size() - 1);
                    order.addDetail(description, qty);
                }
            }
            vals.forEach(System.out::println);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return vals;
    }

    private static void addOrder(Connection conn, PreparedStatement psOrder,
                                 PreparedStatement psDetail, Order order) throws SQLException {
        try {
            conn.setAutoCommit(false);
            int orderId = -1;
            psOrder.setString(1, order.dateString());

            if (psOrder.executeUpdate() == 1) {
                var rs = psOrder.getGeneratedKeys();
                if (rs.next()) {
                    orderId = rs.getInt(1);
                    System.out.println("orderId = " + orderId);

                    if (orderId > -1) {
                        psDetail.setInt(1, orderId);
                        for (OrderDetail od : order.details()) {
                            psDetail.setString(2, od.itemDescription());
                            psDetail.setInt(3, od.qty());
                            psDetail.addBatch();
                        }
                        int[] data = psDetail.executeBatch();
                        int rowsInserted = Arrays.stream(data).sum();
                        if (rowsInserted != order.details().size()) {
                            throw new SQLException("Inserts don't match");
                        }
                    }
                }
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    private static void addOrders(Connection conn, List<Order> orders) {

        String insertOrder = "INSERT INTO storefront.order (order_date) VALUES (?)";
        String insertDetail = "INSERT INTO storefront.order_details " +
                "(order_id, item_description, quantity) values(?, ?, ?)";

        try (
                PreparedStatement psOrder = conn.prepareStatement(insertOrder,
                        Statement.RETURN_GENERATED_KEYS);
                PreparedStatement psDetail = conn.prepareStatement(insertDetail,
                        Statement.RETURN_GENERATED_KEYS);
        ) {

            orders.forEach((o) -> {
                try {
                    addOrder(conn, psOrder, psDetail, o);
                } catch (SQLException e) {
                    System.err.printf("%d (%s) %s%n", e.getErrorCode(),
                            e.getSQLState(), e.getMessage());
                    System.err.println("Problem: " + psOrder);
                    System.err.println("Order: " + o);
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
