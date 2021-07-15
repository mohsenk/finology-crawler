package my.com.finology.crawler;


import my.com.finology.crawler.core.impl.Crawler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CrawlerTest {

    private final Crawler crawler = new Crawler();

    @Test
    void addition() {
        crawler.addToQueue("https://google.com");
        crawler.onNewProduct(product -> {

        });
        crawler.start();

    }

}
