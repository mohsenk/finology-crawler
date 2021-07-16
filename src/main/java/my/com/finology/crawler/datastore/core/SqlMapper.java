package my.com.finology.crawler.datastore.core;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlMapper<T> {

    T map(ResultSet resultSet) throws SQLException;

}
