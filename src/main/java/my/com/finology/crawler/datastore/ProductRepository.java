package my.com.finology.crawler.datastore;

import com.google.gson.Gson;
import my.com.finology.crawler.core.datastore.BaseRepository;
import my.com.finology.crawler.core.datastore.SqlConnectionInfo;
import my.com.finology.crawler.core.datastore.SqlMapper;
import my.com.finology.crawler.models.Product;

import java.util.Map;

public class ProductRepository implements BaseRepository<Product> {

    Gson gson = new Gson();

    final SqlMapper<Product> productMapper = resultSet -> {
        var id = resultSet.getInt("id");
        var name = resultSet.getString("name");
        var description = resultSet.getString("description");
        var price = resultSet.getFloat("price");
        var url = resultSet.getString("url");
        var attrs = resultSet.getString("attrs");
        var attrsMap = gson.fromJson(attrs, Map.class);
        return new Product(id, url, name, price, description, attrsMap);
    };

    final SqlConnectionInfo sqlConnection;

    public ProductRepository(SqlConnectionInfo sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    @Override
    public SqlConnectionInfo getSqlConnection() {
        return sqlConnection;
    }

    public Product create(Product product) {

        var id = executeUpdate(
                "insert into products(url,name,price,description,attrs) values(?,?,?,?,?)",
                product.getUrl(), product.getName(), product.getPrice(), product.getDescription(), gson.toJson(product.getAttrs())
        );
        product.setId(id);
        return product;
    }

    public Product findById(Integer id) {
        return executeQuery("select * from products where id=?", productMapper, id);
    }

    public Product findByUrl(String url) {
        return executeQuery("select * from products where url=?", productMapper, url);
    }

    public Boolean existByUrl(String url) {
        var result = executeQuery("select count(*) as count from products where url=?", resultSet -> resultSet.getInt("count") > 0, url);
        return result;
    }

}
