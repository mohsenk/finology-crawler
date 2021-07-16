package my.com.finology.crawler.core;

import my.com.finology.crawler.models.Product;

import java.util.function.Consumer;

public interface BaseCrawler<T> {

    void start();

    void addToQueue(String url);

    void setNewProductFetchedEventListener(Consumer<Product> listener);

}
