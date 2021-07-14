package my.com.finology.crawler.core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;


import java.io.IOException;
import java.util.function.Consumer;


public class Spider implements Runnable {


    static final String START_POINT_URL = "http://magento-test.finology.com.my/breathe-easy-tank.html";

    public Spider() {
        new Thread(this, "Spider-Crawler").start();
    }

    Consumer<String> consumer;

    public void setConsumer(Consumer<String> consumer) {
        this.consumer = consumer;
    }


    @Override
    public void run() {

        try {
            var doc = Jsoup.connect(START_POINT_URL).get();
            var categories = doc.select(".navigation ul a");
            for (Element category : categories) {
                fetchPage(category.attr("href"));
            }

        } catch (Exception ex) {

        } finally {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
