package my.com.finology.crawler.workers;

import my.com.finology.crawler.core.BaseCrawler;
import my.com.finology.crawler.models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.function.Consumer;


public class Crawler implements Runnable, BaseCrawler<Product> {

    private final ExecutorService executor;

    static final Logger logger = LogManager.getLogger(Crawler.class.getSimpleName());

    private final Queue<String> queue;

    private final List<Future<Product>> futures;

    private Consumer<Product> newProductFetchedEventListener;


    public Crawler() {
        this.futures = new ArrayList<>();
        this.queue = new ConcurrentLinkedQueue<>();
        this.executor = Executors.newFixedThreadPool(10);
    }

    public List<Future<Product>> getFutures() {
        return futures;
    }

    @Override
    public void setNewProductFetchedEventListener(Consumer<Product> listener) {
        this.newProductFetchedEventListener = listener;
    }

    @Override
    public void addToQueue(String url) {
        queue.add(url);
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (queue.isEmpty() && futures.isEmpty()) {
                    Thread.sleep(100);
                    continue;
                }
                processNewItems();
                processPendingItems();
            } catch (Exception ex) {
                logger.error("", ex);
            }
        }
    }


    protected void processPendingItems() {
        for (int i = 0; i < futures.size(); i++) {
            try {
                var future = futures.get(i);
                if (future.isDone()) {
                    try {
                        newProductFetchedEventListener.accept(future.get());
                    } catch (Exception e) {
                        logger.error("Something happened in executing event listener", e);
                    }
                    futures.remove(i);
                }
            } catch (Exception ex) {
                logger.error("Future have an error in processing", ex);
                futures.remove(i);
            }
        }
    }

    protected void processNewItems() {
        while (futures.size() < 10 && !queue.isEmpty()) {
            var url = queue.poll();
            var future = executor.submit(new ProductParser(url));
            futures.add(future);
        }
    }


    @Override
    public void start() {
        new Thread(this, "Product-Crawler-Worker").start();
    }
}
