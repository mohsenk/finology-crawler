package my.com.finology.crawler.core;

public interface BaseParser<T> {

    T parse(String document);
}
