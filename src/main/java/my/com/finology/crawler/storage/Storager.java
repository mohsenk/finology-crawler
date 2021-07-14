package my.com.finology.crawler.storage;

import my.com.finology.crawler.models.Product;

public class Storager implements Runnable {

    ProductRepository repository;

    public Storager(ProductRepository repository) {
        this.repository = repository;
    }

    public void store(Product product) {
        repository.create(product);
    }

    @Override
    public void run() {

    }
}
