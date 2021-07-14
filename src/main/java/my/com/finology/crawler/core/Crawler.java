package my.com.finology.crawler.core;

import my.com.finology.crawler.models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.function.Consumer;


public class Crawler implements Runnable {

    final Executor executor = Executors.newFixedThreadPool(10);

    public Crawler() {
        new Thread(this, "Product-Crawler-Worker").start();
    }

    static final Logger logger = LogManager.getLogger(Crawler.class.getName());

    private Queue<String> queue = new ConcurrentLinkedQueue<>();
    private ProductCrawler crawler = new ProductCrawler();

    public List<CompletableFuture<Product>> futures = new ArrayList<>();


    public List<CompletableFuture<Product>> getFutures() {
        return futures;
    }

    private Consumer<Product> onNewProduct;

    public void onNewProduct(Consumer<Product> onNewProduct) {
        this.onNewProduct = onNewProduct;
    }

    public void addToQueue(String url) {
        queue.add(url);
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (queue.isEmpty() && futures.isEmpty()) continue;
                processNewItems();
                processPendingItems();
            } catch (Exception ex) {
                logger.error("", ex);
            } finally {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void processPendingItems() {
        for (int i = 0; i < futures.size(); i++) {
            var future = futures.get(i);
            if (future.isDone()) {
                try {
                    onNewProduct.accept(future.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                futures.remove(i);
            }
        }
    }

    void processNewItems() {
        while (futures.size() < 10 && !queue.isEmpty()) {
            var url = queue.poll();
            var future = CompletableFuture.supplyAsync(() -> crawl(url));
            futures.add(future);
        }
    }

    private Product crawl(String url) throws CompletionException {
        try {
            var document = Jsoup.connect(url).get();
            var title = document.select(".page-title").text();
            var description = document.select(".product.description").text();
            var price = Float.valueOf(document.select(".product-info-main .price-wrapper .price").text().replace("$", ""));
            var props = document.select("#product-attribute-specs-table tr");
            var propsMap = new HashMap<String, String>();
            for (Element prop : props) {
                var propName = prop.select("th").text();
                var propValue = prop.select("td").text();
                propsMap.put(propName, propValue);
            }
            var product = new Product(url, title, description, price, propsMap);
            return product;
        } catch (Exception ex) {
            throw new CompletionException(ex);
        }
    }
}
