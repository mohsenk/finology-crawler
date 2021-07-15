package my.com.finology.crawler.storage;

import my.com.finology.crawler.models.Product;

import java.sql.*;

public class ProductRepository implements BaseRepository<Product> {

    static final SqlMapper<Product> productMapper = resultSet -> {
        var id = resultSet.getInt("id");
        var name = resultSet.getString("name");
        var description = resultSet.getString("description");
        var price = resultSet.getFloat("price");
        var url = resultSet.getString("url");
        return new Product(id, url, name, price, description, null);
    };


    public ProductRepository() {
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:products.db");
    }

    public Product create(Product product) {
        executeUpdate("insert into products(url,name,price,description) values(?,?,?,?)", product.getUrl(), product.getName(), product.getPrice(), product.getDescription());
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
