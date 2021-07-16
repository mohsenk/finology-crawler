package my.com.finology.crawler.models;

import java.util.Map;

public class Product {


    Integer id;
    String url;
    String name;
    Float price;
    String description;
    Map<String, String> attrs;

    public Product() {
    }

    public Product(Integer id, String url, String name, Float price, String description, Map<String, String> attrs) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.price = price;
        this.description = description;
        this.attrs = attrs;
    }

    public Product(String url, String name, String description, Float price, Map<String, String> attrs) {
        this.url = url;
        this.name = name;
        this.price = price;
        this.description = description;
        this.attrs = attrs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getAttrs() {
        return attrs;
    }
}
