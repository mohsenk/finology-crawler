package my.com.finology.crawler.core.datastore;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlMapper<T> {

    T map(ResultSet resultSet) throws SQLException;

}
