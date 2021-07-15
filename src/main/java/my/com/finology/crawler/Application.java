package my.com.finology.crawler;

import my.com.finology.crawler.workers.MainWorker;

public class Application {

    public static void main(String[] args) {
        var startUrl = "https://magento-test.finology.com.my/breathe-easy-tank.html";
        var mainWorker = new MainWorker(startUrl);
        mainWorker.start();
    }


}
