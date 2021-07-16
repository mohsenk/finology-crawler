package my.com.finology.crawler.datastore.core;

import my.com.finology.crawler.models.Product;

public interface BaseDataStore {

    Product save(Product product);

    Boolean exists(String url);
}
