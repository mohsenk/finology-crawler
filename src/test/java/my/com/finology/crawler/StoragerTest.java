package my.com.finology.crawler;

import my.com.finology.crawler.models.Product;
import my.com.finology.crawler.storage.ProductRepository;
import my.com.finology.crawler.storage.Storager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

public class StoragerTest {

    @Test
    void testStorage() {
        var storager = new Storager(new ProductRepository());
        var url = "https://magento-test.finology.com.my/breathe-easy-tank.html";
        var product = new Product(
                "https://magento-test.finology.com.my/breathe-easy-tank.html",
                "Breathe-Easy Tank",
                "The Breathe Easy Tank is so soft, lightweight, and comfortable, you won't even know it's there",
                34.0f,
                Map.of("Style", "Tank", "Pattern", "Solid")
        );
        storager.store(product);
        assertTrue(storager.check(url),"The product entity stored correctly");
    }

    @Test
    void testStorageCheck() {
        var storager = new Storager(new ProductRepository());
        var url = "https://magento-test.finology.com.my/breathe-easy-tank.html";
        var product = new Product(
                "https://magento-test.finology.com.my/breathe-easy-tank.html",
                "Breathe-Easy Tank",
                "The Breathe Easy Tank is so soft, lightweight, and comfortable, you won't even know it's there",
                34.0f,
                Map.of("Style", "Tank", "Pattern", "Solid")
        );
        //storager.store(product);
        assertFalse(storager.check(url),"The check method is not working correctly");
    }
}
