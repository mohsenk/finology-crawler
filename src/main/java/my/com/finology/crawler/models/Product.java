package my.com.finology.crawler.models;


import java.util.Map;

//@Entity
//@Table(name = "products")
public class Product {


    Integer id;
    String url;
    String name;
    Float price;
    String description;
    Map<String, String> props;

    public Product() {
    }

    public Product(Integer id, String url, String name, Float price, String description, Map<String, String> props) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.price = price;
        this.description = description;
        this.props = props;
    }

    public Product(String url, String name, String description, Float price, Map<String, String> props) {
        this.url = url;
        this.name = name;
        this.price = price;
        this.description = description;
        this.props = props;
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

    public Map<String, String> getProps() {
        return props;
    }
}
