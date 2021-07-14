package my.com.finology.crawler.storage;

import java.sql.Connection;
import java.sql.SQLException;

public interface BaseRepository<T> {

    Connection getConnection() throws SQLException;

    default Integer executeUpdate(String sql, Object... params) {
        Connection connection = null;
        try {
            // create a database connection
            connection = getConnection();
            var statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            var result = statement.executeUpdate();
            return statement.getGeneratedKeys().getInt(1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    default <B> B executeQuery(String sql, SqlMapper<B> mapper, Object... params) {
        Connection connection = null;
        try {
            // create a database connection
            connection = getConnection();
            var statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            var result = statement.executeQuery();
            return mapper.map(result);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

}
