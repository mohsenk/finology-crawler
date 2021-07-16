package my.com.finology.crawler.core.datastore;

import my.com.finology.crawler.models.Product;

public interface BaseDataStore {

    Product save(Product product);

    Boolean exists(String url);
}
