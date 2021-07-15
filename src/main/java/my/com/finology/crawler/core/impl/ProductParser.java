package my.com.finology.crawler.core.impl;


import my.com.finology.crawler.core.BaseParser;
import my.com.finology.crawler.core.NetworkException;
import my.com.finology.crawler.core.ParseException;
import my.com.finology.crawler.models.Product;
import org.jsoup.Jsoup;
import org.jsoup.SerializationException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class ProductParser implements Callable<Product> {

    final String url;

    public ProductParser(String url) {
        this.url = url;
    }

    @Override
    public Product call() throws Exception {
        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (Exception ex) {
            throw new NetworkException("Something happened in fetching url", ex);
        }
        try {
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
            throw new ParseException("There is an error in parsing document", ex);
        }
    }
}
