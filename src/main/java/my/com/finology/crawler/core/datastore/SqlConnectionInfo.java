package my.com.finology.crawler.core.datastore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnectionInfo {

    final String connectionUrl;

    public SqlConnectionInfo(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl);
    }
}
