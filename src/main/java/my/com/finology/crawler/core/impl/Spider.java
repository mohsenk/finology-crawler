package my.com.finology.crawler.core.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;


import java.io.IOException;
import java.util.function.Consumer;


public class Spider implements Runnable {

    static final Logger logger = LogManager.getLogger(Spider.class.getName());

    final String startUrl;
    Consumer<String> consumer;

    public Spider(String startUrl) {
        new Thread(this, "Spider-Crawler").start();
        this.startUrl = startUrl;
    }

    public void setConsumer(Consumer<String> consumer) {
        this.consumer = consumer;
    }


    @Override
    public void run() {

        try {
            var doc = Jsoup.connect(startUrl).get();
            var categories = doc.select(".navigation ul a");
            for (Element category : categories) {
                fetchPage(category.attr("href"));
            }

        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }

    }

    void fetchPage(String url) throws IOException {
        var document = Jsoup.connect(url).get();
        var pages = document.select(".pages-items a");
        var products = document.select(".product-items a.product");
        for (var product : products) {
            consumer.accept(product.attr("href"));
        }

        for (var page : pages) {
            fetchPage(page.attr("href"));
        }

    }
}
