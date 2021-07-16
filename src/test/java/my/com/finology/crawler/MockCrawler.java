package my.com.finology.crawler;

import my.com.finology.crawler.core.impl.Crawler;

public class MockCrawler extends Crawler {

    @Override
    public void start() {
        processNewItems();
        processPendingItems();
    }
}
