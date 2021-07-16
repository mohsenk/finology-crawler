package my.com.finology.crawler;

import my.com.finology.crawler.core.datastore.BaseDataStore;
import my.com.finology.crawler.core.datastore.SqlConnectionInfo;
import my.com.finology.crawler.models.Product;
import my.com.finology.crawler.datastore.ProductRepository;
import my.com.finology.crawler.datastore.SqlLiteDataStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataStoreTests {

    BaseDataStore dataStore;

    @BeforeEach
    void setUp() {
        dataStore = new SqlLiteDataStore(new ProductRepository(new SqlConnectionInfo(String.format("jdbc:sqlite:test-%s.db", System.currentTimeMillis()))));
    }


    @Test
    void testStorage() {
        var url = "https://magento-test.finology.com.my/breathe-easy-tank.html";
        var product = new Product(
                "https://magento-test.finology.com.my/breathe-easy-tank.html",
                "Breathe-Easy Tank",
                "The Breathe Easy Tank is so soft, lightweight, and comfortable, you won't even know it's there",
                34.0f,
                Map.of("Style", "Tank", "Pattern", "Solid")
        );
        product = dataStore.save(product);
        assertTrue(product.getId() > 0, "The product entity stored correctly");
    }

    @Test
    void testStorageCheckExist() {
        var url = "https://magento-test.finology.com.my/breathe-easy-tank.html";
        assertFalse(dataStore.exists(url), "The check method is not working correctly");
    }

    @Test
    void testStorageCheckNotExist() {
        var url = "https://magento-test.finology.com.my/breathe-easy-tank.html";
        var product = new Product(
                url,
                "Breathe-Easy Tank",
                "The Breathe Easy Tank is so soft, lightweight, and comfortable, you won't even know it's there",
                34.0f,
                Map.of("Style", "Tank", "Pattern", "Solid")
        );
        dataStore.save(product);
        assertTrue(dataStore.exists(url), "The check method is not working correctly");
    }

}
