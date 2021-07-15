package my.com.finology.crawler.workers;

import my.com.finology.crawler.core.impl.Crawler;
import my.com.finology.crawler.core.impl.Spider;
import my.com.finology.crawler.models.Product;
import my.com.finology.crawler.storage.ProductRepository;
import my.com.finology.crawler.storage.Storager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainWorker implements Runnable {

    static final Logger logger = LogManager.getLogger(MainWorker.class.getName());

    Crawler crawler;
    Spider spider;
    Storager storager;

    public MainWorker(String startUrl) {
        this.spider = new Spider(startUrl);
        this.spider.setConsumer(this::onNewProductFound);

        this.crawler = new Crawler();
        this.crawler.onNewProduct(this::onNewProductFetched);

        this.storager = new Storager(new ProductRepository());
    }

    public void start() {
        new Thread(this, "Main-Worker").start();
        logger.info("Main Worker is started.");
    }

    void onNewProductFound(String url) {
        logger.info("Spider found a new product at : {}", url);
        if (storager.check(url)) {
            logger.info("Product is already stored in database !");
            return;
        }
        crawler.addToQueue(url);
    }

    void onNewProductFetched(Product product) {
        logger.info("Crawled fetched a new product : {}", product.getName());
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
