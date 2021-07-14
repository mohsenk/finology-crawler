package my.com.finology.crawler.core;

public interface BaseCrawler<T> {


    T crawl(String pageURL) throws Exception;

}
