package my.com.finology.crawler.workers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;


import java.io.IOException;
import java.util.function.Consumer;


public class Spider implements Runnable {

    static final Logger logger = LogManager.getLogger(Spider.class.getSimpleName());

    final String startUrl;
    Consumer<String> newProductFoundEventListener;

    public Spider(String startUrl) {
        this.startUrl = startUrl;
    }

    public void start() {
        new Thread(this, "Spider-Crawler").start();
    }

    public void setNewProductFoundEventListener(Consumer<String> newProductFoundEventListener) {
        this.newProductFoundEventListener = newProductFoundEventListener;
    }

    @Override
    public void run() {
        try {
            var doc = Jsoup.connect(startUrl).get();
            var categories = doc.select(".navigation ul a");
            for (var category : categories) {
                if (category.nextElementSibling() != null && category.nextElementSibling().tagName().equals("ul")) continue;
                fetchPage(category.attr("href"), 1);
            }
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            logger.info("Spider finished his job !");
        }
    }

    void fetchPage(String url, Integer currentPage) throws IOException {

        var document = Jsoup.connect(url).get();
        var products = document.select(".product-items a.product");
        logger.info("I'm going to look at : {} , products : {}", url, products.size());
        for (var product : products) {
            var productUrl = product.attr("href");
            newProductFoundEventListener.accept(productUrl);
        }

        if (document.select(".pages-items").isEmpty()) return;
        var pages = document.select(".pages-items").get(0).select("a.page");
        for (var page : pages) {
            var pageIndex = Integer.valueOf(page.select("span:last-child").text());
            if (currentPage > pageIndex) continue;
            fetchPage(page.attr("href"), pageIndex);
        }
    }
}
