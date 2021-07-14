package my.com.finology.crawler.workers;

import my.com.finology.crawler.core.Crawler;
import my.com.finology.crawler.core.Spider;
import my.com.finology.crawler.models.Product;
import my.com.finology.crawler.storage.ProductRepository;
import my.com.finology.crawler.storage.Storager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;

public class MainWorker implements Runnable {

    static final Logger logger = LogManager.getLogger(MainWorker.class.getName());

    Crawler crawler;
    Spider spider;
    Storager storager;

    public MainWorker() {
        this.spider = new Spider();
        this.spider.setConsumer(this::onNewProductFound);

        this.crawler = new Crawler();
        this.crawler.onNewProduct(this::onNewProductFetched);

        this.storager = new Storager(new ProductRepository());
    }

    public void start() {
        new Thread(this, "Main-Worker").start();
    }

    void onNewProductFound(String url) {
        crawler.addToQueue(url);
    }

    void onNewProductFetched(Product product) {
        this.storager.store(product);
    }

    @Override
    public void run() {
        // do monitoring
        while (true) {
            try {
                logger.info("Crawler status : pending tasks {}", crawler.getFutures().size());

            } catch (Exception ex) {

            } finally {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
