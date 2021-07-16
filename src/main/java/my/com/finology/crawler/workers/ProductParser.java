package my.com.finology.crawler.workers;


import my.com.finology.crawler.core.BaseParser;
import my.com.finology.crawler.core.NetworkException;
import my.com.finology.crawler.core.ParseException;
import my.com.finology.crawler.models.Product;
import org.jsoup.Jsoup;
import org.jsoup.SerializationException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class ProductParser implements Callable<Product> {

    final String url;

    public ProductParser(String url) {
        this.url = url;
    }

    @Override
    public Product call() {
        var document = getDocument(url);
        try {
            var title = document.select(".page-title").text();
            var description = document.select(".product.description").text();
            var priceElement = document.select(".product-info-main .price-wrapper .price").get(0);
            var price = Float.valueOf(priceElement.text().replace("$", ""));
            var attrs = fetchAttrs(document.select("#product-attribute-specs-table tr"));
            return new Product(url, title, description, price, attrs);
        } catch (Exception ex) {
            throw new ParseException("There is an error in parsing document", ex);
        }
    }

    private Document getDocument(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (Exception ex) {
            throw new NetworkException("Something happened in fetching url", ex);
        }
    }

    private Map<String, String> fetchAttrs(Elements props) {
        var propsMap = new HashMap<String, String>();
        for (Element prop : props) {
            var propName = prop.select("th").text();
            var propValue = prop.select("td").text();
            propsMap.put(propName, propValue);
        }
        return propsMap;
    }
}
