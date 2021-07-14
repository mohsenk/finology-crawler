package my.com.finology.crawler.core;


import my.com.finology.crawler.models.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class ProductCrawler implements BaseCrawler<Product>, Supplier<Product> {

    public Product crawl(String url) throws IOException {
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
    }

    @Override
    public Product get() {
        return null;
    }


}
