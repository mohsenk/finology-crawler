package my.com.finology.crawler;


import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class CrawlerTest {


    @Test
    void futures() throws InterruptedException, ExecutionException, TimeoutException {
        var crawler = new MockCrawler();
        crawler.addToQueue("https://magento-test.finology.com.my/sprite-yoga-companion-kit.html");
        // crawler.addToQueue("https://magento-test.finology.com.my/push-it-messenger-bag.html");
        crawler.setNewProductFetchedEventListener(product -> {
            assertEquals("Sprite Yoga Companion Kit", product.getName(), "Product fetched correctly :)");
        });
        crawler.start();
        var product = crawler.getFutures().get(0).get(5, TimeUnit.SECONDS);
        assertEquals("Sprite Yoga Companion Kit", product.getName(), "Something is wrong , product data is invalid :)");
    }

}
