package my.com.finology.crawler;

import my.com.finology.crawler.core.NetworkException;
import my.com.finology.crawler.core.ParseException;
import my.com.finology.crawler.core.impl.ProductParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ProductParserTest {

    static final Logger logger = LogManager.getLogger(ProductParserTest.class.getName());

    final String URL = "https://magento-test.finology.com.my/breathe-easy-tank.html";

    @Test
    public void testParser() throws Exception {
        var parser = new ProductParser(URL);
        var product = parser.call();
        assertFalse(product.getName().isEmpty(), "Product name is empty !");
        assertFalse(product.getDescription().isEmpty(), "Product description is empty !");
        assertTrue(product.getPrice() > 0, "Product price is not correct !");
        assertTrue(product.getProps().keySet().size() > 0, "There is no props in product !");
    }

    @Test
    public void testFailedNetwork() {
        assertThrows(NetworkException.class, () -> {
            var parser = new ProductParser("https://magento-test.finology.com.my/breathe-easy-tank.htmls");
            var product = parser.call();
        });
    }

    @Test
    public void testFailedParsing() {
        assertThrows(ParseException.class, () -> {
            var parser = new ProductParser("https://magento-test.finology.com.my");
            var product = parser.call();
        });
    }
}
