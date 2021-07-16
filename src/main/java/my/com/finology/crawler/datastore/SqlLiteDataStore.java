package my.com.finology.crawler.datastore;

import my.com.finology.crawler.core.datastore.BaseDataStore;
import my.com.finology.crawler.models.Product;

public class SqlLiteDataStore implements BaseDataStore {

    String SQL_SCRIPT = """
            create table IF NOT EXISTS products
             (
             	id integer
             		    constraint products_pk
             			primary key autoincrement,
             	name TEXT not null,
             	description text,
             	price REAL not null,
             	url text,
             	attrs TEXT
             );""";

    ProductRepository repository;

    public SqlLiteDataStore(ProductRepository repository) {
        this.repository = repository;
        this.init();
    }

    public void init() {
        repository.executeUpdate(SQL_SCRIPT);
    }

    @Override
    public Product save(Product product) {
        return repository.create(product);
    }

    @Override
    public Boolean exists(String url) {
        return repository.existByUrl(url);
    }

}
